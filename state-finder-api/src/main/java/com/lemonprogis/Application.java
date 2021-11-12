package com.lemonprogis;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public DataStore stateDataStore(
			@Value("${data.shapefiles.us-states}") String usStatesShapefile) throws IOException {
		File shp = ResourceUtils.getFile(usStatesShapefile);
		Map<String, Object> map = new HashMap<>();
		map.put("url", shp.toURI().toURL());
		return DataStoreFinder.getDataStore(map);
	}
}
