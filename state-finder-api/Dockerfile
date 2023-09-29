FROM gcr.io/distroless/java:17-nonroot
COPY src/main/resources/data data
COPY target/*.jar app.jar
EXPOSE 8080
CMD ["-Ddata.shapefiles.us-states=./data/us_states/cb_2018_us_state_500k.shp","app.jar"]
