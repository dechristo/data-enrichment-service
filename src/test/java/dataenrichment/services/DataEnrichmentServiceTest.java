package dataenrichment.services;

import dataenrichment.entities.Failure;
import dataenrichment.models.EnrichedTemperatureDataDTO;
import dataenrichment.models.LocationDTO;
import dataenrichment.repository.FailureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class DataEnrichmentServiceTest {

    @InjectMocks
    private DataEnrichmentService service;

    @Mock
    private LocationService locationService;

    @Test
    public void enrichShouldReturnFailuresWithEnrichedDataForFailuresList() {
        List<Failure> failuresMock = new LinkedList<Failure>();
        when(locationService.findBySensorName("unittestsensor")).thenReturn(new LocationDTO("Cancun", "Mexico"));

        failuresMock.add(new Failure((long)1, "unittestsensor", 30));
        List<EnrichedTemperatureDataDTO> result = service.enrich(failuresMock);

        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getValue(), 30);
        assertEquals(result.get(0).getName(), "unittestsensor");
        assertEquals(result.get(0).getLocation().getCountry(), "Mexico");
        assertEquals(result.get(0).getLocation().getCity(), "Cancun");
    }

    @Test
    public void enrichShouldReturnFailureWithEnrichedDataForSingleFailure() {
        Failure failureMock = new Failure((long)2, "unittestsensor2", 24);
        when(locationService.findBySensorName("unittestsensor2")).thenReturn(new LocationDTO("Brussels", "Belgium"));

        EnrichedTemperatureDataDTO result = service.enrich(failureMock);
        assertEquals(result.getValue(), 24);
        assertEquals(result.getName(), "unittestsensor2");
        assertEquals(result.getLocation().getCountry(), "Belgium");
        assertEquals(result.getLocation().getCity(), "Brussels");

    }
}
