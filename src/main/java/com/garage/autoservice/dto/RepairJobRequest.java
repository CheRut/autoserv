package com.garage.autoservice.dto;

import com.garage.autoservice.entity.Part;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO для передачи данных о ремонтных заданиях.
 */
@Data
public class RepairJobRequest {

    private String jobName;
    private long intervalInMileage;
    private long intervalInHours;
    private long intervalInDays;
    private List<PartRequest> requiredParts;
    private long lastMileage;
    private LocalDate lastJobDate;
}
