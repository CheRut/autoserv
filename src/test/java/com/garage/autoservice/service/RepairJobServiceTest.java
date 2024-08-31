package com.garage.autoservice.service;

import com.garage.autoservice.dto.PartRequest;
import com.garage.autoservice.dto.RepairJobRequest;
import com.garage.autoservice.entity.Part;
import com.garage.autoservice.entity.RepairJob;
import com.garage.autoservice.exception.InvalidRequestException;
import com.garage.autoservice.exception.ResourceNotFoundException;
import com.garage.autoservice.repository.PartRepository;
import com.garage.autoservice.repository.RepairJobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepairJobServiceTest {

    @Mock
    private RepairJobRepository repairJobRepository;

    @Mock
    private PartRepository partRepository;

    @InjectMocks
    private RepairJobService repairJobService;

    private RepairJobRequest validRequest;
    private RepairJob repairJob;
    private Part part;

    @BeforeEach
    void setUp() {
        part = new Part();
        part.setId(1L);
        part.setName("Oil Filter");
        part.setManufacturer("OEM");
        part.setPartNumber("OF123");
        part.setQuantity(10);
        part.setCardNumber(12345);
        part.setVin("VIN123");

        PartRequest partRequest = new PartRequest();
        partRequest.setName("Oil Filter");
        partRequest.setManufacturer("OEM");
        partRequest.setPartNumber("OF123");
        partRequest.setQuantity(1);
        partRequest.setCardNumber(12345);
        partRequest.setVin("VIN123");

        validRequest = new RepairJobRequest();
        validRequest.setJobName("Oil Change");
        validRequest.setIntervalInMileage(10000);
        validRequest.setIntervalInHours(500);
        validRequest.setIntervalInDays(365);
        validRequest.setLastMileage(5000);
        validRequest.setLastJobDate(LocalDate.now().minusDays(180));
        validRequest.setSerialNumber("VIN123");
        validRequest.setRequiredParts(Collections.singletonList(partRequest));

        repairJob = new RepairJob();
        repairJob.setId(1L);
        repairJob.setJobName("Oil Change");
        repairJob.setIntervalInMileage(10000L);
        repairJob.setIntervalInHours(500L);
        repairJob.setIntervalInDays(365L);
        repairJob.setLastMileage(5000L);
        repairJob.setLastJobDate(LocalDate.now().minusDays(180));
        repairJob.setSerialNumber("VIN123");
        repairJob.setRequiredParts(Collections.singletonList(part));
    }

    @Test
    void createRepairJob_ShouldReturnSavedRepairJob_WhenRequestIsValid() {
        when(partRepository.save(any(Part.class))).thenReturn(part);
        when(repairJobRepository.save(any(RepairJob.class))).thenReturn(repairJob);

        RepairJob result = repairJobService.createRepairJob(validRequest);

        assertNotNull(result);
        assertEquals(repairJob.getJobName(), result.getJobName());
        assertEquals(repairJob.getId(), result.getId());
        verify(repairJobRepository, times(1)).save(any(RepairJob.class));
    }

    @Test
    void createRepairJob_ShouldThrowException_WhenSavingFails() {
        when(partRepository.save(any(Part.class))).thenReturn(part);
        when(repairJobRepository.save(any(RepairJob.class))).thenThrow(new DataIntegrityViolationException("Error saving"));

        assertThrows(IllegalStateException.class, () -> repairJobService.createRepairJob(validRequest));
        verify(repairJobRepository, times(1)).save(any(RepairJob.class));
    }

    @Test
    void getRepairJobById_ShouldReturnRepairJob_WhenIdExists() {
        when(repairJobRepository.findById(anyLong())).thenReturn(Optional.of(repairJob));

        RepairJob result = repairJobService.getRepairJobById(1L);

        assertNotNull(result);
        assertEquals(repairJob.getId(), result.getId());
        verify(repairJobRepository, times(1)).findById(1L);
    }

    @Test
    void getRepairJobById_ShouldThrowException_WhenIdDoesNotExist() {
        when(repairJobRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> repairJobService.getRepairJobById(1L));
        verify(repairJobRepository, times(1)).findById(1L);
    }

    @Test
    void updateRepairJob_ShouldReturnUpdatedRepairJob_WhenRequestIsValid() {
        when(repairJobRepository.findById(anyLong())).thenReturn(Optional.of(repairJob));
        when(partRepository.save(any(Part.class))).thenReturn(part);
        when(repairJobRepository.save(any(RepairJob.class))).thenReturn(repairJob);

        RepairJob result = repairJobService.updateRepairJob(1L, validRequest);

        assertNotNull(result);
        assertEquals(repairJob.getJobName(), result.getJobName());
        verify(repairJobRepository, times(1)).findById(1L);
        verify(repairJobRepository, times(1)).save(any(RepairJob.class));
    }

    @Test
    void updateRepairJob_ShouldThrowException_WhenSavingFails() {
        when(repairJobRepository.findById(anyLong())).thenReturn(Optional.of(repairJob));
        when(partRepository.save(any(Part.class))).thenReturn(part);
        when(repairJobRepository.save(any(RepairJob.class))).thenThrow(new DataIntegrityViolationException("Error saving"));

        assertThrows(InvalidRequestException.class, () -> repairJobService.updateRepairJob(1L, validRequest));
        verify(repairJobRepository, times(1)).findById(1L);
        verify(repairJobRepository, times(1)).save(any(RepairJob.class));
    }

    @Test
    void getAllRepairJobs_ShouldReturnListOfRepairJobs() {
        when(repairJobRepository.findAll()).thenReturn(Collections.singletonList(repairJob));

        List<RepairJob> result = repairJobService.getAllRepairJobs();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(repairJobRepository, times(1)).findAll();
    }

    @Test
    void getJobsForVehicleInPeriod_ShouldReturnListOfRepairJobs_WhenJobsExist() {
        when(repairJobRepository.findAllBySerialNumberAndLastJobDateBetween(anyString(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Collections.singletonList(repairJob));

        List<RepairJob> result = repairJobService.getJobsForVehicleInPeriod("VIN123", LocalDate.now().minusDays(365), LocalDate.now());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(repairJobRepository, times(1))
                .findAllBySerialNumberAndLastJobDateBetween("VIN123", LocalDate.now().minusDays(365), LocalDate.now());
    }

    @Test
    void getJobsForVehicleInPeriod_ShouldReturnEmptyList_WhenNoJobsExist() {
        when(repairJobRepository.findAllBySerialNumberAndLastJobDateBetween(anyString(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());

        List<RepairJob> result = repairJobService.getJobsForVehicleInPeriod("VIN123", LocalDate.now().minusDays(365), LocalDate.now());

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repairJobRepository, times(1))
                .findAllBySerialNumberAndLastJobDateBetween("VIN123", LocalDate.now().minusDays(365), LocalDate.now());
    }
}
