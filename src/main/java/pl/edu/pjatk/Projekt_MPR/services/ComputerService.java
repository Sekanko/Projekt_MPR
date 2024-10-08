package pl.edu.pjatk.Projekt_MPR.services;

import org.springframework.stereotype.Component;
import pl.edu.pjatk.Projekt_MPR.model.Computer;

import java.util.ArrayList;
import java.util.List;

@Component
public class ComputerService {
    List<Computer> computerList = new ArrayList<>();

    public ComputerService() {
        computerList.add(new Computer("Kopos", "Titanic"));
        computerList.add(new Computer("Kopkon","Nierra"));
        computerList.add(new Computer("Kopmam", "Latun"));
    }

    public List<Computer> getAll() {
        return this.computerList;
    }
    public void createComputer(Computer computer) {
        this.computerList.add(computer);
    }

    public void deleteComputer(int id) {
        if (id >=0 && id < this.computerList.size()) {
            this.computerList.remove(id);
        }
    }

    public Computer getComputer(int id) {
        return this.computerList.get(id);
    }

    public void updateData(int id ,Computer updatedComputer) {
        computerList.get(id).setName(updatedComputer.getName());
        computerList.get(id).setComputerCaseModel(updatedComputer.getComputerCaseModel());
    }
}
