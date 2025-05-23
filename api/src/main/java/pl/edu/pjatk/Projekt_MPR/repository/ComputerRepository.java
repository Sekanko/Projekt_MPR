package pl.edu.pjatk.Projekt_MPR.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjatk.Projekt_MPR.model.Computer;

import java.util.List;

@Repository
public interface ComputerRepository extends CrudRepository<Computer, Long> {
    public List<Computer> findByName(String name);
    public List<Computer> findByComputerCaseModel(String computerCaseModel);
}
