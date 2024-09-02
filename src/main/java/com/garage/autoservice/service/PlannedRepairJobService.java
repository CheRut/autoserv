package com.garage.autoservice.service;

import com.garage.autoservice.entity.PlannedRepairJob;
import com.garage.autoservice.repository.PlannedRepairJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlannedRepairJobService {

    @Autowired
    private PlannedRepairJobRepository plannedRepairJobRepository;

    public List<PlannedRepairJob> findAllPlannedJobs() {
        return plannedRepairJobRepository.findAll();
    }

    public void save(PlannedRepairJob job) {
        plannedRepairJobRepository.save(job);
    }

    public void delete(PlannedRepairJob job) {
        plannedRepairJobRepository.delete(job);
    }
}
