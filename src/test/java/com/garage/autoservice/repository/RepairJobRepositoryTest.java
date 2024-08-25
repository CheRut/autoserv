package com.garage.autoservice.repository;

import com.garage.autoservice.entity.RepairJob;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepairJobRepositoryTest {

    @Autowired
    private RepairJobRepository repairJobRepository;

    @Test
    public void testSaveAndFindById() {
        RepairJob repairJob = new RepairJob();
        repairJob.setJobName("Oil Change");
        repairJob.setIntervalInMileage(10000);
        repairJob.setIntervalInHours(1000);
        repairJob.setIntervalInDays(365);

        RepairJob savedJob = repairJobRepository.save(repairJob);
        Optional<RepairJob> foundJob = repairJobRepository.findById(savedJob.getId());

        assertThat(foundJob).isPresent();
        assertThat(foundJob.get().getJobName()).isEqualTo("Oil Change");
    }
}
