FROM rubix-docker-dev.artifacts.tabdigital.com.au/rubix-atlas-docker-base:1.0-1505727165
WORKDIR /app
COPY target/wiremockstudy-1.0-SNAPSHOT-jar-with-dependencies.jar wiremockstudy-1.0-SNAPSHOT-jar-with-dependencies.jar
COPY target/classes/ target/classes
EXPOSE 8181
ENTRYPOINT java -jar wiremockstudy-1.0-SNAPSHOT-jar-with-dependencies.jar