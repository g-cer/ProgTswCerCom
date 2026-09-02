package config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Individua la directory che contiene le immagini del catalogo.
 *
 * Le immagini non stanno dentro l'applicazione ma in una cartella esterna e
 * scrivibile: l'amministratore ne carica di nuove dal pannello di gestione e
 * devono sopravvivere a un nuovo deploy. La posizione di quella cartella
 * dipende pero' dalla macchina, percio' viene risolta all'avvio nell'ordine:
 *
 *   1. variabile d'ambiente KEYITALY_MEDIA_DIR   (usata da Docker)
 *   2. system property keyitaly.media.dir        (comoda da IDE)
 *   3. context-param WEBCONTENT_PATH in web.xml
 *   4. una cartella dentro la directory di lavoro di Tomcat
 *
 * La directory viene creata se non esiste e resa disponibile alle servlet
 * come attributo del ServletContext.
 */
@WebListener
public class MediaStorage implements ServletContextListener {

    private static final String ATTRIBUTE = "KEYITALY_MEDIA_DIR";
    private static final String ENV_VAR = "KEYITALY_MEDIA_DIR";
    private static final String SYSTEM_PROPERTY = "keyitaly.media.dir";
    private static final String CONTEXT_PARAM = "WEBCONTENT_PATH";
    private static final String DEFAULT_DIR_NAME = "keyitaly-media";

    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        Path directory = resolve(context);

        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Impossibile creare la directory delle immagini del catalogo: " + directory, e);
        }

        context.setAttribute(ATTRIBUTE, directory);
        context.log("Directory delle immagini del catalogo: " + directory);
    }

    public void contextDestroyed(ServletContextEvent event) {
        event.getServletContext().removeAttribute(ATTRIBUTE);
    }

    /**
     * Restituisce la directory delle immagini risolta all'avvio dell'applicazione.
     */
    public static Path directory(ServletContext context) {
        Path directory = (Path) context.getAttribute(ATTRIBUTE);
        if (directory == null) {
            throw new IllegalStateException(
                    "Directory delle immagini non inizializzata: il listener MediaStorage non e' stato eseguito");
        }
        return directory;
    }

    private static Path resolve(ServletContext context) {
        String configured = System.getenv(ENV_VAR);

        if (isBlank(configured)) {
            configured = System.getProperty(SYSTEM_PROPERTY);
        }
        if (isBlank(configured)) {
            configured = context.getInitParameter(CONTEXT_PARAM);
        }
        if (isBlank(configured)) {
            String base = System.getProperty("catalina.base", System.getProperty("java.io.tmpdir"));
            configured = Paths.get(base, DEFAULT_DIR_NAME).toString();
        }

        return Paths.get(configured).toAbsolutePath().normalize();
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
