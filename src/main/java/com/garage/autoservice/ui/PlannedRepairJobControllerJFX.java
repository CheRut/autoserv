package com.garage.autoservice.ui;

import com.garage.autoservice.entity.PlannedRepairJob;
import com.garage.autoservice.service.PlannedRepairJobService;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PlannedRepairJobControllerJFX {

    private static final Logger logger = LoggerFactory.getLogger(PlannedRepairJobControllerJFX.class);

    @FXML
    private TableView<PlannedRepairJob> plannedRepairJobTable;

    @FXML
    private TableColumn<PlannedRepairJob, String> jobNameColumn;
    @FXML
    private TableColumn<PlannedRepairJob, String> cardNumberColumn;
    @FXML
    private TableColumn<PlannedRepairJob, String> fluidTypeColumn;
    @FXML
    private TableColumn<PlannedRepairJob, String> serialNumberColumn;
    @FXML
    private TableColumn<PlannedRepairJob, Long> remainingMileageColumn;
    @FXML
    private TableColumn<PlannedRepairJob, String> remainingTimeColumn;
    @FXML
    private TableColumn<PlannedRepairJob, String> orderNumberColumn;
    @FXML
    private TableColumn<PlannedRepairJob, String> notesColumn;

    @FXML
    private TextField jobNameField;
    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField fluidTypeField;
    @FXML
    private TextField serialNumberField;
    @FXML
    private TextField remainingMileageField;
    @FXML
    private TextField remainingTimeField; // количество дней
    @FXML
    private TextField orderNumberField;
    @FXML
    private TextArea notesField;

    @FXML
    private CheckBox inParkCheckBox;
    @FXML
    private Button completeJobButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;

    @Autowired
    private PlannedRepairJobService plannedRepairJobService;

    private ObservableList<PlannedRepairJob> plannedRepairJobList;

    @FXML
    public void initialize() {
        plannedRepairJobList = FXCollections.observableArrayList();
        loadPlannedRepairJobs();

        jobNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJobName()));
        cardNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCardNumber()));
        fluidTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFluidType()));
        serialNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSerialNumber()));
        remainingMileageColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getRemainingMileage()).asObject());
        remainingTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRemainingTime() != null ? cellData.getValue().getRemainingTime().toString() : ""));
        orderNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderNumber()));
        notesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNotes()));

        plannedRepairJobTable.setItems(plannedRepairJobList);
    }

    private void loadPlannedRepairJobs() {
        plannedRepairJobList.setAll(plannedRepairJobService.findAllPlannedJobs());
    }

    @FXML
    private void addPlannedRepairJob() {
        try {
            // Проверяем, что обязательные поля заполнены
            if (jobNameField.getText().isEmpty() || serialNumberField.getText().isEmpty()) {
                showAlert("Ошибка", "Поля 'Наименование работы' и 'Serial Number' должны быть заполнены", Alert.AlertType.ERROR);
                return;
            }

            // Проверяем, что orderNumberField заполнено, если remaining поля пустые и чекбокс не отмечен
            boolean isRemainingFieldsEmpty = remainingMileageField.getText().isEmpty() && remainingTimeField.getText().isEmpty();
            if (isRemainingFieldsEmpty && !inParkCheckBox.isSelected() && orderNumberField.getText().isEmpty()) {
                showAlert("Ошибка", "Поле 'Order Number' должно быть заполнено, если поля 'Remaining Mileage' и 'Remaining Time' пустые и чекбокс 'Работа в автопарке' не отмечен", Alert.AlertType.ERROR);
                return;
            }

            PlannedRepairJob job = new PlannedRepairJob();
            job.setJobName(jobNameField.getText());
            job.setSerialNumber(serialNumberField.getText());
            job.setCardNumber(cardNumberField.getText());
            job.setFluidType(fluidTypeField.getText());
            job.setOrderNumber(orderNumberField.getText());
            job.setNotes(notesField.getText());
            job.setInPark(inParkCheckBox.isSelected());

            if (!remainingMileageField.getText().isEmpty()) {
                job.setRemainingMileage(Long.parseLong(remainingMileageField.getText()));
            }

            if (!remainingTimeField.getText().isEmpty()) {
                long remainingDays = Long.parseLong(remainingTimeField.getText());
                LocalDate remainingTime = LocalDate.now().plusDays(remainingDays);
                job.setRemainingTime(remainingTime);
            }

            plannedRepairJobService.save(job);
            loadPlannedRepairJobs();
            clearFields();

        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Некорректный ввод числового значения.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Ошибка", "Не удалось добавить запланированную работу: " + e.getMessage(), Alert.AlertType.ERROR);
            logger.error("Ошибка при добавлении запланированной работы", e);
        }
    }

    @FXML
    private void deletePlannedRepairJob() {
        PlannedRepairJob selectedJob = plannedRepairJobTable.getSelectionModel().getSelectedItem();
        if (selectedJob != null) {
            try {
                plannedRepairJobService.delete(selectedJob);
                loadPlannedRepairJobs();  // Перезагружаем список после удаления
                clearFields();  // Очищаем поля после удаления
                logger.info("Удалена запланированная работа: {}", selectedJob);
            } catch (Exception e) {
                logger.error("Ошибка при удалении запланированной работы", e);
                showAlert("Ошибка", "Не удалось удалить запланированную работу: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Предупреждение", "Пожалуйста, выберите работу для удаления", Alert.AlertType.WARNING);
        }
    }
    @FXML
    private void editPlannedRepairJob() {
        PlannedRepairJob selectedJob = plannedRepairJobTable.getSelectionModel().getSelectedItem();
        if (selectedJob != null) {
            try {
                selectedJob.setJobName(jobNameField.getText());
                selectedJob.setCardNumber(cardNumberField.getText());
                selectedJob.setFluidType(fluidTypeField.getText());
                selectedJob.setSerialNumber(serialNumberField.getText());
                selectedJob.setRemainingMileage(Long.parseLong(remainingMileageField.getText()));
                selectedJob.setRemainingTime(LocalDate.parse(remainingTimeField.getText()));
                selectedJob.setOrderNumber(orderNumberField.getText());
                selectedJob.setNotes(notesField.getText());
                selectedJob.setInPark(inParkCheckBox.isSelected());

                plannedRepairJobService.save(selectedJob);
                loadPlannedRepairJobs();  // Перезагружаем список после редактирования
                clearFields();  // Очищаем поля после редактирования
                logger.info("Обновлена запланированная работа: {}", selectedJob);
            } catch (Exception e) {
                logger.error("Ошибка при редактировании запланированной работы", e);
                showAlert("Ошибка", "Не удалось обновить запланированную работу: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Ошибка", "Не выбрана работа для редактирования", Alert.AlertType.ERROR);
        }
    }
    private void clearFields() {
        jobNameField.clear();
        cardNumberField.clear();
        fluidTypeField.clear();
        serialNumberField.clear();
        remainingMileageField.clear();
        remainingTimeField.clear();
        orderNumberField.clear();
        notesField.clear();
        inParkCheckBox.setSelected(false);
        logger.debug("Поля ввода очищены");
    }
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
        logger.debug("Показано предупреждение: {} - {}", title, content);
    }

}
