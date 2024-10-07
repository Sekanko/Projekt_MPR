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
}
