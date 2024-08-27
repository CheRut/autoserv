package com.garage.autoservice.exception;

/**
 * Исключение, указывающее на то, что запрашиваемый ресурс не был найден.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
