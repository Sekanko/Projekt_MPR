package pl.edu.pjatk.Projekt_MPR;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.edu.pjatk.Projekt_MPR.model.Computer;
import pl.edu.pjatk.Projekt_MPR.repository.ComputerRepository;
import pl.edu.pjatk.Projekt_MPR.service.ComputerService;
import pl.edu.pjatk.Projekt_MPR.service.StringUtilsService;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        System.out.println(computer.getId());

        verify(stringUtilsService,times(2)).upper(any());
        verify(computerRepository,times(4)).save(any());
    }

    @Test
    public void getAllComputersToLowerCaseWithFirstLetterAsCapital(){
        Computer computer = new Computer("SIN","COS");
        Computer computer2 = new Computer("MMM","OOO");

        when(computerRepository.findAll()).thenReturn(Arrays.asList(computer,computer2));

        service.getAll();

        verify(stringUtilsService,times(4)).lowerExceptFirst(any());
    }

    @Test
    public void getComputerByNameToLowercaseWithFirstLetterAsCapital(){
        Computer computer = new Computer("SIN","COS");
        Computer computer2 = new Computer("SIN","TG");
        Computer computer3 = new Computer("MMM","OOO");

        when(computerRepository.findByName(stringUtilsService.upper("sin"))).thenReturn(Arrays.asList(computer,computer2));
        service.getComputerByName("sin");

        verify(stringUtilsService,times(4)).lowerExceptFirst(any());
    }

    @Test
    public void getComputerByComputerCaseModelToLowercaseWithFirstLetterAsCapital(){
        Computer computer = new Computer("SIN","COS");
        Computer computer2 = new Computer("MMM","TG");
        Computer computer3 = new Computer("SIN","TG");

        when(computerRepository.findByComputerCaseModel(stringUtilsService.upper("tg"))).thenReturn(Arrays.asList(computer2, computer3));
        service.getComputerByComputerCaseModel(computer2.getComputerCaseModel());

        verify(stringUtilsService,times(4)).lowerExceptFirst(any());
    }

    @Test void getInfoOfComputerInPDF(){
        Computer computer = new Computer("SIN","SIN");

        try {
            Field idField = Computer.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(computer, 1L);
        } catch (Exception e) {
            e.printStackTrace();
        }


        when(computerRepository.findById(1L)).thenReturn(Optional.of(computer));
        when(stringUtilsService.lowerExceptFirst(any())).thenReturn("Sin");

        byte[] testPDFInBytes = this.service.getInfo(1L);
        assertNotNull(testPDFInBytes);


        try (PDDocument testPDF = PDDocument.load(testPDFInBytes)){
            assertEquals(1, testPDF.getNumberOfPages());
            PDFTextStripper stripper = new PDFTextStripper();
            String content = stripper.getText(testPDF);
            String compareString = "Id:\n1\nName:\nSin\nComputerCase model:\nSin\nCalc id:\n138\n";
            assertEquals(compareString, content);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
