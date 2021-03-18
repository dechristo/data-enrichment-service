package dataenrichment.services;

import dataenrichment.entities.Location;
import dataenrichment.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository repository;

    public List<Location> findAll() {

        var locations = (List<Location>) repository.findAll();

        return locations;
    }
}
