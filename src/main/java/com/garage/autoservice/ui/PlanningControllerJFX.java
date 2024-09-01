package com.garage.autoservice.ui;

import com.garage.autoservice.entity.RepairJob;
import com.garage.autoservice.service.RepairJobService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Контроллер для управления окном планирования ремонтных работ.
 * Предоставляет функционал для добавления, удаления и редактирования ремонтных работ.
 */
@Component
public class PlanningControllerJFX {

    @FXML
    private TableView<RepairJob> repairJobTable;

    @FXML
    private TableColumn<RepairJob, String> jobNameColumn;

    @FXML
    private TableColumn<RepairJob, String> intervalInMileageColumn;

    @FXML
    private TableColumn<RepairJob, String> intervalInHoursColumn;

    @FXML
    private TableColumn<RepairJob, String> intervalInDaysColumn;

    @FXML
    private TableColumn<RepairJob, String> lastMileageColumn;

    @FXML
    private TableColumn<RepairJob, String> lastJobDateColumn;

    @FXML
    private TableColumn<RepairJob, String> serialNumberColumn;

    @FXML
    private TextField jobNameField;

    @FXML
    private TextField intervalInMileageField;

    @FXML
    private TextField intervalInHoursField;

    @FXML
    private TextField intervalInDaysField;

    @FXML
    private TextField lastMileageField;

    @FXML
    private TextField lastJobDateField;

    @FXML
    private TextField serialNumberField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @Autowired
    private RepairJobService repairJobService;

    private ObservableList<RepairJob> repairJobList;

    /**
     * Инициализация контроллера. Загружает данные и настраивает таблицу.
     */
    @FXML
    public void initialize() {
        repairJobList = FXCollections.observableArrayList();
        loadRepairJobs();

        jobNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getJobName()));
        intervalInMileageColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIntervalInMileage().toString()));
        intervalInHoursColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIntervalInHours().toString()));
        intervalInDaysColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIntervalInDays().toString()));
        lastMileageColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLastMileage().toString()));
        lastJobDateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLastJobDate().format(DateTimeFormatter.ISO_DATE)));
        serialNumberColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSerialNumber()));

        repairJobTable.setItems(repairJobList);
    }

    /**
     * Загружает список всех ремонтных работ из базы данных.
     */
    private void loadRepairJobs() {
        List<RepairJob> jobs = repairJobService.findAll();
        repairJobList.setAll(jobs);
    }

    /**
     * Добавление новой ремонтной работы.
     */
    @FXML
    private void addRepairJob() {
        RepairJob job = new RepairJob();
        job.setJobName(jobNameField.getText());
        job.setIntervalInMileage(Long.parseLong(intervalInMileageField.getText()));
        job.setIntervalInHours(Long.parseLong(intervalInHoursField.getText()));
        job.setIntervalInDays(Long.parseLong(intervalInDaysField.getText()));
        job.setLastMileage(Long.parseLong(lastMileageField.getText()));
        job.setLastJobDate(LocalDate.parse(lastJobDateField.getText(), DateTimeFormatter.ISO_DATE));
        job.setSerialNumber(serialNumberField.getText());

        repairJobService.save(job);
        loadRepairJobs();
    }

    /**
     * Удаление выбранной ремонтной работы.
     */
    @FXML
    private void deleteRepairJob() {
        RepairJob selectedJob = repairJobTable.getSelectionModel().getSelectedItem();
        if (selectedJob != null) {
            repairJobService.delete(selectedJob);
            loadRepairJobs();
        }
    }

    /**
     * Редактирование выбранной ремонтной работы.
     */
    @FXML
    private void editRepairJob() {
        RepairJob selectedJob = repairJobTable.getSelectionModel().getSelectedItem();
        if (selectedJob != null) {
            selectedJob.setJobName(jobNameField.getText());
            selectedJob.setIntervalInMileage(Long.parseLong(intervalInMileageField.getText()));
            selectedJob.setIntervalInHours(Long.parseLong(intervalInHoursField.getText()));
            selectedJob.setIntervalInDays(Long.parseLong(intervalInDaysField.getText()));
            selectedJob.setLastMileage(Long.parseLong(lastMileageField.getText()));
            selectedJob.setLastJobDate(LocalDate.parse(lastJobDateField.getText(), DateTimeFormatter.ISO_DATE));
            selectedJob.setSerialNumber(serialNumberField.getText());

            repairJobService.save(selectedJob);
            loadRepairJobs();
        }
    }
}

