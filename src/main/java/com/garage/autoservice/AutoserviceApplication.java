package com.garage.autoservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Основной класс Spring Boot приложения для управления автосервисом.
 * Этот класс используется для запуска приложения.
 */
//@OpenAPIDefinition(info = @Info(title = "My API", version = "v1"))
@SpringBootApplication
@EntityScan(basePackages = {"com.garage.autoservice.entity"})
public class AutoserviceApplication {

	/**
	 * Главный метод, с которого начинается выполнение программы.
	 * Используется для запуска Spring Boot приложения.
	 *
	 * @param args аргументы командной строки
	 */
	public static void main(String[] args) {
		SpringApplication.run(AutoserviceApplication.class, args);
	}

}
