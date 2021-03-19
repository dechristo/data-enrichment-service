package dataenrichment.repository;

import dataenrichment.entities.Failure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FailureRepository extends CrudRepository<Failure, Long> {
    List<Failure> findBySensorNameAndErrorValue(String sensorName, double errorValue);
    List<Failure> findAll();
}