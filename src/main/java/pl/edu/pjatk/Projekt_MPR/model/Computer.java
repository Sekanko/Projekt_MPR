package pl.edu.pjatk.Projekt_MPR.model;

public class Computer {
    private String name;
    private String computerCaseModel;

    public Computer(String name, String computerCaseModel) {
        this.name = name;
        this.computerCaseModel = computerCaseModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComputerCaseModel() {
        return computerCaseModel;
    }

    public void setComputerCaseModel(String computerCaseModel) {
        this.computerCaseModel = computerCaseModel;
    }


}
