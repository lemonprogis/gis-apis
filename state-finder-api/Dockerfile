FROM gcr.io/distroless/java17-debian12
COPY src/main/resources/data data
COPY target/*.jar app.jar
EXPOSE 8081
CMD ["-Ddata.shapefiles.us-states=./data/us_states/cb_2018_us_state_500k.shp","app.jar"]
