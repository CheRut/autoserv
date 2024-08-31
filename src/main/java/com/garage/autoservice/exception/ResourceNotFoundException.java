package com.garage.autoservice.exception;

/**
 * Исключение, указывающее на то, что запрашиваемый ресурс не был найден.
 * Это исключение выбрасывается, когда ресурс, запрашиваемый клиентом, отсутствует в системе.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Создает новый экземпляр {@code ResourceNotFoundException} с указанным сообщением об ошибке.
     *
     * @param message сообщение об ошибке, поясняющее, какой ресурс не был найден
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
