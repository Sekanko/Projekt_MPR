package pl.edu.pjatk.Projekt_MPR.model;

import java.util.Objects;

public class ComputerDto {
    private Long id;
    private String name;
    private String computerCaseModel;
    private int calcId;

    public ComputerDto(String name, String computerCaseModel) {
        this.name = name;
        this.computerCaseModel = computerCaseModel;
        setCalcId();
    }

    public ComputerDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCalcId() {
        return calcId;
    }

    public void setCalcId(){
        String merged = this.name + this.computerCaseModel;
        int sum = 0;
        for (Character c : merged.toCharArray()) {
            sum += Character.getNumericValue(c);
        }
        this.calcId = sum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setCalcId();
    }

    public String getComputerCaseModel() {
        return computerCaseModel;
    }

    public void setComputerCaseModel(String computerCaseModel) {
        this.computerCaseModel = computerCaseModel;
        setCalcId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComputerDto computer = (ComputerDto) o;
        return calcId == computer.calcId && Objects.equals(id, computer.id) && Objects.equals(name, computer.name) && Objects.equals(computerCaseModel, computer.computerCaseModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, computerCaseModel, calcId);
    }

}
