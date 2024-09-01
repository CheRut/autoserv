package com.garage.autoservice.ui;

import com.garage.autoservice.entity.Part;
import com.garage.autoservice.service.PartService;
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

import java.util.Optional;

/**
 * Контроллер для управления окном склада запчастей.
 * Предоставляет функционал для добавления, удаления и редактирования запчастей.
 */
@Component
public class PartControllerJFX {

    private static final Logger logger = LoggerFactory.getLogger(PartControllerJFX.class);

    @FXML
    private TableView<Part> partTable;

    @FXML
    private TableColumn<Part, String> nameColumn;

    @FXML
    private TableColumn<Part, String> manufacturerColumn;

    @FXML
    private TableColumn<Part, String> partNumberColumn;

    @FXML
    private TableColumn<Part, Integer> quantityColumn;

    @FXML
    private TableColumn<Part, Integer> cardNumberColumn;

    @FXML
    private TableColumn<Part, String> vinColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField manufacturerField;

    @FXML
    private TextField partNumberField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField vinField;

    @FXML
    private TextField searchField; // Поле для поиска запчастей

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @Autowired
    private PartService partService;

    private ObservableList<Part> partList;

    /**
     * Инициализация контроллера и загрузка данных.
     */
    public void initialize() {
        partList = FXCollections.observableArrayList();
        loadParts();

        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        manufacturerColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getManufacturer()));
        partNumberColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPartNumber()));
        quantityColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        cardNumberColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getCardNumber()).asObject());
        vinColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getVin()));

        partTable.setItems(partList);

        editButton.setDisable(true); // Отключаем кнопку редактирования по умолчанию

        setupFieldListeners(); // Добавляем слушатели изменений для полей
        partTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateFields(newValue);
                setFieldsEditable(true);
            }
        });
        editButton.setDisable(true);
        setFieldsEditable(true);
    }

    /**
     * Загрузка всех запчастей из базы данных.
     */
    private void loadParts() {
        partList.setAll(partService.findAll());
        logger.info("Запчасти загружены в таблицу. Всего элементов: {}", partList.size());
    }

    /**
     * Добавление новой запчасти в базу данных и обновление таблицы.
     */
    @FXML
    private void addPart() {
        try {
            if (fieldsAreEmpty()) {
                showAlert("Ошибка", "Все поля должны быть заполнены", Alert.AlertType.ERROR);
                logger.warn("Попытка добавления запчасти с незаполненными полями");
                return;
            }

            Part part = new Part();
            part.setName(nameField.getText());
            part.setManufacturer(manufacturerField.getText());
            part.setPartNumber(partNumberField.getText());
            part.setQuantity(Integer.parseInt(quantityField.getText()));
            part.setCardNumber(Integer.parseInt(cardNumberField.getText()));
            part.setVin(vinField.getText());

            partService.save(part);
            logger.info("Запчасть добавлена: {}", part);

            loadParts();
            clearFields();

        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Некорректный ввод числовых данных", Alert.AlertType.ERROR);
            logger.error("Ошибка: некорректный ввод числовых данных", e);
        } catch (Exception e) {
            showAlert("Ошибка", "Не удалось добавить запчасть: " + e.getMessage(), Alert.AlertType.ERROR);
            logger.error("Ошибка при добавлении запчасти", e);
        }
    }

    /**
     * Удаление выбранной запчасти из базы данных и обновление таблицы.
     */
    @FXML
    private void deletePart() {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            partService.delete(selectedPart);
            logger.info("Запчасть удалена: {}", selectedPart);
            loadParts();
        } else {
            showAlert("Ошибка", "Не выбрана запчасть для удаления", Alert.AlertType.ERROR);
            logger.warn("Попытка удаления без выбора запчасти");
        }
    }

    /**
     * Редактирование выбранной запчасти и обновление данных в базе и таблице.
     */
    @FXML
    private void editPart() {
        try {
            Part selectedPart = partTable.getSelectionModel().getSelectedItem();
            if (selectedPart != null) {
                selectedPart.setName(nameField.getText());
                selectedPart.setManufacturer(manufacturerField.getText());
                selectedPart.setPartNumber(partNumberField.getText());
                selectedPart.setQuantity(Integer.parseInt(quantityField.getText()));
                selectedPart.setCardNumber(Integer.parseInt(cardNumberField.getText()));
                selectedPart.setVin(vinField.getText());

                partService.save(selectedPart);
                logger.info("Запчасть обновлена: {}", selectedPart);

                loadParts();
                clearFields();
                editButton.setDisable(true);

            } else {
                showAlert("Ошибка", "Не выбрана запчасть для редактирования", Alert.AlertType.ERROR);
                logger.warn("Попытка редактирования без выбора запчасти");
            }
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Некорректный ввод числовых данных", Alert.AlertType.ERROR);
            logger.error("Ошибка: некорректный ввод числовых данных", e);
        } catch (Exception e) {
            showAlert("Ошибка", "Не удалось отредактировать запчасть: " + e.getMessage(), Alert.AlertType.ERROR);
            logger.error("Ошибка при редактировании запчасти", e);
        }
    }

    /**
     * Поиск запчасти по CardNumber и отображение в таблице.
     */
    @FXML
    private void searchPart() {
        try {
            int cardNumber = Integer.parseInt(searchField.getText());
            Optional<Part> foundPart = partService.findByCardNumber(cardNumber);

            if (foundPart.isPresent()) {
                partTable.getSelectionModel().select(foundPart.get());
                populateFields(foundPart.get());
                setFieldsEditable(true);
            } else {
                showAlert("Не найдено", "Запчасть с таким Card Number не найдена", Alert.AlertType.INFORMATION);
                clearFields();
                setFieldsEditable(false);
            }
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Введите корректный Card Number для поиска", Alert.AlertType.ERROR);
            logger.error("Ошибка при вводе Card Number для поиска", e);
        }
    }

    /**
     * Заполняет поля данными выбранной запчасти.
     *
     * @param part выбранная запчасть
     */
    private void populateFields(Part part) {
        nameField.setText(part.getName());
        manufacturerField.setText(part.getManufacturer());
        partNumberField.setText(part.getPartNumber());
        quantityField.setText(String.valueOf(part.getQuantity()));
        cardNumberField.setText(String.valueOf(part.getCardNumber()));
        vinField.setText(part.getVin());
        // Заполняем поле поиска cardNumber текущей запчасти
        searchField.setText(String.valueOf(part.getCardNumber()));
    }

    /**
     * Очищает все поля ввода.
     */
    private void clearFields() {
        nameField.clear();
        manufacturerField.clear();
        partNumberField.clear();
        quantityField.clear();
        cardNumberField.clear();
        vinField.clear();
        searchField.clear();
        logger.debug("Все поля ввода очищены");
    }

    /**
     * Делает поля ввода редактируемыми или нет.
     *
     * @param editable true, если поля должны быть редактируемыми; false, если нет
     */
    private void setFieldsEditable(boolean editable) {
        nameField.setEditable(editable);
        manufacturerField.setEditable(editable);
        partNumberField.setEditable(editable);
        quantityField.setEditable(editable);
        cardNumberField.setEditable(editable);
        vinField.setEditable(editable);
    }

    /**
     * Устанавливает слушатели изменений для полей ввода, чтобы отслеживать изменения.
     */
    private void setupFieldListeners() {
        ChangeListener<String> changeListener = (observable, oldValue, newValue) -> {
            checkForChanges();
        };

        nameField.textProperty().addListener(changeListener);
        manufacturerField.textProperty().addListener(changeListener);
        partNumberField.textProperty().addListener(changeListener);
        quantityField.textProperty().addListener(changeListener);
        cardNumberField.textProperty().addListener(changeListener);
        vinField.textProperty().addListener(changeListener);
    }

    /**
     * Проверяет, были ли внесены изменения в поля ввода, по сравнению с выбранной запчастью.
     */
    private void checkForChanges() {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            boolean hasChanges =
                    !nameField.getText().equals(selectedPart.getName()) ||
                            !manufacturerField.getText().equals(selectedPart.getManufacturer()) ||
                            !partNumberField.getText().equals(selectedPart.getPartNumber()) ||
                            !quantityField.getText().equals(String.valueOf(selectedPart.getQuantity())) ||
                            !cardNumberField.getText().equals(String.valueOf(selectedPart.getCardNumber())) ||
                            !vinField.getText().equals(selectedPart.getVin());

            editButton.setDisable(!hasChanges);
        } else {
            editButton.setDisable(true);
        }
    }

    /**
     * Проверка, заполнены ли все обязательные поля.
     *
     * @return true, если хотя бы одно поле пустое; false, если все поля заполнены.
     */
    private boolean fieldsAreEmpty() {
        return nameField.getText().isEmpty() ||
                manufacturerField.getText().isEmpty() ||
                partNumberField.getText().isEmpty() ||
                quantityField.getText().isEmpty() ||
                cardNumberField.getText().isEmpty() ||
                vinField.getText().isEmpty();
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
