package pl.edu.pjatk.Projekt_MPR.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjatk.Projekt_MPR.model.Computer;
import pl.edu.pjatk.Projekt_MPR.services.ComputerService;

import java.util.List;

@RestController
public class MyRestController {
    private ComputerService computerService;

    @Autowired
    public MyRestController(ComputerService computerService) {
        this.computerService = computerService;
    }

    @GetMapping("computer/all")
    public List<Computer> getAll() {
        return computerService.getAll();
    }

    @PostMapping("computer")
    public void create(@RequestBody Computer computer){
        this.computerService.createComputer(computer);
    }
}
