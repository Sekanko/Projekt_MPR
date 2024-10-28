package pl.edu.pjatk.Projekt_MPR.service;

import org.springframework.stereotype.Service;
import pl.edu.pjatk.Projekt_MPR.model.Computer;
import pl.edu.pjatk.Projekt_MPR.repository.ComputerRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ComputerService {

    private ComputerRepository computerRepository;
    private StringUtilsService stringUtilsService;

    public ComputerService(ComputerRepository computerRepository, StringUtilsService stringUtilsService) {
        this.computerRepository = computerRepository;
        this.stringUtilsService = stringUtilsService;

        computerRepository.save(new Computer("IDK", "TITANIC"));
        computerRepository.save(new Computer("IDK","NIERRA"));
        computerRepository.save(new Computer("KOMP", "LATUN"));
    }

    public List<Computer> getAll() {
        return view((List<Computer>) computerRepository.findAll());
    }

    public void createComputer(Computer computer) {
        computer.setName(stringUtilsService.upper(computer.getName()));
        computer.setComputerCaseModel(stringUtilsService.upper(computer.getComputerCaseModel()));
        computer.setCalcId(computer.calcualteId());
        this.computerRepository.save(computer);
    }

    public void deleteComputer(Long id) {
        this.computerRepository.deleteById(id);
    }

    public Computer getComputer(Long id) {
        Optional<Computer> computer = computerRepository.findById(id);

        return computer.map(value -> view(Collections.singletonList(value))
                .get(0))
                .orElse(null);
    }


    public List<Computer> getComputerByName(String name) {
        List<Computer> computers = computerRepository.findByName(stringUtilsService.upper(name));
        return view(computers);
    }

    public List<Computer> getComputerByComputerCaseModel(String computerCaseModel) {
        List<Computer> computers = computerRepository.findByComputerCaseModel(stringUtilsService.upper(computerCaseModel));
        return view(computers);
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

    private List<Computer> view(List<Computer> list){
        list.forEach(computer -> {
            computer.setName(stringUtilsService.lowerExceptFirst(computer.getName()));
            computer.setComputerCaseModel(stringUtilsService.lowerExceptFirst(computer.getComputerCaseModel()));
        });
        return list;
    }
}
