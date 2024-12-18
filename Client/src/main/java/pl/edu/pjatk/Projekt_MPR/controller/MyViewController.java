package pl.edu.pjatk.Projekt_MPR.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.Projekt_MPR.model.Computer;
import pl.edu.pjatk.Projekt_MPR.service.ComputerService;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MyViewController {
    private final ComputerService computerService;

    public MyViewController(ComputerService computerService) {
        this.computerService = computerService;
    }

    @GetMapping("/view/all")
    public String getAllComputers(Model model) {
        List<Computer> computers = this.computerService.getAll();
        model.addAttribute("computers", computers);
        return "viewAll";
    }

    @GetMapping("/addForm")
    public String displayAddForm(Model model) {
        model.addAttribute("computer", new Computer());
        return "addForm";
    }

    @PostMapping("/addForm")
    public String submitAddForm(@ModelAttribute Computer computer) {
        this.computerService.createComputer(computer);
        return "redirect:/view/all";
    }

    @GetMapping("/updateForm")
    public String displayUpdateForm(Model model) {
        model.addAttribute("computer", new Computer());
        return "updateForm";
    }

    @PostMapping("/updateForm")
    public String submitUpdateForm(@RequestParam("id") Long id, @ModelAttribute Computer computer) {
        Map<String, Object> newValues = new HashMap<>();
        for (Field field : computer.getClass().getDeclaredFields()) {
            String fieldName = field.getName();
            if (fieldName.equals("id") || fieldName.equals("calcId")) {
                continue;
            }
            try {
                field.setAccessible(true);
                newValues.put(fieldName, field.get(computer));
                field.setAccessible(false);
            } catch (Exception e){
                throw new RuntimeException("Not possible throw");
            }
        }
        this.computerService.patchComputer(id, newValues);
        return "redirect:/view/all";
    }

    @GetMapping("/deleteForm")
    public String deleteComputer() {
        return "deleteForm";
    }

    @PostMapping("/deleteForm")
    public String submitDeleteForm(@RequestParam("id") Long id) {
        this.computerService.deleteComputer(id);
        return "redirect:/view/all";
    }
}
