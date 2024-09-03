package com.garage.autoservice.ui;

import com.garage.autoservice.entity.Part;
import com.garage.autoservice.entity.PlannedRepairJob;
import com.garage.autoservice.entity.Fluid;
import com.garage.autoservice.service.PartService;
import com.garage.autoservice.service.PlannedRepairJobService;
import com.garage.autoservice.service.FluidService;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlannedRepairJobControllerJFX {

    private static final Logger logger = LoggerFactory.getLogger(PlannedRepairJobControllerJFX.class);

    @FXML
    private TableView<PlannedRepairJob> plannedRepairJobTable;

    @FXML
    private TableColumn<PlannedRepairJob, String> jobNameColumn;

    @FXML
    private TableColumn<PlannedRepairJob, String> partNameColumn; // Столбец для наименования запчасти
    @FXML
    private TableColumn<PlannedRepairJob, Integer> partQuantityColumn; // Столбец для количества запчастей
    @FXML
    private TableColumn<PlannedRepairJob, String> fluidNameColumn; // Столбец для наименования жидкости
    @FXML
    private TableColumn<PlannedRepairJob, Float> fluidVolumeColumn; // Столбец для объема жидкостей
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
    private TextField partNameField;

    @FXML
    private TextField partQuantityField; // Текстовое поле для количества запчастей
    @FXML
    private TextField fluidNameField;
    @FXML
    private TextField fluidVolumeField; // Текстовое поле для объема жидкостей
    @FXML
    private TextField serialNumberField;
    @FXML
    private TextField remainingMileageField;
    @FXML
    private TextField remainingTimeField;
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

    private ObservableList<PlannedRepairJob> plannedRepairJobList;

    @FXML
    public void initialize() {
        plannedRepairJobList = FXCollections.observableArrayList();
        loadPlannedRepairJobs();

        jobNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJobName()));
        partNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCardNumber()));
        partQuantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPartQuantity()).asObject()); // Связывание столбца с количеством запчастей
        fluidNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFluidType()));
        fluidVolumeColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getFluidVolume()).asObject()); // Связывание столбца с объемом жидкости
        serialNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSerialNumber()));
        remainingMileageColumn.setCellValueFactory(cellData -> {
            Long remainingMileage = cellData.getValue().getRemainingMileage();
            return new SimpleLongProperty(remainingMileage != null ? remainingMileage : 0).asObject();
        });
        remainingTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRemainingTime() != null ? cellData.getValue().getRemainingTime().toString() : ""));
        orderNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderNumber()));
        notesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNotes()));
        jobTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJobType()));

        plannedRepairJobTable.setItems(plannedRepairJobList);

        setupJobTypeComboBox();
        setupCardNumberField(); // Добавляем обработчик для поля cardNumberField
        setupFluidTypeField();  // Добавляем обработчик для поля fluidTypeField
    }

    private void loadPlannedRepairJobs() {
        plannedRepairJobList.setAll(plannedRepairJobService.findAllPlannedJobs());
    }

    private void setupJobTypeComboBox() {
        jobTypeComboBox.setItems(FXCollections.observableArrayList("ТО1", "ТО2", "ТР", "ДО1", "ДО2", "УТО", "СО"));
    }

    private void setupCardNumberField() {
        partNameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String cardNumber = partNameField.getText();
                Optional<Part> partOpt = partService.findByCardNumber(cardNumber);

                if (partOpt.isPresent()) {
                    Part part = partOpt.get();
                    partNameField.setText(part.getCardNumber() + " - " + part.getName() + " (" + part.getManufacturer() + ")");
                    logger.info("Найдена запчасть: {}", part);
                } else {
                    partNameField.setText("отсутствует на складе");
                    notesField.setText("невозможно выполнить работу из-за отсутствия запчастей");
                    logger.warn("Запчасть с номером карты {} отсутствует на складе", cardNumber);
                }
            }
        });
    }

    private void setupFluidTypeField() {
        fluidNameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String fluidCardNumber = fluidNameField.getText();
                Optional<Fluid> fluidOpt = Optional.ofNullable(fluidService.findByCardNumber(fluidCardNumber));

                if (fluidOpt.isPresent()) {
                    Fluid fluid = fluidOpt.get();
                    fluidNameField.setText(fluid.getCardNumber() + " - " + fluid.getName());
                    logger.info("Найдена жидкость: {}", fluid);
                } else {
                    fluidNameField.setText("отсутствует на складе");
                    notesField.setText("невозможно выполнить работу из-за отсутствия жидкостей");
                    logger.warn("Жидкость с номером карты {} отсутствует на складе", fluidCardNumber);
                }
            }
        });
    }

    @FXML
    private void addPlannedRepairJob() {
        // остальная логика addPlannedRepairJob
    }

    @FXML
    private void deletePlannedRepairJob() {
        // остальная логика deletePlannedRepairJob
    }

    @FXML
    private void editPlannedRepairJob() {
        // остальная логика editPlannedRepairJob
    }

    private void clearFields() {
        // остальная логика clearFields
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
        logger.debug("Показано предупреждение: {} - {}", title, content);
    }
}
