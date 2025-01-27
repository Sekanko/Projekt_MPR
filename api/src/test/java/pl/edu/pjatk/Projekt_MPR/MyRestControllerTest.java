package pl.edu.pjatk.Projekt_MPR;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import pl.edu.pjatk.Projekt_MPR.controller.MyRestController;
import pl.edu.pjatk.Projekt_MPR.model.Computer;
import pl.edu.pjatk.Projekt_MPR.service.ComputerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MyRestControllerTest {
    private MyRestController myRestController;
    private ComputerService computerService;

    @BeforeEach
    public void setUp() {
        this.computerService = Mockito.mock(ComputerService.class);
        this.myRestController = new MyRestController(computerService);
    }

    @Test
    public void getAllComputers() {
        List<Computer> computers = List.of(
                new Computer("Sin", "Cos"),
                new Computer("Tg", "Ctg")
        );
        when(this.computerService.getAll()).thenReturn(computers);

        var result = this.myRestController.getAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(computers, result.getBody());
    }

    @Test
    public void getComputerById() {
        Computer computer = new Computer("Sin", "Cos");

        when(this.computerService.getComputer(2L)).thenReturn(computer);

        var result = this.myRestController.getComputer(2L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(computer, result.getBody());
    }

    @Test
    public void getComputerByName() {
        Computer computer = new Computer("Sin", "Cos");
        when(this.computerService.getComputerByName("Sin")).thenReturn(List.of(computer));
        var result = this.myRestController.getComputerByName("Sin");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(computer), result.getBody());
    }

    @Test
    public void getComputerByComputerCaseModel() {
        Computer computer = new Computer("Sin", "Cos");
        when(this.computerService.getComputerByComputerCaseModel("Cos")).thenReturn(List.of(computer));
        var result = this.myRestController.getComputerCaseModel("Cos");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(computer), result.getBody());
    }

    @Test
    public void createComputer() {
        Computer computer = new Computer("Sin", "Cos");
        this.computerService.createComputer(computer);
        var result = this.myRestController.create(computer);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
    @Test
    public void deleteComputer() {
        var result = this.myRestController.delete(2L);
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
    }
    @Test
    public void updateComputer() {
        var result = this.myRestController.patch(2L, Map.of("name", "Sin"));
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
    }


}
