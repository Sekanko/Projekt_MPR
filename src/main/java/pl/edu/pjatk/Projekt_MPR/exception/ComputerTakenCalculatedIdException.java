package pl.edu.pjatk.Projekt_MPR.exception;

public class ComputerTakenCalculatedIdException extends RuntimeException {
    public ComputerTakenCalculatedIdException() {
        super("This calculated id was taken. Change fields of your Computer to proceed");
    }
}
