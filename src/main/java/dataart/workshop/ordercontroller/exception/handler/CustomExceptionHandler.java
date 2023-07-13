package dataart.workshop.ordercontroller.exception.handler;

import dataart.workshop.ordercontroller.exception.OrderNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final String ERROR = "error";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String NOT_FOUND = "Not found";

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleBookNotFoundException(OrderNotFoundException exception) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ERROR, NOT_FOUND);
        map.put(ERROR_MESSAGE, exception.getMessage());

        return new ResponseEntity<>(map, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
