package pl.edu.pjatk.Projekt_MPR.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("computer/get/{id}")
    public Computer getComputer(@PathVariable int id) {
        return this.computerService.getComputer(id);
    }

    @PostMapping("computer")
    public void create(@RequestBody Computer computer){
        this.computerService.createComputer(computer);
    }

    @DeleteMapping("computer/delete/{id}")
    public void delete(@PathVariable int id){
        this.computerService.deleteComputer(id);
    }

    @PutMapping("computer/put/{id}")
    public void update(@PathVariable int id, @RequestBody Computer updatedComputer){

        if(this.computerService.getComputer(id) != null){
            this.computerService.updateData(id,updatedComputer);
        }
    }
}
