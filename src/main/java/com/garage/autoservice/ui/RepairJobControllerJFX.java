package com.garage.autoservice.ui;

import com.garage.autoservice.entity.RepairJob;
import com.garage.autoservice.service.RepairJobService;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Контроллер для управления выполненными работами.
 * Предоставляет функционал для поиска, редактирования и удаления выполненных работ.
 */
@Component
public class RepairJobControllerJFX {

    private static final Logger logger = LoggerFactory.getLogger(RepairJobControllerJFX.class);

    @FXML
    private TableView<RepairJob> repairJobTable;

    @FXML
    private TableColumn<RepairJob, String> jobNameColumn;
    @FXML
    private TableColumn<RepairJob, Long> intervalInMileageColumn;
    @FXML
    private TableColumn<RepairJob, Long> intervalInHoursColumn;
    @FXML
    private TableColumn<RepairJob, Long> intervalInDaysColumn;
    @FXML
    private TableColumn<RepairJob, Long> lastMileageColumn;
    @FXML
    private TableColumn<RepairJob, String> lastJobDateColumn;
    @FXML
    private TableColumn<RepairJob, Long> lastHoursColumn;
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
    private TextField lastHoursField;
    @FXML
    private TextField serialNumberField;

    @FXML
    private TextField searchField;
    @FXML
    private Button editButton;

    @Autowired
    private RepairJobService repairJobService;

    private ObservableList<RepairJob> repairJobList;

    /**
     * Инициализация контроллера. Загружает список выполненных работ и связывает колонки таблицы с соответствующими полями.
     */
    public void initialize() {
        editButton.setDisable(true);
        repairJobList = FXCollections.observableArrayList();

        setupFieldListeners();
        loadRepairJobs();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        jobNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJobName()));
        intervalInMileageColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getIntervalInMileage()).asObject());
        intervalInHoursColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getIntervalInHours()).asObject());
        intervalInDaysColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getIntervalInDays()).asObject());
        lastMileageColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getLastMileage()).asObject());
        lastJobDateColumn.setCellValueFactory(cellData -> {
            LocalDate date = cellData.getValue().getLastJobDate();
            return new SimpleStringProperty(date != null ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "");
        });
        lastHoursColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getLastHours()).asObject());
        serialNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSerialNumber()));

        repairJobTable.setItems(repairJobList);
        repairJobTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateFields(newValue);
                setFieldsEditable(true);
            }
        });

        setFieldsEditable(true);
    }

    /**
     * Загрузка списка выполненных работ из базы данных.
     */
    @FXML
    private void loadRepairJobs() {
        repairJobList.setAll(repairJobService.findAll());
        logger.info("Загружено {} записей о выполненных работах из базы данных", repairJobList.size());
    }

    /**
     * Добавление новой ремонтной работы в базу данных и обновление таблицы.
     */
    @FXML
    private void addRepairJob() {
        try {
            if (fieldsAreEmpty()) {
                showAlert("Ошибка", "Все поля должны быть заполнены", Alert.AlertType.ERROR);
                return;
            }

            RepairJob repairJob = new RepairJob();
            repairJob.setJobName(jobNameField.getText());
            repairJob.setIntervalInMileage(Long.parseLong(intervalInMileageField.getText()));
            repairJob.setIntervalInHours(Long.parseLong(intervalInHoursField.getText()));
            repairJob.setIntervalInDays(Long.parseLong(intervalInDaysField.getText()));
            repairJob.setLastMileage(Long.parseLong(lastMileageField.getText()));
            repairJob.setLastJobDate(LocalDate.parse(lastJobDateField.getText()));
            repairJob.setLastHours(Long.parseLong(lastHoursField.getText()));
            repairJob.setSerialNumber(serialNumberField.getText());

            repairJobService.save(repairJob);
            logger.info("Ремонтная работа добавлена: {}", repairJob);

            loadRepairJobs();
            clearFields();

        } catch (Exception e) {
            showAlert("Ошибка", "Не удалось добавить ремонтную работу: " + e.getMessage(), Alert.AlertType.ERROR);
            logger.error("Ошибка при добавлении ремонтной работы", e);
        }
    }

    /**
     * Удаление выбранной работы из базы данных и обновление таблицы.
     */
    @FXML
    private void deleteJob() {
        RepairJob selectedJob = repairJobTable.getSelectionModel().getSelectedItem();
        if (selectedJob != null) {
            try {
                repairJobService.delete(selectedJob);
                logger.info("Работа удалена: {}", selectedJob);
                loadRepairJobs();  // Перезагрузка списка работ
                clearFields();     // Очистка полей после удаления
                showAlert("Информация", "Работа успешно удалена", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                logger.error("Ошибка при удалении работы", e);
                showAlert("Ошибка", "Не удалось удалить работу: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Предупреждение", "Пожалуйста, выберите работу для удаления", Alert.AlertType.WARNING);
        }
    }

    /**
     * Редактирование выбранной записи о выполненной работе.
     */
    @FXML
    private void editRepairJob() {
        RepairJob selectedJob = repairJobTable.getSelectionModel().getSelectedItem();
        if (selectedJob != null) {
            try {
                selectedJob.setJobName(jobNameField.getText());
                selectedJob.setIntervalInMileage(Long.parseLong(intervalInMileageField.getText()));
                selectedJob.setIntervalInHours(Long.parseLong(intervalInHoursField.getText()));
                selectedJob.setIntervalInDays(Long.parseLong(intervalInDaysField.getText()));
                selectedJob.setLastMileage(Long.parseLong(lastMileageField.getText()));
                selectedJob.setLastJobDate(LocalDate.parse(lastJobDateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                selectedJob.setLastHours(Long.parseLong(lastHoursField.getText()));
                selectedJob.setSerialNumber(serialNumberField.getText());

                repairJobService.save(selectedJob);
                loadRepairJobs();
                logger.info("Запись о выполненной работе обновлена: {}", selectedJob);

                showAlert("Информация", "Запись о выполненной работе успешно обновлена", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                logger.error("Ошибка при редактировании записи о выполненной работе", e);
                showAlert("Ошибка", "Не удалось обновить запись о выполненной работе: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Ошибка", "Не выбрана работа для редактирования", Alert.AlertType.ERROR);
        }
    }

    /**
     * Поиск записи о выполненной работе по серийному номеру.
     */
    @FXML
    private void searchRepairJob() {
        String serialNumber = searchField.getText();
        Optional<RepairJob> foundJob = repairJobService.findBySerialNumber(serialNumber);

        if (foundJob.isPresent()) {
            repairJobTable.getSelectionModel().select(foundJob.get());
            populateFields(foundJob.get());
            setFieldsEditable(true);
        } else {
            showAlert("Не найдено", "Запись с таким серийным номером не найдена", Alert.AlertType.INFORMATION);
            clearFields();
            setFieldsEditable(false);
        }
    }

    /**
     * Устанавливает поля как редактируемые или нет в зависимости от параметра.
     */
    private void setFieldsEditable(boolean editable) {
        jobNameField.setEditable(editable);
        intervalInMileageField.setEditable(editable);
        intervalInHoursField.setEditable(editable);
        intervalInDaysField.setEditable(editable);
        lastMileageField.setEditable(editable);
        lastJobDateField.setEditable(editable);
        lastHoursField.setEditable(editable);
        serialNumberField.setEditable(editable);
    }

    /**
     * Заполняет текстовые поля значениями из выбранной записи о выполненной работе.
     */
    private void populateFields(RepairJob job) {
        jobNameField.setText(job.getJobName());
        intervalInMileageField.setText(String.valueOf(job.getIntervalInMileage()));
        intervalInHoursField.setText(String.valueOf(job.getIntervalInHours()));
        intervalInDaysField.setText(String.valueOf(job.getIntervalInDays()));
        lastMileageField.setText(String.valueOf(job.getLastMileage()));
        lastJobDateField.setText(job.getLastJobDate().toString());
        lastHoursField.setText(String.valueOf(job.getLastHours()));
        serialNumberField.setText(job.getSerialNumber());

        searchField.setText(job.getSerialNumber());
    }

    /**
     * Проверка полей на изменения и активация кнопки редактирования.
     */
    private void setupFieldListeners() {
        ChangeListener<String> changeListener = (observable, oldValue, newValue) -> checkForChanges();

        jobNameField.textProperty().addListener(changeListener);
        intervalInMileageField.textProperty().addListener(changeListener);
        intervalInHoursField.textProperty().addListener(changeListener);
        intervalInDaysField.textProperty().addListener(changeListener);
        lastMileageField.textProperty().addListener(changeListener);
        lastJobDateField.textProperty().addListener(changeListener);
        lastHoursField.textProperty().addListener(changeListener);
        serialNumberField.textProperty().addListener(changeListener);
    }

    private void checkForChanges() {
        RepairJob selectedJob = repairJobTable.getSelectionModel().getSelectedItem();
        if (selectedJob != null) {
            boolean hasChanges =
                    !jobNameField.getText().equals(selectedJob.getJobName()) ||
                            !intervalInMileageField.getText().equals(String.valueOf(selectedJob.getIntervalInMileage())) ||
                            !intervalInHoursField.getText().equals(String.valueOf(selectedJob.getIntervalInHours())) ||
                            !intervalInDaysField.getText().equals(String.valueOf(selectedJob.getIntervalInDays())) ||
                            !lastMileageField.getText().equals(String.valueOf(selectedJob.getLastMileage())) ||
                            !lastJobDateField.getText().equals(selectedJob.getLastJobDate().toString()) ||
                            !lastHoursField.getText().equals(String.valueOf(selectedJob.getLastHours())) ||
                            !serialNumberField.getText().equals(selectedJob.getSerialNumber());

            editButton.setDisable(!hasChanges);
        } else {
            editButton.setDisable(true);
        }
    }

    /**
     * Очистка всех текстовых полей.
     */
    private void clearFields() {
        jobNameField.clear();
        intervalInMileageField.clear();
        intervalInHoursField.clear();
        intervalInDaysField.clear();
        lastMileageField.clear();
        lastJobDateField.clear();
        lastHoursField.clear();
        serialNumberField.clear();
        searchField.clear();
        logger.debug("Все поля ввода очищены");
    }
    /**
     * Проверяет, заполнены ли все обязательные поля.
     *
     * @return true, если хотя бы одно поле пустое; false, если все поля заполнены.
     */
    private boolean fieldsAreEmpty() {
        return jobNameField.getText().isEmpty() ||
                intervalInMileageField.getText().isEmpty() ||
                intervalInHoursField.getText().isEmpty() ||
                intervalInDaysField.getText().isEmpty() ||
                lastMileageField.getText().isEmpty() ||
                lastJobDateField.getText().isEmpty() ||
                lastHoursField.getText().isEmpty() ||
                serialNumberField.getText().isEmpty();
    }
    /**
     * Показать предупреждение.
     */
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
        logger.debug("Показано предупреждение: {} - {}", title, content);
    }
}
