package dataenrichment.services;

import dataenrichment.entities.Failure;
import dataenrichment.entities.Location;
import dataenrichment.models.EnrichedTemperatureDataDTO;
import dataenrichment.models.LocationDTO;
import dataenrichment.repository.FailureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FailureService {

    @Autowired
    private FailureRepository failureRepository;

    @Autowired
    private LocationService locationService;

    public List<Failure> getFailures() {
        return failureRepository.findAll();
    }

    public List<EnrichedTemperatureDataDTO> getEnrichedFailures() {
        List<Failure> failures = failureRepository.findAll();
        List<EnrichedTemperatureDataDTO> enrichedFailures = failures
            .stream()
            .map(failure -> {
                LocationDTO location = locationService.findBySensorName(failure.getSensorName());
                return new EnrichedTemperatureDataDTO(failure.getSensorName(), failure.getErrorValue(), location);
            })
            .collect(Collectors.toList());

        return enrichedFailures;
    }
}
