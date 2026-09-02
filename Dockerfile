# ---------------------------------------------------------------------------
# Fase 1: compilazione
#
# Maven vive solo in questa fase: non serve averlo installato sulla macchina.
# ---------------------------------------------------------------------------
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /build

# Le dipendenze vengono scaricate prima dei sorgenti, cosi' il livello resta in
# cache finche' il pom non cambia
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package

# Il driver JDBC non va nella WAR: serve al pool di connessioni di Tomcat, che
# vive nel classloader del container. Lo si estrae qui per la fase successiva.
RUN mvn -B dependency:copy \
      -Dartifact=com.mysql:mysql-connector-j:8.0.31 \
      -DoutputDirectory=/driver


# ---------------------------------------------------------------------------
# Fase 2: esecuzione
#
# Tomcat 9 e' obbligatorio: l'applicazione usa il namespace javax.servlet, che
# a partire da Tomcat 10 e' stato rinominato in jakarta.servlet.
# ---------------------------------------------------------------------------
FROM tomcat:9.0-jre17-temurin

ENV LANG=C.UTF-8 \
    TZ=Europe/Rome \
    JAVA_OPTS="-Dfile.encoding=UTF-8 -Duser.timezone=Europe/Rome"

# Consente a Tomcat di risolvere i segnaposto ${...} dei file di configurazione
# anche dalle variabili d'ambiente. La lettura dalle system property -D resta
# comunque attiva di default.
RUN printf '\norg.apache.tomcat.util.digester.PROPERTY_SOURCE=org.apache.tomcat.util.digester.EnvironmentPropertySource\n' \
      >> /usr/local/tomcat/conf/catalina.properties

# L'immagine ufficiale non pubblica applicazioni di default
RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /driver/mysql-connector-j-*.jar /usr/local/tomcat/lib/

# Pubblicata come ROOT.war per ottenere il context path "/"
COPY --from=build /build/target/keyitaly.war /usr/local/tomcat/webapps/ROOT.war

COPY docker/entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

ENV DB_URL="jdbc:mysql://db:3306/keyItaly" \
    DB_USERNAME="keyitaly" \
    DB_PASSWORD="keyitaly" \
    KEYITALY_MEDIA_DIR="/var/lib/keyitaly/immaginiCatalogo"

EXPOSE 8080
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
CMD ["catalina.sh", "run"]
