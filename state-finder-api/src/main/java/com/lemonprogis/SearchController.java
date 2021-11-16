package com.lemonprogis;

import com.lemonprogis.model.UsState;
import org.geotools.filter.text.cql2.CQLException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public UsState findStateByLatLng(
            @RequestParam String lat, @RequestParam String lng) throws IOException, CQLException {
        return searchService.searchPointInState(lat, lng);
    }
}
