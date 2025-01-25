package pl.edu.pjatk.Projekt_MPR.exception;

public class ComputerPDFInfoWasntCreatedException extends RuntimeException {
    public ComputerPDFInfoWasntCreatedException() {
        super("There was an error creating the computer PDF info");
    }
}
