package com.garage.autoservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotBlank;

/**
 * Глобальный обработчик исключений для приложения.
 * Обрабатывает различные типы исключений и возвращает соответствующие HTTP-ответы.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Обработчик исключений для случаев, когда ресурс не найден.
     *
     * @param ex исключение {@link ResourceNotFoundException}
     * @param request объект {@link WebRequest}, содержащий детали запроса
     * @return объект {@link ResponseEntity}, содержащий {@link ErrorDetails} и статус NOT_FOUND
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        logger.error("Resource not found: {}", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Обработчик исключений для случаев, когда запрос невалиден.
     *
     * @param ex исключение {@link InvalidRequestException}
     * @param request объект {@link WebRequest}, содержащий детали запроса
     * @return объект {@link ResponseEntity}, содержащий {@link ErrorDetails} и статус BAD_REQUEST
     */
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorDetails> handleInvalidRequestException(InvalidRequestException ex, WebRequest request) {
        logger.error("Invalid request: {}", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Глобальный обработчик исключений для случаев, когда возникает непредвиденная ошибка.
     *
     * @param ex исключение {@link Exception}
     * @param request объект {@link WebRequest}, содержащий детали запроса
     * @return объект {@link ResponseEntity}, содержащий {@link ErrorDetails} и статус INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Internal server error: {}", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

/**
 * Класс для представления деталей ошибки в ответе на исключение.
 */
class ErrorDetails {

    @NotBlank(message = "Сообщение об ошибке не может быть пустым")
    private String message;

    @NotBlank(message = "Детали ошибки не могут быть пустыми")
    private String details;

    public ErrorDetails(String message, String details) {
        this.message = message;
        this.details = details;
    }

    // Геттеры и сеттеры
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
