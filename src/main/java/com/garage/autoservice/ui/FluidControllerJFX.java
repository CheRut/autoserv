package com.garage.autoservice.ui;

import com.garage.autoservice.entity.UsedFluid;
import com.garage.autoservice.service.UsedFluidService;
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

/**
 * Контроллер для управления окном склада жидкостей.
 * Предоставляет функционал для добавления, удаления и редактирования жидкостей.
 */
@Component
public class FluidControllerJFX {

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
    private TextField typeField;

    @FXML
    private TextField brandField;

    @FXML
    private TextField volumeField;

    @FXML
    private TextField cardNumberField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @Autowired
    private UsedFluidService usedFluidService;

    private ObservableList<UsedFluid> fluidList;

    @FXML
    public void initialize() {
        fluidList = FXCollections.observableArrayList();
        loadFluids();

        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        brandColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBrand()));
        volumeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getVolume())));
        cardNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCardNumber()));

        fluidTable.setItems(fluidList);
    }

    private void loadFluids() {
        fluidList.setAll(usedFluidService.findAll());
    }

    @FXML
    private void addFluid() {
        UsedFluid fluid = new UsedFluid();
        fluid.setType(typeField.getText());
        fluid.setBrand(brandField.getText());
        fluid.setVolume(Double.parseDouble(volumeField.getText()));
        fluid.setCardNumber(cardNumberField.getText());

        usedFluidService.save(fluid);
        loadFluids();
    }

    @FXML
    private void deleteFluid() {
        UsedFluid selectedFluid = fluidTable.getSelectionModel().getSelectedItem();
        if (selectedFluid != null) {
            usedFluidService.delete(selectedFluid);
            loadFluids();
        }
    }

    @FXML
    private void editFluid() {
        UsedFluid selectedFluid = fluidTable.getSelectionModel().getSelectedItem();
        if (selectedFluid != null) {
            selectedFluid.setType(typeField.getText());
            selectedFluid.setBrand(brandField.getText());
            selectedFluid.setVolume(Double.parseDouble(volumeField.getText()));
            selectedFluid.setCardNumber(cardNumberField.getText());

            usedFluidService.save(selectedFluid);
            loadFluids();
        }
    }
}
