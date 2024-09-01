package com.garage.autoservice.ui;

import com.garage.autoservice.entity.Fluid;
import com.garage.autoservice.service.FluidService;
import javafx.beans.property.SimpleIntegerProperty;
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

/**
 * Контроллер для управления окном склада жидкостей.
 * Предоставляет функционал для добавления, удаления и редактирования жидкостей.
 */
@Component
public class FluidControllerJFX {

    private static final Logger logger = LoggerFactory.getLogger(FluidControllerJFX.class);

    @FXML
    private TableView<Fluid> fluidTable;

    @FXML
    private TableColumn<Fluid, String> nameColumn;

    @FXML
    private TableColumn<Fluid, Integer> quantityColumn;

    @FXML
    private TableColumn<Fluid, String> cardNumberColumn;

    @FXML
    private TableColumn<Fluid, String> typeColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField typeField;

    @FXML
    private TextField searchField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @Autowired
    private FluidService fluidService;

    private ObservableList<Fluid> fluidList;

    /**
     * Инициализация контроллера и загрузка данных.
     */
    public void initialize() {
        editButton.setDisable(true);
        fluidList = FXCollections.observableArrayList();
        setupFieldListeners();
        loadFluids();

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        cardNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCardNumber()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));

        fluidTable.setItems(fluidList);

        fluidTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateFields(newValue);
                setFieldsEditable(true);
            }
        });

        setFieldsEditable(true);
    }

    /**
     * Загрузка всех жидкостей из базы данных.
     */
    private void loadFluids() {
        fluidList.setAll(fluidService.findAll());
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

            Fluid fluid = new Fluid();
            fluid.setName(nameField.getText());
            fluid.setQuantity(Integer.parseInt(quantityField.getText()));
            fluid.setCardNumber(cardNumberField.getText());
            fluid.setType(typeField.getText());

            fluidService.save(fluid);
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

    /**
     * Редактирование выбранной жидкости и обновление данных в базе и таблице.
     */
    @FXML
    private void editFluid() {
        Fluid selectedFluid = fluidTable.getSelectionModel().getSelectedItem();
        if (selectedFluid != null) {
            logger.info("Редактирование жидкости с карточным номером: {}", selectedFluid.getCardNumber());

            selectedFluid.setName(nameField.getText());
            selectedFluid.setQuantity(Integer.parseInt(quantityField.getText()));
            selectedFluid.setCardNumber(cardNumberField.getText());
            selectedFluid.setType(typeField.getText());

            fluidService.save(selectedFluid);
            logger.info("Жидкость обновлена: {}", selectedFluid);

            loadFluids();
        } else {
            showAlert("Ошибка", "Не выбрана жидкость для редактирования", Alert.AlertType.ERROR);
            logger.warn("Попытка редактирования без выбора жидкости");
        }
    }

    @FXML
    private void deleteFluid() {
        Fluid selectedFluid = fluidTable.getSelectionModel().getSelectedItem();
        if (selectedFluid != null) {
            fluidService.delete(selectedFluid);
            logger.info("Жидкость удалена: {}", selectedFluid);
            loadFluids();
        } else {
            showAlert("Ошибка", "Не выбрана жидкость для удаления", Alert.AlertType.ERROR);
            logger.warn("Попытка удаления без выбора жидкости");
        }
    }

    @FXML
    private void searchFluid() {
        String cardNumber = searchField.getText();
        Fluid foundFluid = fluidService.findByCardNumber(cardNumber);

        if (foundFluid != null) {
            fluidTable.getSelectionModel().select(foundFluid);
            populateFields(foundFluid);
            setFieldsEditable(true);
        } else {
            showAlert("Не найдено", "Жидкость с таким карточным номером не найдена", Alert.AlertType.INFORMATION);
            clearFields();
            setFieldsEditable(false);
        }
    }

    private void setFieldsEditable(boolean editable) {
        nameField.setEditable(editable);
        quantityField.setEditable(editable);
        cardNumberField.setEditable(editable);
        typeField.setEditable(editable);
    }

    private void populateFields(Fluid fluid) {
        nameField.setText(fluid.getName());
        quantityField.setText(String.valueOf(fluid.getQuantity()));
        cardNumberField.setText(fluid.getCardNumber());
        typeField.setText(fluid.getType());
        searchField.setText(fluid.getCardNumber());
    }

    private void clearFields() {
        nameField.clear();
        quantityField.clear();
        cardNumberField.clear();
        typeField.clear();
        searchField.clear();
        logger.debug("Все поля ввода очищены");
    }

    private void setupFieldListeners() {
        ChangeListener<String> changeListener = (observable, oldValue, newValue) -> {
            checkForChanges();
        };

        nameField.textProperty().addListener(changeListener);
        quantityField.textProperty().addListener(changeListener);
        cardNumberField.textProperty().addListener(changeListener);
        typeField.textProperty().addListener(changeListener);
    }

    private void checkForChanges() {
        Fluid selectedFluid = fluidTable.getSelectionModel().getSelectedItem();
        if (selectedFluid != null) {
            boolean hasChanges =
                    !nameField.getText().equals(selectedFluid.getName()) ||
                            !quantityField.getText().equals(String.valueOf(selectedFluid.getQuantity())) ||
                            !cardNumberField.getText().equals(selectedFluid.getCardNumber()) ||
                            !typeField.getText().equals(selectedFluid.getType());

            editButton.setDisable(!hasChanges);
        } else {
            editButton.setDisable(true);
        }
    }

    private boolean fieldsAreEmpty() {
        return nameField.getText().isEmpty() ||
                quantityField.getText().isEmpty() ||
                cardNumberField.getText().isEmpty() ||
                typeField.getText().isEmpty();
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
        logger.debug("Показано предупреждение: {} - {}", title, content);
    }
}
