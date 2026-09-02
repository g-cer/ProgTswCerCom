#!/bin/sh
set -eu

# Fallisce subito, e in modo esplicito, se manca una variabile: meglio di un
# avvio apparentemente riuscito con un data source non funzionante.
: "${DB_URL:?DB_URL non impostata}"
: "${DB_USERNAME:?DB_USERNAME non impostata}"
: "${DB_PASSWORD:?DB_PASSWORD non impostata}"
: "${KEYITALY_MEDIA_DIR:?KEYITALY_MEDIA_DIR non impostata}"

# Le credenziali NON passano da CATALINA_OPTS: catalina.sh valuta quella
# variabile con eval, quindi caratteri come & o ; la spezzerebbero. Tomcat
# legge direttamente le variabili d'ambiente grazie a EnvironmentPropertySource.

mkdir -p "${KEYITALY_MEDIA_DIR}"

exec "$@"
