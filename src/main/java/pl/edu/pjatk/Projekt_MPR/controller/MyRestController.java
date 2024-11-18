package pl.edu.pjatk.Projekt_MPR.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Computer>> getAll() {
        return new ResponseEntity<>(computerService.getAll(), HttpStatus.OK);
    }

    @GetMapping("computer/get/id/{id}")
    public ResponseEntity<Computer> getComputer(@PathVariable Long id) {
        return new ResponseEntity<>(this.computerService.getComputer(id), HttpStatus.OK);
    }

    @GetMapping("computer/get/name/{name}")
    public ResponseEntity<List<Computer>> getComputerByName(@PathVariable String name) {
        return new ResponseEntity<>(this.computerService.getComputerByName(name), HttpStatus.OK);
    }
    @GetMapping("computer/get/computerCaseModel/{computerCaseModel}")
    public ResponseEntity<List<Computer>> getComputerCaseModel(@PathVariable String computerCaseModel) {
        return new ResponseEntity<>(this.computerService.getComputerByComputerCaseModel(computerCaseModel), HttpStatus.OK);
    }

    @PostMapping("computer")
    public ResponseEntity<Computer> create(@RequestBody Computer computer){
        this.computerService.createComputer(computer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("computer/delete/{id}")
    public ResponseEntity<Computer> delete(@PathVariable Long id){
        this.computerService.deleteComputer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("computer/patch/{id}")
    public ResponseEntity<Computer> patch(@PathVariable Long id, @RequestBody Map<String, Object> patch){
        this.computerService.patchComputer(id, patch);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
