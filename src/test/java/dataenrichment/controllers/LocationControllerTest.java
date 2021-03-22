package dataenrichment.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dataenrichment.entities.Location;
import dataenrichment.services.LocationService;
import dataenrichment.services.TemperatureService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

@WebMvcTest(LocationController.class)
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationService service;

    @MockBean
    private TemperatureService temperatureService;

    @Test
    public void getAllgreetingShouldReturnAllLocationsService() throws Exception {
        List<Location> locations = new LinkedList<>();
        locations.add(new Location((long)1, "USA", "Miami", "sensor-mia1"));
        locations.add(new Location((long)2, "Finland", "Helsinki", "sensor-mia1"));

        when(service.findAll()).thenReturn(locations);
        this.mockMvc.perform(get("/locations")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello, Mock")));
    }
}