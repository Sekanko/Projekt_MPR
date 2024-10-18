package pl.edu.pjatk.Projekt_MPR.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.Projekt_MPR.model.Computer;
import pl.edu.pjatk.Projekt_MPR.service.ComputerService;

import java.util.List;
import java.util.Map;

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

    @GetMapping("computer/get/id/{id}")
    public Computer getComputer(@PathVariable Long id) {
        return this.computerService.getComputer(id);
    }

    @GetMapping("computer/get/name/{name}")
    public List<Computer> getComputerByName(@PathVariable String name) {
        return this.computerService.getComputerByName(name);
    }
    @GetMapping("computer/get/computerCaseModel/{computerCaseModel}")
    public List<Computer> getComputerCaseModel(@PathVariable String computerCaseModel) {
        return this.computerService.getComputerByComputerCaseModel(computerCaseModel);
    }

    @PostMapping("computer")
    public void create(@RequestBody Computer computer){
        this.computerService.createComputer(computer);
    }

    @DeleteMapping("computer/delete/{id}")
    public void delete(@PathVariable Long id){
        this.computerService.deleteComputer(id);
    }

    @PutMapping("computer/put/{id}")
    public void update(@PathVariable Long id, @RequestBody Computer updatedComputer){
        this.computerService.updateData(id, updatedComputer);
    }

    @PatchMapping("computer/patch/{id}")
    public void patch(@PathVariable Long id, @RequestBody Map<String, Object> patch){
        this.computerService.patchComputer(id, patch);
    }
}
