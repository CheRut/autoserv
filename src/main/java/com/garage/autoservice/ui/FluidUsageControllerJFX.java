package com.garage.autoservice.ui;

import com.garage.autoservice.entity.MaintenanceRecord;
import com.garage.autoservice.entity.UsedFluid;
import com.garage.autoservice.service.UsedFluidService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Контроллер для управления окном склада жидкостей.
 * Предоставляет функционал для добавления, удаления и редактирования жидкостей.
 */
@Component
public class FluidUsageControllerJFX {

    private static final Logger logger = LoggerFactory.getLogger(FluidUsageControllerJFX.class);

    @FXML
    private TableView<UsedFluid> fluidTable;

    @FXML
    private TableColumn<UsedFluid, String> typeColumn;

    @FXML
    private TableColumn<UsedFluid, String> brandColumn;

    @FXML
    private TableColumn<UsedFluid, String> volumeColumn;

    @FXML
    private TableColumn<UsedFluid, String> cardNumberColumn;
    @FXML
    private TableColumn<UsedFluid, String> maintenanceRecordColumn;

    @FXML
    private TextField typeField;

    @FXML
    private TextField brandField;


    @FXML
    private TextField volumeField;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField maintenanceRecordField;

    @FXML
    private TextField searchField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @Autowired
    private UsedFluidService usedFluidService;

    private ObservableList<UsedFluid> fluidList;

    /**
     * Инициализация контроллера и загрузка данных.
     */
    @FXML
    public void initialize() {
        fluidList = FXCollections.observableArrayList();
        loadFluids();

        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        brandColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBrand()));
        volumeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getVolume())));
        cardNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCardNumber()));
        maintenanceRecordColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getMaintenanceRecord() != null ?
                        cellData.getValue().getMaintenanceRecord().toString() : ""));

        fluidTable.setItems(fluidList);




        // Добавляем слушатель для выделения жидкости
        fluidTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateFields(newValue);
                setFieldsEditable(true);
            }
        });
        // деактивируем кнопку до тех пор,пока не будут внесены изменения
        editButton.setDisable(true);
        // Деактивируем поля по умолчанию
        setFieldsEditable(true);
    }

    /**
     * Загрузка всех жидкостей из базы данных.
     */
    private void loadFluids() {
        fluidList.setAll(usedFluidService.findAll());
        logger.info("Жидкости загружены в таблицу. Всего элементов: {}", fluidList.size());
    }

    /**
     * Добавление новой жидкости в базу данных и обновление таблицы.
     */
    @FXML
    private void addFluid() {
        try {
            if (fieldsAreEmpty()) {
                showAlert("Ошибка", "Все поля должны быть заполнены", Alert.AlertType.ERROR);
                logger.warn("Попытка добавления жидкости с незаполненными полями");
                return;
            }
            MaintenanceRecord maintenanceRecord = parseMaintenanceRecord(maintenanceRecordField.getText());
            usedFluidService.saveMaintenanceRecord(maintenanceRecord); // Сначала сохраняем MaintenanceRecord
            UsedFluid fluid = new UsedFluid();
            fluid.setType(typeField.getText());
            fluid.setBrand(brandField.getText());
            fluid.setVolume(Double.parseDouble(volumeField.getText()));
            fluid.setCardNumber(cardNumberField.getText());
            fluid.setMaintenanceRecord(parseMaintenanceRecord(maintenanceRecordField.getText()));

            usedFluidService.save(fluid);
            logger.info("Жидкость добавлена: {}", fluid);

            loadFluids();
            clearFields();

        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Некорректный ввод числовых данных", Alert.AlertType.ERROR);
            logger.error("Ошибка: некорректный ввод числовых данных", e);
        } catch (Exception e) {
            showAlert("Ошибка", "Не удалось добавить жидкость: " + e.getMessage(), Alert.AlertType.ERROR);
            logger.error("Ошибка при добавлении жидкости", e);
        }
    }
    private MaintenanceRecord parseMaintenanceRecord(String input) {
        // Реализуйте парсинг строки в MaintenanceRecord или получите его из сервиса
        return new MaintenanceRecord();
    }

    /**
     * Удаление выбранной жидкости из базы данных и обновление таблицы.
     */
    @FXML
    private void deleteFluid() {
        UsedFluid selectedFluid = fluidTable.getSelectionModel().getSelectedItem();
        if (selectedFluid != null) {
            usedFluidService.delete(selectedFluid);
            logger.info("Жидкость удалена: {}", selectedFluid);
            loadFluids();
            clearFields();
        } else {
            showAlert("Ошибка", "Не выбрана жидкость для удаления", Alert.AlertType.ERROR);
            logger.warn("Попытка удаления без выбора жидкости");
        }
    }

    /**
     * Редактирование выбранной жидкости и обновление данных в базе и таблице.
     */
    @FXML
    private void editFluid() {
        try {
            UsedFluid selectedFluid = fluidTable.getSelectionModel().getSelectedItem();
            if (selectedFluid != null) {
                selectedFluid.setType(typeField.getText());
                selectedFluid.setBrand(brandField.getText());
                selectedFluid.setVolume(Double.parseDouble(volumeField.getText()));
                selectedFluid.setCardNumber(cardNumberField.getText());
                selectedFluid.setMaintenanceRecord(parseMaintenanceRecord(maintenanceRecordField.getText()));
                usedFluidService.save(selectedFluid);
                logger.info("Жидкость обновлена: {}", selectedFluid);

                loadFluids();
                clearFields();
                editButton.setDisable(true);

            } else {
                showAlert("Ошибка", "Не выбрана жидкость для редактирования", Alert.AlertType.ERROR);
                logger.warn("Попытка редактирования без выбора жидкости");
            }
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Некорректный ввод числовых данных", Alert.AlertType.ERROR);
            logger.error("Ошибка: некорректный ввод числовых данных", e);
        } catch (Exception e) {
            showAlert("Ошибка", "Не удалось отредактировать жидкость: " + e.getMessage(), Alert.AlertType.ERROR);
            logger.error("Ошибка при редактировании жидкости", e);
        }
    }

    /**
     * Поиск жидкости по cardNumber.
     */
    @FXML
    private void searchFluid() {
        String cardNumber = searchField.getText();
        Optional<UsedFluid> foundFluid = usedFluidService.findByCardNumber(cardNumber);

        if (foundFluid.isPresent()) {
            fluidTable.getSelectionModel().select(foundFluid.get());
            populateFields(foundFluid.get());
            setFieldsEditable(true);
        } else {
            showAlert("Не найдено", "Жидкость с таким cardNumber не найдена", Alert.AlertType.INFORMATION);
            clearFields();
            setFieldsEditable(false);
        }
    }

    /**
     * Заполнение полей при выделении жидкости в таблице.
     *
     * @param fluid объект UsedFluid, выбранный в таблице.
     */
    private void populateFields(UsedFluid fluid) {
        typeField.setText(fluid.getType());
        brandField.setText(fluid.getBrand());
        volumeField.setText(String.valueOf(fluid.getVolume()));
        cardNumberField.setText(fluid.getCardNumber());
        searchField.setText(fluid.getCardNumber());
        maintenanceRecordField.setText(fluid.getMaintenanceRecord().getJobName());
    }

    /**
     * Очистка полей ввода.
     */
    private void clearFields() {
        typeField.clear();
        brandField.clear();
        volumeField.clear();
        cardNumberField.clear();
        searchField.clear();
        maintenanceRecordField.clear();
    }

    /**
     * Проверка, заполнены ли все обязательные поля.
     *
     * @return true, если хотя бы одно поле пустое; false, если все поля заполнены.
     */
    private boolean fieldsAreEmpty() {
        return typeField.getText().isEmpty() ||
                brandField.getText().isEmpty() ||
                volumeField.getText().isEmpty() ||
                cardNumberField.getText().isEmpty() ||
                maintenanceRecordField.getText().isEmpty();
    }

    /**
     * Установка возможности редактирования полей.
     *
     * @param editable true, если поля должны быть редактируемыми; false, если нет.
     */
    private void setFieldsEditable(boolean editable) {
        typeField.setEditable(editable);
        brandField.setEditable(editable);
        volumeField.setEditable(editable);
        cardNumberField.setEditable(editable);
        maintenanceRecordField.setEditable(editable);
    }

    /**
     * Показать всплывающее окно с уведомлением.
     *
     * @param title Заголовок окна.
     * @param content Содержимое уведомления.
     * @param alertType Тип уведомления.
     */
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
        logger.debug("Показано предупреждение: {} - {}", title, content);
    }
}
