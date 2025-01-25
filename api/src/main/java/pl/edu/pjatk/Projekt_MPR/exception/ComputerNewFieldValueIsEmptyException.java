package pl.edu.pjatk.Projekt_MPR.exception;

public class ComputerNewFieldValueIsEmptyException extends RuntimeException {
    public ComputerNewFieldValueIsEmptyException() {
        super("Field is empty");
    }
}
