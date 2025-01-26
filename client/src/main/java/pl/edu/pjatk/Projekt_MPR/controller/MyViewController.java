package pl.edu.pjatk.Projekt_MPR.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.Projekt_MPR.model.ComputerDto;
import pl.edu.pjatk.Projekt_MPR.service.ComputerViewService;

import java.lang.reflect.Field;
import java.util.Base64;
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
    public String getAllComputers(Model model, HttpSession session) {
        if (session.getAttribute("message") != null) {
            session.removeAttribute("message");
        }
        List<ComputerDto> computers = this.computerViewService.getAll();
        model.addAttribute("computers", computers);
        return "viewAll";
    }

    @GetMapping("/addForm")
    public String displayAddForm(Model model, HttpSession session) {
        ComputerDto computerDto = getComputerForForm(session);

        model.addAttribute("computer", computerDto);
        return "addForm";
    }

    @PostMapping("/addForm")
    public String submitAddForm(@ModelAttribute ComputerDto computer, HttpSession session) {
        var result = this.computerViewService.createComputer(computer);

        return safeRedirection(result, session, computer, "addForm") ;
    }

    @GetMapping("/updateForm/{id}")
    public String displayUpdateForm(Model model, @PathVariable Long id, HttpSession session) {
        ComputerDto computer;

        if (session.getAttribute("computer") != null) {
            computer = getComputerForForm(session);
        } else{
            computer= computerViewService.getComputer(id);
        }
        model.addAttribute("computer", computer);
        return "updateForm";
    }

    @PostMapping("/updateForm")
    public String submitUpdateForm(@RequestParam("id") Long id, @ModelAttribute ComputerDto computer, HttpSession session) {
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
        var result = this.computerViewService.patchComputer(id, newValues);
        return safeRedirection(result, session, computer, "updateForm/" + id) ;
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

    @GetMapping("getPDF/{id}")
    public String getPDF(@PathVariable Long id, Model model) {
        var pdfInfo = computerViewService.getInfo(id);
        String pdfBase64 = Base64.getEncoder().encodeToString(pdfInfo);
        model.addAttribute("pdfBase64", pdfBase64);
        model.addAttribute("id", id);
        return "infoPage";
    }

    private ComputerDto getComputerForForm(HttpSession session) {
        ComputerDto computerDto = new ComputerDto();
        if (session.getAttribute("computer") != null) {
            computerDto = (ComputerDto) session.getAttribute("computer");
            session.removeAttribute("computer");
        }
        return computerDto;
    }

    private String safeRedirection(String message, HttpSession session, ComputerDto computer, String destination) {
        if (message.equals("success")) {
            return "redirect:/view/all";
        }

        session.setAttribute("message", message);
        session.setAttribute("computer", computer);
        return "redirect:/" + destination;
    }
}
