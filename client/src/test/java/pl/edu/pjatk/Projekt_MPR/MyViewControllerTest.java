package pl.edu.pjatk.Projekt_MPR;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import pl.edu.pjatk.Projekt_MPR.controller.MyViewController;
import pl.edu.pjatk.Projekt_MPR.model.ComputerDto;
import pl.edu.pjatk.Projekt_MPR.service.ComputerViewService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MyViewControllerTest {
    private MyViewController myViewController;
    private ComputerViewService computerViewService;
    private HttpSession session;
    private Model model;

    @BeforeEach
    public void setUp() {
        this.computerViewService = Mockito.mock(ComputerViewService.class);
        this.myViewController = new MyViewController(computerViewService);
        this.session = Mockito.mock(HttpSession.class);
        this.model = Mockito.mock(Model.class);

    }

    @Test
    public void getAllComputersGetMethod() {
        when(this.computerViewService.getAll()).thenReturn(List.of(
                new ComputerDto(),
                new ComputerDto()
        ));
        when(session.getAttribute("message")).thenReturn("This is message");

        String returnValue = this.myViewController.getAllComputers(model, session);
        assertEquals("viewAll", returnValue);
        verify(session).removeAttribute("message");
    }

    @Test
    public void displayAddFormGetMethod() {
        when(session.getAttribute("computer")).thenReturn(new ComputerDto());

        String returnValue = this.myViewController.displayAddForm(model, session);

        verify(session).removeAttribute("computer");
        assertEquals("addForm", returnValue);
    }

    @Test
    public void submitAddFormPostMethodWithCorrectData() {
        ComputerDto computerDto = new ComputerDto();
        when(computerViewService.createComputer(computerDto)).thenReturn("success");

        var returnValue = this.myViewController.submitAddForm(computerDto, session);
        assertEquals("redirect:/view/all", returnValue);

    }
    @Test
    public void submitAddFormPostMethodWithIncorrectData() {
        ComputerDto computerDto = new ComputerDto();
        when(computerViewService.createComputer(computerDto)).thenReturn("Not success");

        var returnValue = this.myViewController.submitAddForm(computerDto, session);
        assertEquals("redirect:/addForm", returnValue);

    }

    @Test
    public void displayUpdateFormGetMethod() {
        ComputerDto computerDto = new ComputerDto();
        when(session.getAttribute("computer")).thenReturn(null);
        when(computerViewService.getComputer(1L)).thenReturn(computerDto);

        String returnValue = this.myViewController.displayUpdateForm(model, 1L, session);
        assertEquals("updateForm", returnValue);
    }
    @Test
    public void submitUpdateFormPostMethodWithCorrectData() {
        ComputerDto computerDto = new ComputerDto();
        computerDto.setId(1L);
        computerDto.setName("Test");
        computerDto.setComputerCaseModel("Test");
        computerDto.setCalcId();

        Map<String, Object> newFields = new HashMap<>();
        newFields.put("name", "Test");
        newFields.put("computerCaseModel", "Test");

        when(computerViewService.patchComputer(1L, newFields)).thenReturn("success");
        String returnValue = this.myViewController.submitUpdateForm(1L, computerDto, session);
        assertEquals("redirect:/view/all", returnValue);

    }
    @Test
    public void submitUpdateFormPostMethodWithIncorrectData() {
        ComputerDto computerDto = new ComputerDto();
        computerDto.setId(1L);
        computerDto.setName("Test");
        computerDto.setComputerCaseModel("Test");
        computerDto.setCalcId();

        Map<String, Object> newFields = new HashMap<>();
        newFields.put("name", "Test");
        newFields.put("computerCaseModel", "Test");

        when(computerViewService.patchComputer(1L, newFields)).thenReturn("Not success");
        String returnValue = this.myViewController.submitUpdateForm(1L, computerDto, session);

        verify(session, times(2)).setAttribute(anyString(), any());
        assertEquals("redirect:/updateForm/1", returnValue);
    }

    @Test
    public void deleteComputerGetMethod() {
        String returnValue = this.myViewController.deleteComputer(model, 1L);
        verify(model).addAttribute(anyString(), any());
        assertEquals("deleteForm", returnValue);
    }

    @Test
    public void deleteComputerPostMethod() {
        ComputerDto computerDto = new ComputerDto();

        String returnValue = this.myViewController.submitDeleteForm(1L);
        verify(computerViewService, times(1)).deleteComputer(1L);
        assertEquals("redirect:/view/all", returnValue);
    }

}
