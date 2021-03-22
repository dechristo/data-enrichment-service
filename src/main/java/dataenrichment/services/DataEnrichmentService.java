package dataenrichment.services;

import common.TemperatureSensor;
import dataenrichment.entities.Failure;
import dataenrichment.models.EnrichedTemperatureDataDTO;
import dataenrichment.models.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataEnrichmentService {

    @Autowired
    private LocationService locationService;

    public DataEnrichmentService() {

    }

    public List<EnrichedTemperatureDataDTO> enrich(List<Failure> failures) {
       List<EnrichedTemperatureDataDTO> enrichedFailures = failures
            .stream()
            .map(failure -> {
                LocationDTO location = locationService.findBySensorName(failure.getSensorName());
                return new EnrichedTemperatureDataDTO(failure.getSensorName(), failure.getErrorValue(), location);
            })
            .collect(Collectors.toList());

       return enrichedFailures;
    }

    public EnrichedTemperatureDataDTO enrich(Failure failure) {
        LocationDTO location = locationService.findBySensorName(failure.getSensorName());
        return new EnrichedTemperatureDataDTO(failure.getSensorName(), failure.getErrorValue(), location);
    }
}
