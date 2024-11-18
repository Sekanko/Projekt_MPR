package pl.edu.pjatk.Projekt_MPR.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ComputerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value={ComputerNoFoundException.class})
    public ResponseEntity<Object> handleNotFound(RuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ComputerTakenCalculatedIdException.class})
    public ResponseEntity<Object> handleTakenCalculatedId(RuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ComputerFieldDoesntExistsException.class})
    public ResponseEntity<Object> handleFieldDoesntExists(RuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ComputerNewFieldValueIsEmptyException.class})
    public ResponseEntity<Object> handleNewFieldValueIsEmpty(RuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
