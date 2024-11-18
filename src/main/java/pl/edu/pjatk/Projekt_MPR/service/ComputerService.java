package pl.edu.pjatk.Projekt_MPR.service;

import org.springframework.stereotype.Service;
import pl.edu.pjatk.Projekt_MPR.exception.ComputerFieldDoesntExistsException;
import pl.edu.pjatk.Projekt_MPR.exception.ComputerNewFieldValueIsEmptyException;
import pl.edu.pjatk.Projekt_MPR.exception.ComputerNoFoundException;
import pl.edu.pjatk.Projekt_MPR.exception.ComputerTakenCalculatedIdException;
import pl.edu.pjatk.Projekt_MPR.model.Computer;
import pl.edu.pjatk.Projekt_MPR.repository.ComputerRepository;

import java.util.*;

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

        if (isCalculatedIdTaken(computer.getCalcId())) {
            throw new ComputerTakenCalculatedIdException();
        }

        this.computerRepository.save(computer);
    }

    public void deleteComputer(Long id) {
        if (computerRepository.findById(id).isEmpty()){
            throw new ComputerNoFoundException();
        }

        this.computerRepository.deleteById(id);
    }

    public Computer getComputer(Long id) {
        Optional<Computer> computer = computerRepository.findById(id);

        if (computer.isEmpty()){
            throw new ComputerNoFoundException();
        }

        return computer.map(value -> view(Collections.singletonList(value))
                .getFirst()).get();
    }


    public List<Computer> getComputerByName(String name) {
        List<Computer> computers = computerRepository.findByName(stringUtilsService.upper(name));
        if (computers.isEmpty()){
            throw new ComputerNoFoundException();
        }
        return view(computers);
    }

    public List<Computer> getComputerByComputerCaseModel(String computerCaseModel) {
        List<Computer> computers = computerRepository.findByComputerCaseModel(stringUtilsService.upper(computerCaseModel));
        if (computers.isEmpty()){
            throw new ComputerNoFoundException();
        }
        return view(computers);
    }

    public void patchComputer(Long id, Map<String, Object> patch) {
        Optional<Computer> optionalComputer = computerRepository.findById(id);

        if (optionalComputer.isEmpty()){
            throw new ComputerNoFoundException();
        }

        Computer computer = optionalComputer.get();

        patch.forEach((key, value) -> {
            if (value == "" || value == null){
                throw new ComputerNewFieldValueIsEmptyException();
            }
            switch (key) {
                case "name":
                    computer.setName((String) value);
                    break;
                case "computerCaseModel":
                    computer.setComputerCaseModel((String) value);
                    break;
                default:
                    throw new ComputerFieldDoesntExistsException();
            }
        });

        computer.setCalcId(computer.calcualteId());

        if (isCalculatedIdTaken(computer.getCalcId())) {
            throw new ComputerTakenCalculatedIdException();
        }

        computerRepository.save(computer);
    }

    private List<Computer> view(List<Computer> list){
        list.forEach(computer -> {
            computer.setName(stringUtilsService.lowerExceptFirst(computer.getName()));
            computer.setComputerCaseModel(stringUtilsService.lowerExceptFirst(computer.getComputerCaseModel()));
        });
        return list;
    }

    private boolean isCalculatedIdTaken(int calcId){
        List<Computer> computers = (ArrayList <Computer>) computerRepository.findAll();
        return !computers.stream()
                .filter(computer -> computer.getCalcId() == calcId)
                .toList()
                .isEmpty();
    }
}
