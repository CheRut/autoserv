package com.garage.autoservice.exception;

/**
 * Исключение, указывающее на то, что запрос был некорректен.
 */
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
