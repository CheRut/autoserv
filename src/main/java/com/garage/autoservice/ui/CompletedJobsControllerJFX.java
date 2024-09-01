package com.garage.autoservice.ui;


import com.garage.autoservice.entity.MaintenanceRecord;
import com.garage.autoservice.service.MaintenanceRecordService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Controller
public class CompletedJobsControllerJFX {

    private static final Logger logger = LoggerFactory.getLogger(CompletedJobsControllerJFX.class);
    @Autowired
    private MaintenanceRecordService maintenanceRecordService;

    @FXML
    private TableView<MaintenanceRecord> maintenanceTable;

    @FXML
    private TextField vinField;
    @FXML
    private TextField jobNameField;
    @FXML
    private TextField mileageField;
    @FXML
    private TextField hoursField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField intervalMileageField;
    @FXML
    private TextField intervalHoursField;
    @FXML
    private TextField intervalDaysField;

    @FXML
    private void editMaintenanceRecord() {
        try {
            MaintenanceRecord selectedRecord = maintenanceTable.getSelectionModel().getSelectedItem();
            if (selectedRecord == null) {
                showAlert("Предупреждение", "Пожалуйста, выберите запись для редактирования", Alert.AlertType.WARNING);
                return;
            }

            selectedRecord.setVin(vinField.getText());
            selectedRecord.setJobName(jobNameField.getText());
            selectedRecord.setMileage(Integer.parseInt(mileageField.getText()));
            selectedRecord.setHours(Integer.parseInt(hoursField.getText()));

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = formatter.parse(dateField.getText());
            selectedRecord.setDate(parsedDate);

            selectedRecord.setIntervalMileage(Integer.parseInt(intervalMileageField.getText()));
            selectedRecord.setIntervalHours(Integer.parseInt(intervalHoursField.getText()));
            selectedRecord.setIntervalDays(Integer.parseInt(intervalDaysField.getText()));

            maintenanceRecordService.save(selectedRecord);
            loadMaintenanceRecords();
            showAlert("Информация", "Запись о выполненной работе успешно обновлена", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            logger.error("Ошибка при редактировании записи о выполненной работе", e);
            showAlert("Ошибка", "Не удалось редактировать запись о выполненной работе: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void deleteMaintenanceRecord() {
        MaintenanceRecord selectedRecord = maintenanceTable.getSelectionModel().getSelectedItem();
        if (selectedRecord == null) {
            showAlert("Предупреждение", "Пожалуйста, выберите запись для удаления", Alert.AlertType.WARNING);
            return;
        }

        try {
            maintenanceRecordService.delete(selectedRecord);
            loadMaintenanceRecords();
            showAlert("Информация", "Запись о выполненной работе успешно удалена", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            logger.error("Ошибка при удалении записи о выполненной работе", e);
            showAlert("Ошибка", "Не удалось удалить запись о выполненной работе: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void loadMaintenanceRecords() {
        try {
            // Получаем список всех записей о выполненных работах из сервиса
            List<MaintenanceRecord> maintenanceRecords = maintenanceRecordService.findAll();

            // Очищаем таблицу перед добавлением новых данных
            maintenanceTable.getItems().clear();

            // Добавляем все записи в таблицу
            maintenanceTable.getItems().addAll(maintenanceRecords);

            logger.info("Записи о выполненных работах успешно загружены в таблицу.");
        } catch (Exception e) {
            logger.error("Ошибка при загрузке записей о выполненных работах", e);
            showAlert("Ошибка", "Не удалось загрузить записи о выполненных работах: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
