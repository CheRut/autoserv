package com.garage.autoservice.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import com.garage.autoservice.AutoserviceApplication;

/**
 * Основной класс JavaFX приложения.
 * Отвечает за запуск и отображение главного окна с кнопками для навигации.
 */
public class MainApp extends Application {

    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    private ApplicationContext springContext;

    @Override
    public void init() {
        try {
            // Получаем Spring контекст из AutoserviceApplication
            springContext = AutoserviceApplication.getSpringContext();
        } catch (Exception e) {
            logger.error("Ошибка при инициализации Spring контекста", e);
            throw new RuntimeException("Не удалось инициализировать Spring контекст", e);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Garage Autoservice");

        // Создание кнопок для главного окна
        Button carParkButton = new Button("Автопарк");
        carParkButton.setOnAction(event -> openCarParkWindow());

        Button partsWarehouseButton = new Button("Склад запчастей");
        partsWarehouseButton.setOnAction(event -> openPartsWarehouseWindow());

        Button fluidsWarehouseButton = new Button("Склад ГСМ");
        fluidsWarehouseButton.setOnAction(event -> openFluidsWarehouseWindow());

        Button planningButton = new Button("Планирование работ");
        planningButton.setOnAction(event -> openPlanningWindow());

        Button completedJobsButton = new Button("Выполненные работы");
        completedJobsButton.setOnAction(event -> openCompletedJobsWindow());

        // Размещение кнопок в VBox
        VBox vbox = new VBox(carParkButton, partsWarehouseButton, fluidsWarehouseButton,planningButton,completedJobsButton);
        vbox.setSpacing(15);
        vbox.setStyle("-fx-padding: 20px;");

        // Создание сцены и установка её на сцену
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        logger.info("Главное окно успешно загружено");
    }

    /**
     * Открытие окна "Автопарк".
     */
    private void openCarParkWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/car_park.fxml"));
            fxmlLoader.setControllerFactory(springContext::getBean);
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Автопарк");
            stage.setScene(new Scene(root));
            stage.show();

            logger.info("Окно 'Автопарк' успешно открыто");
        } catch (Exception e) {
            logger.error("Ошибка при открытии окна 'Автопарк'", e);
        }
    }

    /**
     * Открытие окна "Склад запчастей".
     */
    private void openPartsWarehouseWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/part_warehouse.fxml"));
            fxmlLoader.setControllerFactory(springContext::getBean);
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Склад запчастей");
            stage.setScene(new Scene(root));
            stage.show();

            logger.info("Окно 'Склад запчастей' успешно открыто");
        } catch (Exception e) {
            logger.error("Ошибка при открытии окна 'Склад запчастей'", e);
        }
    }

    /**
     * Заглушка для открытия окна "Склад ГСМ".
     */
    private void openFluidsWarehouseWindow() {
        logger.warn("Открывается окно 'Склад ГСМ'");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fluid_warehouse.fxml"));
            fxmlLoader.setControllerFactory(springContext::getBean);
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Склад ГСМ");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            logger.error("Ошибка при открытии окна 'Склад ГСМ'", e);
        }

    }

    /**
     * Открытие окна "Планирование работ".
     */
    private void openPlanningWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/planning_window.fxml"));
            fxmlLoader.setControllerFactory(springContext::getBean);
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Планирование работ");
            stage.setScene(new Scene(root));
            stage.show();

            logger.info("Окно 'Планирование работ' успешно открыто");
        } catch (Exception e) {
            logger.error("Ошибка при открытии окна 'Планирование работ'", e);
        }
    }

    /**
     * Открытие окна "Выполненные работы".
     */
    private void openCompletedJobsWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/completed_jobs.fxml"));
            fxmlLoader.setControllerFactory(springContext::getBean);
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Выполненные работы");
            stage.setScene(new Scene(root));
            stage.show();

            logger.info("Окно 'Выполненные работы' успешно открыто");
        } catch (Exception e) {
            logger.error("Ошибка при открытии окна 'Выполненные работы'", e);
        }
    }



    /**
     * Главный метод запуска приложения.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        launch(args);
    }
}
