package dataenrichment.services;

import dataenrichment.entities.Failure;
import dataenrichment.models.EnrichedTemperatureDataDTO;
import dataenrichment.repository.FailureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FailureService {

    @Autowired
    private FailureRepository failureRepository;

    @Autowired
    private DataEnrichmentService dataEnrichmentService;

    @Autowired
    private LocationService locationService;

    public List<Failure> getFailures() {
        return failureRepository.findAll();
    }

    public List<EnrichedTemperatureDataDTO> getEnrichedFailures() {
        List<Failure> failures = failureRepository.findAll();
        return dataEnrichmentService.enrich(failures);
    }
}
