package dataenrichment.services;

import dataenrichment.entities.Failure;
import dataenrichment.repository.FailureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FailureService {

    @Autowired
    private FailureRepository failureRepository;
    public List<Failure> getFailures() {
        return failureRepository.findAll();
    }
}
