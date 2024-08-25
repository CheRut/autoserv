package com.garage.autoservice.repository;

import com.garage.autoservice.entity.RepairJob;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@Transactional
public class RepairJobRepositoryTest {

    @Autowired
    private RepairJobRepository repairJobRepository;

    @Test
    void testCreateRepairJob() {
        RepairJob repairJob = new RepairJob();
        repairJob.setJobName("Oil Change");
        repairJob.setIntervalInDays(180);
        repairJob.setIntervalInMileage(10000);

        RepairJob savedRepairJob = repairJobRepository.save(repairJob);

        assertThat(savedRepairJob).isNotNull();
        assertThat(savedRepairJob.getId()).isNotNull();
        assertThat(savedRepairJob.getJobName()).isEqualTo("Oil Change");
    }

    @Test
    void testFindRepairJobById() {
        RepairJob repairJob = new RepairJob();
        repairJob.setJobName("Brake Inspection");
        repairJob.setIntervalInDays(365);
        repairJob.setIntervalInMileage(20000);

        RepairJob savedRepairJob = repairJobRepository.save(repairJob);

        Optional<RepairJob> foundRepairJob = repairJobRepository.findById(savedRepairJob.getId());

        assertThat(foundRepairJob).isPresent();
        assertThat(foundRepairJob.get().getJobName()).isEqualTo("Brake Inspection");
    }

    @Test
    void testFindAllRepairJobs() {
        RepairJob repairJob1 = new RepairJob();
        repairJob1.setJobName("Oil Change");
        repairJob1.setIntervalInDays(180);
        repairJob1.setIntervalInMileage(10000);

        RepairJob repairJob2 = new RepairJob();
        repairJob2.setJobName("Brake Inspection");
        repairJob2.setIntervalInDays(365);
        repairJob2.setIntervalInMileage(20000);

        repairJobRepository.save(repairJob1);
        repairJobRepository.save(repairJob2);

        List<RepairJob> repairJobs = repairJobRepository.findAll();

        assertThat(repairJobs).hasSize(2);
        assertThat(repairJobs.get(0).getJobName()).isEqualTo("Oil Change");
        assertThat(repairJobs.get(1).getJobName()).isEqualTo("Brake Inspection");
    }

    @Test
    void testUpdateRepairJob() {
        RepairJob repairJob = new RepairJob();
        repairJob.setJobName("Oil Change");
        repairJob.setIntervalInDays(180);
        repairJob.setIntervalInMileage(10000);

        RepairJob savedRepairJob = repairJobRepository.save(repairJob);

        savedRepairJob.setJobName("Brake Inspection");
        savedRepairJob.setIntervalInDays(365);
        savedRepairJob.setIntervalInMileage(20000);

        RepairJob updatedRepairJob = repairJobRepository.save(savedRepairJob);

        assertThat(updatedRepairJob.getJobName()).isEqualTo("Brake Inspection");
        assertThat(updatedRepairJob.getIntervalInDays()).isEqualTo(365);
        assertThat(updatedRepairJob.getIntervalInMileage()).isEqualTo(20000);
    }

    @Test
    void testDeleteRepairJob() {
        RepairJob repairJob = new RepairJob();
        repairJob.setJobName("Oil Change");
        repairJob.setIntervalInDays(180);
        repairJob.setIntervalInMileage(10000);

        RepairJob savedRepairJob = repairJobRepository.save(repairJob);

        repairJobRepository.delete(savedRepairJob);

        Optional<RepairJob> deletedRepairJob = repairJobRepository.findById(savedRepairJob.getId());

        assertThat(deletedRepairJob).isNotPresent();
    }
}
