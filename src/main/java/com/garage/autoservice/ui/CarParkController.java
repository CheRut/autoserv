package com.garage.autoservice.ui;

import com.garage.autoservice.entity.Car;
import com.garage.autoservice.service.CarService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Контроллер для управления окном автопарка.
 * Предоставляет функционал для добавления, удаления и редактирования автомобилей.
 */
@Component
public class CarParkController {

    private static final Logger logger = LoggerFactory.getLogger(CarParkController.class);

    @FXML
    private TableView<Car> carTable;

    @FXML
    private TableColumn<Car, String> serialNumberColumn;
    @FXML
    private TableColumn<Car, String> enterpriseNumberColumn;
    @FXML
    private TableColumn<Car, String> vinColumn;
    @FXML
    private TableColumn<Car, String> licensePlateColumn;
    @FXML
    private TableColumn<Car, String> makeColumn;
    @FXML
    private TableColumn<Car, String> modelColumn;
    @FXML
    private TableColumn<Car, String> engineTypeColumn;
    @FXML
    private TableColumn<Car, String> engineNumberColumn;
    @FXML
    private TableColumn<Car, String> transmissionTypeColumn;
    @FXML
    private TableColumn<Car, String> transmissionNumberColumn;
    @FXML
    private TableColumn<Car, Integer> yearOfManufactureColumn;
    @FXML
    private TableColumn<Car, Long> mileageColumn;
    @FXML
    private TableColumn<Car, Long> engineHoursColumn;
    @FXML
    private TableColumn<Car, String> carTypeColumn;

    @FXML
    private TextField serialNumberField;
    @FXML
    private TextField enterpriseNumberField;
    @FXML
    private TextField vinField;
    @FXML
    private TextField licensePlateField;
    @FXML
    private TextField makeField;
    @FXML
    private TextField modelField;
    @FXML
    private TextField engineTypeField;
    @FXML
    private TextField engineNumberField;
    @FXML
    private TextField transmissionTypeField;
    @FXML
    private TextField transmissionNumberField;
    @FXML
    private TextField yearOfManufactureField;
    @FXML
    private TextField mileageField;
    @FXML
    private TextField engineHoursField;
    @FXML
    private TextField carTypeField;

    @Autowired
    private CarService carService;
    private ObservableList<Car> carList;

    /**
     * Инициализация контроллера. Загружает список автомобилей и связывает колонки таблицы с соответствующими полями.
     */
    public void initialize() {
        carList = FXCollections.observableArrayList();
        loadCars();

        serialNumberColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSerialNumber()));
        enterpriseNumberColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEnterpriseNumber()));
        vinColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getVin()));
        licensePlateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLicensePlate()));
        makeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMake()));
        modelColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getModel()));
        engineTypeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEngineType()));
        engineNumberColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEngineNumber()));
        transmissionTypeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTransmissionType()));
        transmissionNumberColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTransmissionNumber()));
        yearOfManufactureColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getYearOfManufacture()).asObject());
        mileageColumn.setCellValueFactory(cellData ->
                new SimpleLongProperty(cellData.getValue().getMileage()).asObject());
        engineHoursColumn.setCellValueFactory(cellData ->
                new SimpleLongProperty(cellData.getValue().getEngineHours()).asObject());
        carTypeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCarType().toString()));

        carTable.setItems(carList);
    }

    /**
     * Загрузка списка автомобилей из базы данных.
     */
    private void loadCars() {
        carList.setAll(carService.findAll());
        logger.info("Загружено {} автомобилей из базы данных", carList.size());
    }

    /**
     * Добавление нового автомобиля в базу данных и обновление таблицы.
     */
    @FXML
    private void addCar() {
        try {
            if (fieldsAreEmpty()) {
                showAlert("Ошибка", "Все поля должны быть заполнены", Alert.AlertType.ERROR);
                return;
            }

            Car car = new Car();
            car.setSerialNumber(serialNumberField.getText());
            car.setEnterpriseNumber(enterpriseNumberField.getText());
            car.setVin(vinField.getText());
            car.setLicensePlate(licensePlateField.getText());
            car.setMake(makeField.getText());
            car.setModel(modelField.getText());
            car.setEngineType(engineTypeField.getText());
            car.setEngineNumber(engineNumberField.getText());
            car.setTransmissionType(transmissionTypeField.getText());
            car.setTransmissionNumber(transmissionNumberField.getText());
            car.setYearOfManufacture(Integer.parseInt(yearOfManufactureField.getText()));
            car.setMileage(Long.parseLong(mileageField.getText()));
            car.setEngineHours(Long.parseLong(engineHoursField.getText()));
            car.setCarType(Car.CarType.valueOf(carTypeField.getText().toUpperCase()));

            carService.save(car);
            logger.info("Автомобиль добавлен: {}", car);

            loadCars();
            clearFields();

        } catch (Exception e) {
            showAlert("Ошибка", "Не удалось добавить автомобиль: " + e.getMessage(), Alert.AlertType.ERROR);
            logger.error("Ошибка при добавлении автомобиля", e);
        }
    }

    /**
     * Редактирование выбранного автомобиля и обновление данных в базе и таблице.
     */
    @FXML
    private void editCar() {
        Car selectedCar = carTable.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            logger.info("Редактирование автомобиля: {}", selectedCar);

            selectedCar.setSerialNumber(serialNumberField.getText());
            selectedCar.setEnterpriseNumber(enterpriseNumberField.getText());
            selectedCar.setVin(vinField.getText());
            selectedCar.setLicensePlate(licensePlateField.getText());
            selectedCar.setMake(makeField.getText());
            selectedCar.setModel(modelField.getText());
            selectedCar.setEngineType(engineTypeField.getText());
            selectedCar.setEngineNumber(engineNumberField.getText());
            selectedCar.setTransmissionType(transmissionTypeField.getText());
            selectedCar.setTransmissionNumber(transmissionNumberField.getText());
            selectedCar.setYearOfManufacture(Integer.parseInt(yearOfManufactureField.getText()));
            selectedCar.setMileage(Long.parseLong(mileageField.getText()));
            selectedCar.setEngineHours(Long.parseLong(engineHoursField.getText()));
            selectedCar.setCarType(Car.CarType.valueOf(carTypeField.getText().toUpperCase()));

            carService.save(selectedCar);
            logger.info("Автомобиль обновлен: {}", selectedCar);

            loadCars();
        } else {
            showAlert("Ошибка", "Не выбран автомобиль для редактирования", Alert.AlertType.ERROR);
            logger.warn("Попытка редактирования без выбора автомобиля");
        }
    }

    /**
     * Удаление выбранного автомобиля из базы данных и обновление таблицы.
     */
    @FXML
    private void deleteCar() {
        Car selectedCar = carTable.getSelectionModel().getSelectedItem();

        if (selectedCar != null) {
            // Создаем копию объекта перед удалением
            Car carCopy = new Car();
            carCopy.setSerialNumber(selectedCar.getSerialNumber());
            carCopy.setEnterpriseNumber(selectedCar.getEnterpriseNumber());
            carCopy.setVin(selectedCar.getVin());
            carCopy.setLicensePlate(selectedCar.getLicensePlate());
            carCopy.setMake(selectedCar.getMake());
            carCopy.setModel(selectedCar.getModel());
            carCopy.setEngineType(selectedCar.getEngineType());
            carCopy.setEngineNumber(selectedCar.getEngineNumber());
            carCopy.setTransmissionType(selectedCar.getTransmissionType());
            carCopy.setTransmissionNumber(selectedCar.getTransmissionNumber());
            carCopy.setYearOfManufacture(selectedCar.getYearOfManufacture());
            carCopy.setMileage(selectedCar.getMileage());
            carCopy.setEngineHours(selectedCar.getEngineHours());
            carCopy.setCarType(selectedCar.getCarType());
            carService.delete(selectedCar);
            logger.info("Автомобиль удален: {}", carCopy);
            loadCars();
        } else {
            showAlert("Ошибка", "Не выбран автомобиль для удаления", Alert.AlertType.ERROR);
            logger.warn("Попытка удаления без выбора автомобиля");
        }
    }

    /**
     * Очистка всех текстовых полей после успешного добавления или редактирования.
     */
    private void clearFields() {
        serialNumberField.clear();
        enterpriseNumberField.clear();
        vinField.clear();
        licensePlateField.clear();
        makeField.clear();
        modelField.clear();
        engineTypeField.clear();
        engineNumberField.clear();
        transmissionTypeField.clear();
        transmissionNumberField.clear();
        yearOfManufactureField.clear();
        mileageField.clear();
        engineHoursField.clear();
        carTypeField.clear();
        logger.debug("Все поля ввода очищены");
    }

    /**
     * Проверяет, заполнены ли все обязательные поля.
     * @return true, если хотя бы одно поле пустое; false, если все поля заполнены.
     */
    private boolean fieldsAreEmpty() {
        boolean empty = serialNumberField.getText().isEmpty() ||
                enterpriseNumberField.getText().isEmpty() ||
                vinField.getText().isEmpty() ||
                licensePlateField.getText().isEmpty() ||
                makeField.getText().isEmpty() ||
                modelField.getText().isEmpty() ||
                engineTypeField.getText().isEmpty() ||
                engineNumberField.getText().isEmpty() ||
                transmissionTypeField.getText().isEmpty() ||
                transmissionNumberField.getText().isEmpty() ||
                yearOfManufactureField.getText().isEmpty() ||
                mileageField.getText().isEmpty() ||
                engineHoursField.getText().isEmpty() ||
                carTypeField.getText().isEmpty();

        if (empty) {
            logger.warn("Обнаружены пустые поля при попытке добавления/редактирования автомобиля");
        }

        return empty;
    }

    /**
     * Показать предупреждение.
     * @param title заголовок окна предупреждения
     * @param content содержимое предупреждения
     * @param alertType тип предупреждения
     */
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
        logger.debug("Показано предупреждение: {} - {}", title, content);
    }
}
