package pl.edu.pjatk.Projekt_MPR.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.Projekt_MPR.model.ComputerDto;
import pl.edu.pjatk.Projekt_MPR.service.ComputerViewService;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MyViewController {
    private final ComputerViewService computerViewService;

    public MyViewController(ComputerViewService computerViewService) {
        this.computerViewService = computerViewService;
    }

    @GetMapping("/view/all")
    public String getAllComputers(Model model) {
        List<ComputerDto> computers = this.computerViewService.getAll();
        model.addAttribute("computers", computers);
        return "viewAll";
    }

    @GetMapping("/addForm")
    public String displayAddForm(Model model) {
        model.addAttribute("computer", new ComputerDto());
        return "addForm";
    }

    @PostMapping("/addForm")
    public String submitAddForm(@ModelAttribute ComputerDto computer) {
        this.computerViewService.createComputer(computer);
        return "redirect:/view/all";
    }

    @GetMapping("/updateForm/{id}")
    public String displayUpdateForm(Model model, @PathVariable Long id) {
        ComputerDto computer = computerViewService.getComputer(id);
        model.addAttribute("computer", computer);
        return "updateForm";
    }

    @PostMapping("/updateForm")
    public String submitUpdateForm(@RequestParam("id") Long id, @ModelAttribute ComputerDto computer) {
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
        this.computerViewService.patchComputer(id, newValues);
        return "redirect:/view/all";
    }

    @GetMapping("/deleteForm/{id}")
    public String deleteComputer(Model model, @PathVariable Long id) {
        ComputerDto computer = computerViewService.getComputer(id);
        model.addAttribute("computer", computer);
        return "deleteForm";
    }

    @PostMapping("/deleteForm/{id}")
    public String submitDeleteForm(@PathVariable Long id) {
        this.computerViewService.deleteComputer(id);
        return "redirect:/view/all";
    }
}
