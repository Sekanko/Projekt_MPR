package pl.edu.pjatk.Projekt_MPR.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.Projekt_MPR.model.Computer;
import pl.edu.pjatk.Projekt_MPR.repository.ComputerRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ComputerService {

    ComputerRepository computerRepository;

    public ComputerService(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;

        computerRepository.save(new Computer("IDK", "Titanic"));
        computerRepository.save(new Computer("Kopkon","Nierra"));
        computerRepository.save(new Computer("Kopmam", "Latun"));
    }

    public List<Computer> getAll() {
        return (List<Computer>) computerRepository.findAll();
    }

    public void createComputer(Computer computer) {
        computer.setCalcId(computer.calcualteId());
        this.computerRepository.save(computer);
    }

    public void deleteComputer(Long id) {
        this.computerRepository.deleteById(id);
    }

    public Computer getComputer(Long id) {
        Optional<Computer> computer = computerRepository.findById(id);
        return computer.orElse(null);
    }


    public List<Computer> getComputerByName(String name) {
        return this.computerRepository.findByName(name);
    }

    public List<Computer> getComputerByComputerCaseModel(String computerCaseModel) {
        return this.computerRepository.findByComputerCaseModel(computerCaseModel);
    }

    public void patchComputer(Long id, Map<String, Object> patch) {
        Computer computer = computerRepository.findById(id).get();

        patch.forEach((key, value) -> {
            switch (key) {
                case "name":
                    computer.setName((String) value);
                    break;
                case "computerCaseModel":
                    computer.setComputerCaseModel((String) value);
                    break;
            }
        });
        computer.setCalcId(computer.calcualteId());
        computerRepository.save(computer);
    }
}
