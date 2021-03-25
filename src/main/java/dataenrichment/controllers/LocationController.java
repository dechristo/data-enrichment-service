package dataenrichment.controllers;

import dataenrichment.entities.Location;
import dataenrichment.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class LocationController {
    @Autowired
    LocationService service;

    @GetMapping("/locations")
    public List<Location> getAll() {
        return service.findAll();
    }

    @PostMapping("/location")
    public void create(@Valid @RequestBody Location location) {
        service.create(location);
    }
}