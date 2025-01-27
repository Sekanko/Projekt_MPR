package pl.edu.pjatk.Projekt_MPR;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.edu.pjatk.Projekt_MPR.exception.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComputerExceptionHandlerTest {

    private final ComputerExceptionHandler exceptionHandler = new ComputerExceptionHandler();

    @Test
    void handleComputerNotFoundException() {
        RuntimeException exception = new ComputerNoFoundException();

        ResponseEntity<Object> response = exceptionHandler.handleNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Computer not found", response.getBody());
    }

    @Test
    void handleComputerTakenCalculatedIdException() {
        RuntimeException exception = new ComputerTakenCalculatedIdException();

        ResponseEntity<Object> response = exceptionHandler.handleTakenCalculatedId(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("This calculated id was taken. Change fields of your Computer to proceed", response.getBody());
    }

    @Test
    void handleComputerFieldDoesntExistsException() {
        RuntimeException exception = new ComputerFieldDoesntExistsException();

        ResponseEntity<Object> response = exceptionHandler.handleFieldDoesntExists(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("One or more computer fields do not exist", response.getBody());
    }

    @Test
    void handleComputerNewFieldValueIsEmptyException() {
        RuntimeException exception = new ComputerNewFieldValueIsEmptyException();

        ResponseEntity<Object> response = exceptionHandler.handleNewFieldValueIsEmpty(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Field is empty", response.getBody());
    }
}
