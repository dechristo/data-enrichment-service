package dataenrichment.services;

import dataenrichment.entities.Failure;
import dataenrichment.models.EnrichedTemperatureDataDTO;
import dataenrichment.models.LocationDTO;
import dataenrichment.repository.FailureRepository;
import org.aspectj.lang.annotation.Before;
import org.checkerframework.common.value.qual.MinLenFieldInvariant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FailureServiceTest {

    @InjectMocks
    private FailureService service;

    @Mock
    private FailureRepository failureRepository;

    @Mock
    private DataEnrichmentService dataEnrichmentService;

    @Test
    public void getFailuresShouldReturnFailuresWithEnrichedData() {
        List<Failure> failuresMock = new LinkedList<Failure>();
        failuresMock.add(new Failure((long)1, "unittestsensor", 30));
        List<EnrichedTemperatureDataDTO> mockEnrichedData = new LinkedList<EnrichedTemperatureDataDTO>();
        mockEnrichedData.add(new EnrichedTemperatureDataDTO("sensor-xy122", 0, new LocationDTO("Miami", "USA")));
        when(failureRepository.findAll()).thenReturn(failuresMock);
        when(dataEnrichmentService.enrich(anyList())).thenReturn(mockEnrichedData);

        List<EnrichedTemperatureDataDTO> result = service.getEnrichedFailures();

        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getLocation().getCity(), "Miami");
        assertEquals(result.get(0).getLocation().getCountry(), "USA");
        assertEquals(result.get(0).getName(), "sensor-xy122");
        assertEquals(result.get(0).getValue(), 0);
    }
}
