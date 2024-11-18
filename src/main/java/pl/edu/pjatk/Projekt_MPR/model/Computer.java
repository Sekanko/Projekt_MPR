package pl.edu.pjatk.Projekt_MPR.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String computerCaseModel;
    private int calcId;

    public Computer(String name, String computerCaseModel) {
        this.name = name;
        this.computerCaseModel = computerCaseModel;
        this.calcId = calcualteId();
    }

    public Computer() {

    }

    public Long getId() {
        return id;
    }

    public int getCalcId() {
        return calcId;
    }

    public void setCalcId(int calcId) {
        this.calcId = calcId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.calcId = calcualteId();
    }

    public String getComputerCaseModel() {
        return computerCaseModel;
    }

    public void setComputerCaseModel(String computerCaseModel) {
        this.computerCaseModel = computerCaseModel;
        this.calcId = calcualteId();
    }

    public int calcualteId(){
        String merged = this.name + this.computerCaseModel;
        int sum = 0;
        for (Character c : merged.toCharArray()) {
            sum += Character.getNumericValue(c);
        }
        return sum;
    }


}
