package com.garage.autoservice;

import com.garage.autoservice.ui.MainApp;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Основной класс Spring Boot приложения для управления автосервисом.
 * Этот класс используется для запуска приложения.
 */
@SpringBootApplication
@EntityScan(basePackages = {"com.garage.autoservice.entity"})
public class AutoserviceApplication {

	private static ConfigurableApplicationContext springContext;

	/**
	 * Главный метод, с которого начинается выполнение программы.
	 * Используется для запуска Spring Boot приложения.
	 *
	 * @param args аргументы командной строки
	 */
	public static void main(String[] args) {
		// Запуск Spring Boot контекста в отдельном потоке
		springContext = SpringApplication.run(AutoserviceApplication.class, args);

		// Запуск JavaFX приложения
		Application.launch(MainApp.class, args);
	}

	public static ConfigurableApplicationContext getSpringContext() {
		return springContext;
	}
}
