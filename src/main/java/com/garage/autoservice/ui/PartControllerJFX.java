package com.garage.autoservice.ui;

import com.garage.autoservice.entity.Part;
import com.garage.autoservice.service.PartService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Контроллер для управления окном склада запчастей.
 * Предоставляет функционал для добавления, удаления и редактирования запчастей.
 */
@Component
public class PartControllerJFX {

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
        // Инициализация списка перед использованием
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
    }

    /**
     * Загрузка всех запчастей из базы данных.
     */
    private void loadParts() {
        partList.setAll(partService.findAll());
    }

    /**
     * Добавление новой запчасти в базу данных и обновление таблицы.
     */
    private void addPart() {
        try {
            Part part = new Part();
            part.setName(nameField.getText());
            part.setManufacturer(manufacturerField.getText());
            part.setPartNumber(partNumberField.getText());
            part.setQuantity(Integer.parseInt(quantityField.getText()));
            part.setCardNumber(Integer.parseInt(cardNumberField.getText()));
            part.setVin(vinField.getText());

            partService.save(part); // Сохранение запчасти в базе данных
            loadParts(); // Обновление таблицы
        } catch (NumberFormatException e) {
            // Логгирование ошибки и уведомление пользователя
            System.err.println("Ошибка: некорректный ввод числовых данных.");
        } catch (Exception e) {
            // Логгирование и обработка других исключений
            System.err.println("Ошибка при добавлении запчасти: " + e.getMessage());
        }
    }

    /**
     * Удаление выбранной запчасти из базы данных и обновление таблицы.
     */
    private void deletePart() {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            partService.delete(selectedPart); // Удаление запчасти из базы данных
            loadParts(); // Обновление таблицы
        }
    }

    /**
     * Редактирование выбранной запчасти и обновление данных в базе и таблице.
     */
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

                partService.save(selectedPart); // Обновление данных запчасти в базе данных
                loadParts(); // Обновление таблицы
            }
        } catch (NumberFormatException e) {
            // Логгирование ошибки и уведомление пользователя
            System.err.println("Ошибка: некорректный ввод числовых данных.");
        } catch (Exception e) {
            // Логгирование и обработка других исключений
            System.err.println("Ошибка при редактировании запчасти: " + e.getMessage());
        }
    }
}
