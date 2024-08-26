package com.garage.autoservice.service;

import com.garage.autoservice.dto.PartRequest;
import com.garage.autoservice.dto.RepairJobRequest;
import com.garage.autoservice.entity.Part;
import com.garage.autoservice.entity.RepairJob;
import com.garage.autoservice.repository.PartRepository;
import com.garage.autoservice.repository.RepairJobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RepairJobServiceTest {

    @Mock
    private PartRepository partRepository;

    @Mock
    private RepairJobRepository repairJobRepository;

    @InjectMocks
    private RepairJobService repairJobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRepairJobWithNewPart() {
        PartRequest partRequest = new PartRequest();
        partRequest.setName("Oil Filter");
        partRequest.setManufacturer("Bosch");
        partRequest.setPartNumber("OF123");
        partRequest.setQuantity(10);
        partRequest.setVin("123VIN");

        RepairJobRequest request = new RepairJobRequest();
        request.setJobName("Oil Change");
        request.setIntervalInMileage(10000);
        request.setIntervalInHours(1000);
        request.setIntervalInDays(365);
        request.setRequiredParts(Arrays.asList(partRequest));
        request.setLastMileage(9000);
        request.setLastJobDate(LocalDate.now().minusDays(30));

        Part newPart = new Part();
        newPart.setName("Oil Filter");
        newPart.setManufacturer("Bosch");
        newPart.setPartNumber("OF123");
        newPart.setQuantity(10);
        newPart.setVin("123VIN");

        when(partRepository.findByNameAndManufacturerAndPartNumber("Oil Filter", "Bosch", "OF123"))
                .thenReturn(Optional.empty());
        when(partRepository.save(any(Part.class))).thenReturn(newPart);
        when(repairJobRepository.save(any(RepairJob.class))).thenAnswer(invocation -> {
            RepairJob repairJob = invocation.getArgument(0);
            repairJob.setId(1L);
            return repairJob;
        });

        RepairJob result = repairJobService.createRepairJob(request);

        verify(partRepository, times(1)).save(any(Part.class));
        assertEquals(1, result.getRequiredParts().size());
        assertEquals("Oil Filter", result.getRequiredParts().get(0).getName());
    }
}