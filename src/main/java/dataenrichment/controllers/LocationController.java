package dataenrichment.controllers;

import dataenrichment.entities.Location;
import dataenrichment.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationController {
    @Autowired
    LocationService service;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "oi";
    }

    @GetMapping("/locations")
    public List<Location> getAll() {

        return service.findAll();
    }
}