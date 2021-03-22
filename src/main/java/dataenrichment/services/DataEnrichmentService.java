package dataenrichment.services;

import common.TemperatureSensor;
import dataenrichment.models.EnrichedTemperatureDataDTO;
import dataenrichment.models.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class DataEnrichmentService {

    @Autowired
    private LocationService locationService;

    public DataEnrichmentService() {

    }

    public EnrichedTemperatureDataDTO enrich(TemperatureSensor data) {
        LocationDTO location = locationService.findBySensorName(data.getName());
        return new EnrichedTemperatureDataDTO(data.getName(), data.getValue(), location);
    }
}
