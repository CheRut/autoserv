package com.garage.autoservice.ui;

import com.garage.autoservice.entity.*;
import com.garage.autoservice.service.CarService;
import com.garage.autoservice.service.PartService;
import com.garage.autoservice.service.PlannedRepairJobService;
import com.garage.autoservice.service.FluidService;
import com.garage.autoservice.service.RepairJobService;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
public class PlannedRepairJobControllerJFX {

    private static final Logger logger = LoggerFactory.getLogger(PlannedRepairJobControllerJFX.class);

    @FXML
    private TableView<PlannedRepairJob> plannedRepairJobTable;

    @FXML
    private TableColumn<PlannedRepairJob, String> jobNameColumn;

    @FXML
    private TableColumn<PlannedRepairJob, String> partCardNumberColumn; // Столбец для cardNumber запчасти
    @FXML
    private TableColumn<PlannedRepairJob, String> partNameColumn;       // Столбец для наименования запчасти
    @FXML
    private TableColumn<PlannedRepairJob, Integer> partQuantityColumn;
    @FXML
    private TableColumn<PlannedRepairJob, String> fluidCardNumberColumn; // Столбец для cardNumber жидкости
    @FXML
    private TableColumn<PlannedRepairJob, String> fluidNameColumn;      // Столбец для наименования жидкости
    @FXML
    private TableColumn<PlannedRepairJob, Float> fluidVolumeColumn;
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
    private TableColumn<PlannedRepairJob, String> jobTypeColumn;

    @FXML
    private TextField jobNameField;
    @FXML
    private TextField partCardNumberField;
    @FXML
    private TextField partNameField;
    @FXML
    private TextField partQuantityField;
    @FXML
    private TextField fluidCardNumberField;
    @FXML
    private TextField fluidNameField;
    @FXML
    private TextField fluidVolumeField;
    @FXML
    private TextField serialNumberField;

    @FXML
    private TextField orderNumberField;
    @FXML
    private TextArea notesField;
    @FXML
    private ComboBox<String> jobTypeComboBox;

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

    @Autowired
    private PartService partService;

    @Autowired
    private FluidService fluidService;

    @Autowired
    private CarService carService;

    @Autowired
    private RepairJobService repairJobService;

    private ObservableList<PlannedRepairJob> plannedRepairJobList;

    @FXML
    public void initialize() {
        plannedRepairJobList = FXCollections.observableArrayList();
        loadPlannedRepairJobs();

        setupTableColumns();
        setupJobTypeComboBox();
        setupCardNumberFields();
        setupTableEditing();

        // Расчет и планирование работ при инициализации
        checkAndPlanMaintenance();

    }

    private void loadPlannedRepairJobs() {
        plannedRepairJobList.setAll(plannedRepairJobService.findAllPlannedJobs());
    }

    private void setupTableColumns() {
        jobNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJobName()));

        // Запчасти
        partCardNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPartCardNumber()));
        partCardNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        partCardNumberColumn.setOnEditCommit(event -> {
            PlannedRepairJob job = event.getRowValue();
            String newCardNumber = event.getNewValue();
            job.setPartCardNumber(newCardNumber);
            updatePartDetails(job, newCardNumber);
            plannedRepairJobService.save(job);
            plannedRepairJobTable.refresh();
        });

        partNameColumn.setCellValueFactory(cellData -> {
            Optional<Part> partOpt = partService.findByCardNumber(cellData.getValue().getPartCardNumber());
            return new SimpleStringProperty(partOpt.map(Part::getName).orElse(
                    cellData.getValue().getPartCardNumber().isEmpty() ? "Не требуется" : "Не найдено"));
        });

        partQuantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPartQuantity()).asObject());
        partQuantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.IntegerStringConverter()));
        partQuantityColumn.setOnEditCommit(event -> {
            PlannedRepairJob job = event.getRowValue();
            job.setPartQuantity(event.getNewValue());
            plannedRepairJobService.save(job);
            plannedRepairJobTable.refresh();
        });

        // Жидкости
        fluidCardNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFluidCardNumber()));
        fluidCardNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fluidCardNumberColumn.setOnEditCommit(event -> {
            PlannedRepairJob job = event.getRowValue();
            String newFluidCardNumber = event.getNewValue();
            job.setFluidCardNumber(newFluidCardNumber);
            updateFluidDetails(job, newFluidCardNumber);
            plannedRepairJobService.save(job);
            plannedRepairJobTable.refresh();
        });

        fluidNameColumn.setCellValueFactory(cellData -> {
            Optional<Fluid> fluidOpt = Optional.ofNullable(fluidService.findByCardNumber(cellData.getValue().getFluidCardNumber()));
            return new SimpleStringProperty(fluidOpt.map(Fluid::getName).orElse(
                    cellData.getValue().getFluidCardNumber().isEmpty() ? "Не требуется" : "Не найдено"));
        });

        fluidVolumeColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getFluidVolume()).asObject());
        fluidVolumeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.FloatStringConverter()));
        fluidVolumeColumn.setOnEditCommit(event -> {
            PlannedRepairJob job = event.getRowValue();
            job.setFluidVolume(event.getNewValue());
            plannedRepairJobService.save(job);
            plannedRepairJobTable.refresh();
        });

        serialNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnterpriseNumber()));

        remainingMileageColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getRemainingMileage()).asObject());
        remainingMileageColumn.setEditable(false);

        remainingTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getRemainingTime() != null ? cellData.getValue().getRemainingTime().toString() : ""));
        remainingTimeColumn.setEditable(false);

        orderNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderNumber()));
        orderNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        orderNumberColumn.setOnEditCommit(event -> {
            PlannedRepairJob job = event.getRowValue();
            job.setOrderNumber(event.getNewValue());
            plannedRepairJobService.save(job);
            plannedRepairJobTable.refresh();
        });

        notesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNotes()));
        notesColumn.setEditable(false);

        jobTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJobType()));
        jobTypeColumn.setCellFactory(ComboBoxTableCell.forTableColumn("Долив","Замена","ТО1", "ТО2",
                "ТР", "ДО1", "ДО2", "УТО", "СО"));
        jobTypeColumn.setOnEditCommit(event -> {
            PlannedRepairJob job = event.getRowValue();
            job.setJobType(event.getNewValue());
            plannedRepairJobService.save(job);
            plannedRepairJobTable.refresh();
        });

        plannedRepairJobTable.setItems(plannedRepairJobList);
        plannedRepairJobTable.setEditable(true);
    }

    private void setupJobTypeComboBox() {
        jobTypeComboBox.setItems(FXCollections.observableArrayList("Долив","Замена","ТО1", "ТО2",
                "ТР", "ДО1", "ДО2", "УТО", "СО"));
    }

    private void setupCardNumberFields() {
        // Обработчик для поля cardNumber запчасти
        partCardNumberField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String cardNumber = partCardNumberField.getText().trim();
                if (!cardNumber.isEmpty()) {
                    Optional<Part> partOpt = partService.findByCardNumber(cardNumber);
                    if (partOpt.isPresent()) {
                        Part part = partOpt.get();
                        partNameField.setText(part.getName() + " (" + part.getManufacturer() + ")");
                        logger.info("Найдена запчасть: {}", part);
                        notesField.setText(""); // Очистка предыдущих сообщений
                    } else {
                        partNameField.setText("отсутствует на складе");
                        notesField.setText("невозможно выполнить работу из-за отсутствия запчастей");
                        logger.warn("Запчасть с номером карты {} отсутствует на складе", cardNumber);
                    }
                }
            }
        });

        // Обработчик для поля cardNumber жидкости
        fluidCardNumberField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String fluidCardNumber = fluidCardNumberField.getText().trim();
                if (!fluidCardNumber.isEmpty()) {
                    Optional<Fluid> fluidOpt = Optional.ofNullable(fluidService.findByCardNumber(fluidCardNumber));
                    if (fluidOpt.isPresent()) {
                        Fluid fluid = fluidOpt.get();
                        fluidNameField.setText(fluid.getName());
                        logger.info("Найдена жидкость: {}", fluid);
                        notesField.setText(""); // Очистка предыдущих сообщений
                    } else {
                        fluidNameField.setText("отсутствует на складе");
                        notesField.setText("невозможно выполнить работу из-за отсутствия жидкостей");
                        logger.warn("Жидкость с номером карты {} отсутствует на складе", fluidCardNumber);
                    }
                }
            }
        });
    }

    private void setupTableEditing() {
        // Дополнительные настройки редактирования таблицы, если необходимо
    }

    @FXML
    private void addPlannedRepairJob() {
        String jobName = jobNameField.getText().trim();
        String partCardNumber = partCardNumberField.getText().trim();
        String fluidCardNumber = fluidCardNumberField.getText().trim();
        Integer partQuantity = parseInteger(partQuantityField.getText().trim());
        Float fluidVolume = parseFloat(fluidVolumeField.getText().trim());
        String serialNumber = serialNumberField.getText().trim();
        String orderNumber = orderNumberField.getText().trim();
        String jobType = jobTypeComboBox.getValue();

        if (jobName.isEmpty() || serialNumber.isEmpty() || jobType == null) {
            showAlert("Ошибка", "Пожалуйста, заполните обязательные поля: Название работы, Серийный номер, Тип работы.", Alert.AlertType.ERROR);
            return;
        }

        PlannedRepairJob newJob = new PlannedRepairJob();
        newJob.setJobName(jobName);
        newJob.setPartCardNumber(partCardNumber);
        newJob.setPartQuantity(partQuantity != null ? partQuantity : 0);
        newJob.setFluidCardNumber(fluidCardNumber);
        newJob.setFluidVolume(fluidVolume != null ? fluidVolume : 0.0f);
        newJob.setEnterpriseNumber(serialNumber);
        newJob.setOrderNumber(orderNumber);
        newJob.setJobType(jobType);

        // Расчет remainingMileage и remainingTime
        calculateRemainingFields(newJob);

        // Установка примечаний
        updateNotes(newJob);

        plannedRepairJobService.save(newJob);
        plannedRepairJobList.add(newJob);
        clearFields();
        logger.info("Добавлена запланированная работа: {}", newJob);
    }

    @FXML
    private void deletePlannedRepairJob() {
        PlannedRepairJob selectedJob = plannedRepairJobTable.getSelectionModel().getSelectedItem();
        if (selectedJob != null) {
            plannedRepairJobService.delete(selectedJob);
            plannedRepairJobList.remove(selectedJob);
            logger.info("Удалена запланированная работа: {}", selectedJob);
        } else {
            showAlert("Предупреждение", "Пожалуйста, выберите работу для удаления.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void editPlannedRepairJob() {
        PlannedRepairJob selectedJob = plannedRepairJobTable.getSelectionModel().getSelectedItem();
        if (selectedJob != null) {
            // Открыть форму редактирования или реализовать редактирование прямо в таблице
            // В данном примере редактирование происходит прямо в таблице через cell editing
            logger.info("Редактируется запланированная работа: {}", selectedJob);
        } else {
            showAlert("Предупреждение", "Пожалуйста, выберите работу для редактирования.", Alert.AlertType.WARNING);
        }
    }

    private void clearFields() {
        jobNameField.clear();
        partCardNumberField.clear();
        partNameField.clear();
        partQuantityField.clear();
        fluidCardNumberField.clear();
        fluidNameField.clear();
        fluidVolumeField.clear();
        serialNumberField.clear();
        orderNumberField.clear();
        notesField.clear();
        jobTypeComboBox.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
        logger.debug("Показано предупреждение: {} - {}", title, content);
    }

    private Integer parseInteger(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            logger.warn("Неверный формат числа: {}", text);
            return null;
        }
    }

    private Float parseFloat(String text) {
        try {
            return Float.parseFloat(text);
        } catch (NumberFormatException e) {
            logger.warn("Неверный формат числа: {}", text);
            return null;
        }
    }

    /**
     * Метод для расчета оставшегося пробега и времени до следующего обслуживания.
     * @param job Запланированная работа.
     */
    private void calculateRemainingFields(PlannedRepairJob job) {
        String enterpriseNumber = job.getEnterpriseNumber();
        logger.debug("поиск работы для {} ", enterpriseNumber);
        Optional<Car> carOpt = carService.findByEnterpriseNumber(enterpriseNumber);

        if (carOpt.isPresent()) {
            Car car = carOpt.get();
            Long currentMileage = car.getMileage();

            // Получение последних записей о техническом обслуживании
            List<RepairJob> repairJobs = repairJobService.findListBySerialNumber(enterpriseNumber);

            for (RepairJob record : repairJobs) {
                if (record.getJobName().equals(job.getJobName())) {
                    Long lastMileage = record.getLastMileage();
                    Long mileageInterval = record.getIntervalInMileage();
                    Long remainingMileage = mileageInterval - (currentMileage - lastMileage);
                    job.setRemainingMileage(remainingMileage > 0 ? remainingMileage : 0);

                    LocalDate lastDate = record.getLastJobDate();
                    Long timeInterval = record.getIntervalInDays(); // В днях
                    Long daysSinceLast = ChronoUnit.DAYS.between(lastDate, LocalDate.now());
                    Long remainingDays = timeInterval - daysSinceLast;
                    job.setRemainingTime(remainingDays > 0 ? remainingDays.toString() : "Просрочено");
                    break;
                }
            }
        } else {
            job.setRemainingMileage(0L);
            job.setRemainingTime("Неизвестно");
            logger.warn("Автомобиль с серийным номером {} не найден.", enterpriseNumber);
        }
    }

    /**
     * Метод для обновления примечаний на основе наличия запчастей и жидкостей.
     * @param job Запланированная работа.
     */
    private void updateNotes(PlannedRepairJob job) {
        boolean partsAvailable = true;
        boolean fluidsAvailable = true;

        if (!job.getPartCardNumber().isEmpty()) {
            Optional<Part> partOpt = partService.findByCardNumber(job.getPartCardNumber());
            partsAvailable = partOpt.isPresent();
        }

        if (!job.getFluidCardNumber().isEmpty()) {
            Optional<Fluid> fluidOpt = Optional.ofNullable(fluidService.findByCardNumber(job.getFluidCardNumber()));
            fluidsAvailable = fluidOpt.isPresent();
        }

        if (!partsAvailable || !fluidsAvailable) {
            job.setNotes("Невозможно выполнить работу из-за отсутствия запчастей/жидкостей");
        } else {
            job.setNotes("Работу возможно выполнить");
        }
    }

    /**
     * Метод для обновления деталей запчасти после редактирования номера карты.
     * @param job Запланированная работа.
     * @param cardNumber Новый номер карты запчасти.
     */
    private void updatePartDetails(PlannedRepairJob job, String cardNumber) {
        if (!cardNumber.isEmpty()) {
            Optional<Part> partOpt = partService.findByCardNumber(cardNumber);
            if (partOpt.isPresent()) {
                Part part = partOpt.get();
                // Проверяем, указано ли количество
                if (job.getPartQuantity() == null || job.getPartQuantity() == 0) {
                    notesField.setText("Пожалуйста, укажите количество запчастей для этой работы.");
                } else {
                    job.setPartQuantity(job.getPartQuantity()); // Оставляем введенное значение
                    notesField.setText(""); // Очистка предыдущих сообщений
                }
            } else {
                // Запчасть отсутствует
                notesField.setText("Невозможно выполнить работу из-за отсутствия запчастей");
            }
        } else {
            // Запчасть не требуется
            job.setPartQuantity(0);
            notesField.setText("");
        }
    }

    /**
     * Метод для обновления деталей жидкости после редактирования номера карты.
     * @param job Запланированная работа.
     * @param cardNumber Новый номер карты жидкости.
     */
    private void updateFluidDetails(PlannedRepairJob job, String cardNumber) {
        if (!cardNumber.isEmpty()) {
            Optional<Fluid> fluidOpt = Optional.ofNullable(fluidService.findByCardNumber(cardNumber));
            if (fluidOpt.isPresent()) {
                Fluid fluid = fluidOpt.get();
                // Проверяем, указан ли объем
                if (job.getFluidVolume() == null || job.getFluidVolume() == 0.0f) {
                    notesField.setText("Пожалуйста, укажите объем жидкости для этой работы.");
                } else {
                    job.setFluidVolume(job.getFluidVolume()); // Оставляем введенное значение
                    notesField.setText(""); // Очистка предыдущих сообщений
                }
            } else {
                // Жидкость отсутствует
                notesField.setText("Невозможно выполнить работу из-за отсутствия жидкостей");
            }
        } else {
            // Жидкость не требуется
            job.setFluidVolume(0.0f);
            notesField.setText("");
        }
    }

    /**
     * Метод для проверки и планирования технического обслуживания при запуске приложения.
     */
    private void checkAndPlanMaintenance() {
        List<Car> cars = carService.findAllCars(); // Получаем все автомобили из БД

        for (Car car : cars) {
            Long currentMileage = car.getMileage(); // Текущий пробег автомобиля
            String serialNumber = car.getEnterpriseNumber();

            List<RepairJob> repairJobs = repairJobService.findListBySerialNumber(serialNumber);
            logger.info("ищем работы для {}",serialNumber);
            for (RepairJob record : repairJobs) {
                Long lastMileage = record.getLastMileage(); // Пробег при последней выполненной работе
                Long mileageInterval = record.getIntervalInMileage(); // Интервал по пробегу
                Long remainingMileage = mileageInterval - (currentMileage - lastMileage);

                LocalDate lastDate = record.getLastJobDate();
                Long timeInterval = record.getIntervalInDays(); // Интервал по сроку в днях
                Long daysSinceLast = ChronoUnit.DAYS.between(lastDate, LocalDate.now());
                Long remainingDays = timeInterval - daysSinceLast;

                boolean needsMaintenance = false;

                // Пороговое значение для пробега (например, 500 км)
                Long mileageThreshold = 500L;
                if (remainingMileage <= mileageThreshold) {
                    needsMaintenance = true;
                }

                // Пороговое значение для времени (например, 30 дней)
                Long timeThreshold = 30L;
                if (remainingDays <= timeThreshold) {
                    needsMaintenance = true;
                }

                if (needsMaintenance) {
                    PlannedRepairJob plannedJob = new PlannedRepairJob();
                    plannedJob.setJobName(record.getJobName());
                    plannedJob.setEnterpriseNumber(serialNumber);
                    plannedJob.setJobType(record.getJobsType());

                    // Установка remainingMileage и remainingTime
                    plannedJob.setRemainingMileage(remainingMileage > 0 ? remainingMileage : 0);
                    plannedJob.setRemainingTime(remainingDays > 0 ? remainingDays.toString() : "Просрочено");

                    // Поскольку запчасти и жидкости могут не требоваться, оставляем их пустыми
                    plannedJob.setPartCardNumber("");
                    plannedJob.setFluidCardNumber("");
                    plannedJob.setPartQuantity(0);
                    plannedJob.setFluidVolume(0.0f);

                    // Установка примечаний
                    plannedJob.setNotes("Работу возможно выполнить");

                    // Добавляем в список и сохраняем
                    plannedRepairJobService.save(plannedJob);
                    plannedRepairJobList.add(plannedJob);
                    logger.info("Добавлена запланированная работа: {}", plannedJob);
                }
            }
        }
    }
}
