package pl.edu.pjatk.Projekt_MPR;

import org.junit.jupiter.api.Test;
import pl.edu.pjatk.Projekt_MPR.service.StringUtilsService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilsServiceTest {
    private StringUtilsService stringUtilsService = new StringUtilsService();

    @Test
    public void upperTest(){
        String testText = "Sinus123";
        String result = stringUtilsService.upper(testText);
        assertEquals("SINUS123", result);
    }

    @Test
    public void lowerExceptFirstTest(){
        String testText = "SINUS123";
        String result = stringUtilsService.lowerExceptFirst(testText);
        assertEquals("Sinus123", result);
    }

}
