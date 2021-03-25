package dataenrichment.services;

import dataenrichment.entities.Location;
import dataenrichment.models.LocationDTO;
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

    public LocationDTO findBySensorName(String sensorName) {
        Location foundLocation = (Location) repository.findBySensorName(sensorName);

        if (foundLocation != null) {
            return new LocationDTO(foundLocation.getCity(), foundLocation.getCountry());
        }

        return null;
    }

    public void create(Location location) {
        this.repository.save(location);
    }
}
