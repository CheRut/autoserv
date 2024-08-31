package com.garage.autoservice.exception;

/**
 * Исключение, указывающее на то, что запрос был некорректен.
 * Это исключение выбрасывается, когда запрос не соответствует требованиям валидации или содержит неверные данные.
 */
public class InvalidRequestException extends RuntimeException {

    /**
     * Создает новый экземпляр {@code InvalidRequestException} с указанным сообщением об ошибке.
     *
     * @param message сообщение об ошибке, поясняющее причину исключения
     */
    public InvalidRequestException(String message) {
        super(message);
    }
}
