package pl.edu.pjatk.Projekt_MPR;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.edu.pjatk.Projekt_MPR.model.Computer;
import pl.edu.pjatk.Projekt_MPR.repository.ComputerRepository;
import pl.edu.pjatk.Projekt_MPR.service.ComputerService;
import pl.edu.pjatk.Projekt_MPR.service.StringUtilsService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ComputerServiceTest {
    private ComputerService service;
    private StringUtilsService stringUtilsService;
    private ComputerRepository computerRepository;

    @BeforeEach
    public void setUp(){
        this.stringUtilsService = Mockito.mock(StringUtilsService.class);
        this.computerRepository = Mockito.mock(ComputerRepository.class);
        this.service = new ComputerService(computerRepository, stringUtilsService);
    }

    @Test
    public void createSetsComputerToUpperCase(){
        Computer computer = new Computer("name","case");
        this.service.createComputer(computer);

        verify(stringUtilsService,times(2)).upper(any());
        verify(computerRepository,times(4)).save(any());
    }

}
