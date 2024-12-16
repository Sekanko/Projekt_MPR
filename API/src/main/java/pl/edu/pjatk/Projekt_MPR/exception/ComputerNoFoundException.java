package pl.edu.pjatk.Projekt_MPR.exception;

public class ComputerNoFoundException extends RuntimeException {
    public ComputerNoFoundException() {
        super("Computer not found");
    }
}
