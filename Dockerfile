FROM adoptopenjdk/openjdk11
WORKDIR /des
EXPOSE 3010
COPY wait-for.sh .
RUN chmod +x wait-for.sh
COPY data-enrichment-service-1.0-SNAPSHOT.jar app.jar
