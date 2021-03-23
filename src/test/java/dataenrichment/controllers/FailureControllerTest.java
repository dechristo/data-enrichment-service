package dataenrichment.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dataenrichment.entities.Failure;
import dataenrichment.models.EnrichedTemperatureDataDTO;
import dataenrichment.models.LocationDTO;
import dataenrichment.services.FailureService;
import dataenrichment.services.TemperatureMonitorService;
import org.apache.pulsar.shade.com.google.common.net.MediaType;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.mail.internet.ContentType;
import java.util.*;

@WebMvcTest(FailureController.class)
public class FailureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FailureService service;

    @MockBean
    private TemperatureMonitorService temperatureMonitorService;

    @Test
    public void getFailuresShouldReturnListOfFailures() throws Exception {
        List<Failure> failures = new LinkedList<>();
        failures.add(new Failure((long)1, "sensor-cool", 25));
        failures.add(new Failure((long)2, "hot sensor 1680", 37));

        when(service.getFailures()).thenReturn(failures);
        this.mockMvc.perform(get("/failures"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json("[{\"id\":1,\"sensorName\":\"sensor-cool\",\"errorValue\":25.0},{\"id\":2,\"sensorName\":\"hot sensor 1680\",\"errorValue\":37.0}]"));
    }

    @Test
    public void getEnrichedFailuresShouldReturnListOfEnrichedFailures() throws Exception {
        List<EnrichedTemperatureDataDTO> enrichedData = new LinkedList<>();
        enrichedData.add(new EnrichedTemperatureDataDTO("sensor-xy122", 0, new LocationDTO("Miami", "USA")));
        enrichedData.add(new EnrichedTemperatureDataDTO("sensor-3po", 30, new LocationDTO("Denver", "USA")));

        when(service.getEnrichedFailures()).thenReturn(enrichedData);
        this.mockMvc.perform(get("/enriched-failures"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"));
    }
}