package pl.edu.pjatk.Projekt_MPR.exception;

public class ComputerFieldDoesntExistsException extends RuntimeException {
    public ComputerFieldDoesntExistsException() {
        super("One or more computer fields do not exist");
    }
}
