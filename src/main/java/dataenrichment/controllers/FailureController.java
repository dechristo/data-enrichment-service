package dataenrichment.controllers;

import dataenrichment.entities.Failure;
import dataenrichment.models.EnrichedTemperatureDataDTO;
import dataenrichment.services.FailureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class FailureController {

    @Autowired
    FailureService failureService;

    @GetMapping("/failures")
    public List<Failure> getFailures() {
        return failureService.getFailures();
    }

    @GetMapping("/enriched-failures")
    public List<EnrichedTemperatureDataDTO> getEnrichedFailures() { return failureService.getEnrichedFailures();}
}
