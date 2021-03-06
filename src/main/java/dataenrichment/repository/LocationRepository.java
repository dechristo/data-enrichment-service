package dataenrichment.repository;

import dataenrichment.entities.Location;
import dataenrichment.models.LocationDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
    Location findBySensorName(String sensorName);
    Location save(LocationDTO location);
    List<Location> findBySensorNameAndCity(String sensorName, String city);
    List<Location> findBySensorNameAndCountry(String sensorName, String country);
    List<Location> findAll();
}
