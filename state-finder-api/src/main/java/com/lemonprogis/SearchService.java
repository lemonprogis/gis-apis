package com.lemonprogis;

import com.lemonprogis.model.UsState;
import lombok.extern.slf4j.Slf4j;
import org.geotools.data.DataStore;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.filter.text.ecql.ECQL;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class SearchService {
    private final DataStore stateDataStore;

    public SearchService(DataStore stateDataStore) {
        this.stateDataStore = stateDataStore;
    }

    public UsState searchPointInState(String lat, String lng) throws IOException, CQLException {
       String typeName = stateDataStore.getTypeNames()[0];

       FeatureSource<SimpleFeatureType, SimpleFeature> source =
               stateDataStore.getFeatureSource(typeName);
       Filter filter = ECQL.toFilter(String.format("CONTAINS(the_geom, Point(%s %s))", lng, lat));

       FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(filter);
       try (FeatureIterator<SimpleFeature> features = collection.features()) {
           UsState usState = new UsState();
           while (features.hasNext()) {
               SimpleFeature feature = features.next();
               usState.setName((String) feature.getAttribute(6));
               usState.setAbbr((String) feature.getAttribute(5));
           }
           return usState;
       }
   }
}
