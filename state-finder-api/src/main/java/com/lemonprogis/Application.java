package com.lemonprogis;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public DataStore stateDataStore() throws IOException {
		Resource resource = new ClassPathResource("us_states/cb_2018_us_state_500k.shp");
		resource.getFile();
		Map<String, Object> map = new HashMap<>();
		map.put("url", resource.getFile().toURI().toURL());
		return DataStoreFinder.getDataStore(map);
	}
}
