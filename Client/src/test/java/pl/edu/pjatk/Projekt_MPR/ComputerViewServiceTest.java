package pl.edu.pjatk.Projekt_MPR;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.MockServerRestClientCustomizer;
import org.springframework.web.client.RestClient;
import pl.edu.pjatk.Projekt_MPR.service.ComputerViewService;
import pl.edu.pjatk.Projekt_MPR.service.StringUtilsService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RestClientTest
public class ComputerViewServiceTest {
    private ComputerViewService service;
    private StringUtilsService stringUtilsService;
    private MockServerRestClientCustomizer customizer = new MockServerRestClientCustomizer();
    private RestClient.Builder builder = RestClient.builder();

    @BeforeEach
    public void setUp(){
        this.stringUtilsService = mock(StringUtilsService.class);
        customizer.customize(builder);
        this.service = new ComputerViewService(stringUtilsService, builder.build());
    }

//    @Test
//    public void createSetsComputerToUpperCase(){
//        Computer computer = new Computer("SIN","COS");
//
//        when(stringUtilsService.upper(any())).thenReturn("upperWorks");
//        this.service.createComputer(computer);
//
//    }
    //Nie dizała V
//@Test
//public void getComputerById() {
//    Long id = 1L;
//    customizer.getServer()
//            .expect(MockRestRequestMatchers.requestTo("http://localhost:8081/computer/get/id/" + id))
//            .andRespond(MockRestResponseCreators.withSuccess(
//                    """
//                    {
//                        "id": 1,
//                        "name": "sin",
//                        "computerCaseModel": "cos",
//                        "calcId": 120
//                    }
//                    """, MediaType.APPLICATION_JSON
//            ));
//
//    Computer computer = service.getComputer(id);
//    int a = 0;
//    assertNotNull(computer);
//    assertEquals(1L, computer.getId());
//    assertEquals("Sin", computer.getName());
//    assertEquals("Cos", computer.getComputerCaseModel());
//}

//
//
//    @Test
//    public void getAllComputersToLowerCaseWithFirstLetterAsCapital(){
//        Computer computer = new Computer("SIN","COS");
//        Computer computer2 = new Computer("MMM","OOO");
//
//        when(computerRepository.findAll()).thenReturn(Arrays.asList(computer,computer2));
//
//        service.getAll();
//
//        verify(stringUtilsService,times(4)).lowerExceptFirst(any());
//    }
//
//    @Test
//    public void getComputerByNameToLowercaseWithFirstLetterAsCapital(){
//        Computer computer = new Computer("SIN","COS");
//        Computer computer2 = new Computer("SIN","TG");
//        Computer computer3 = new Computer("MMM","OOO");
//
//        when(computerRepository.findByName(stringUtilsService.upper("sin"))).thenReturn(Arrays.asList(computer,computer2));
//        service.getComputerByName("sin");
//
//        verify(stringUtilsService,times(4)).lowerExceptFirst(any());
//    }
//
//    @Test
//    public void getComputerByComputerCaseModelToLowercaseWithFirstLetterAsCapital(){
//        Computer computer = new Computer("SIN","COS");
//        Computer computer2 = new Computer("MMM","TG");
//        Computer computer3 = new Computer("SIN","TG");
//
//        when(computerRepository.findByComputerCaseModel(stringUtilsService.upper("tg"))).thenReturn(Arrays.asList(computer2, computer3));
//        service.getComputerByComputerCaseModel(computer2.getComputerCaseModel());
//
//        verify(stringUtilsService,times(4)).lowerExceptFirst(any());
//    }
//
//    @Test
//    public void getInfoOfComputerInPDF(){
//        Computer computer = new Computer("SIN","COS");
//
//        try {
//            Field idField = Computer.class.getDeclaredField("id");
//            idField.setAccessible(true);
//            idField.set(computer, 1L);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        when(computerRepository.findById(1L)).thenReturn(Optional.of(computer));
//        when(stringUtilsService.lowerExceptFirst("SIN")).thenReturn("Sin");
//        when(stringUtilsService.lowerExceptFirst("COS")).thenReturn("Cos");
//
//        byte[] testPDFInBytes = this.service.getInfo(1L);
//        assertNotNull(testPDFInBytes);
//
//        try (PDDocument testPDF = PDDocument.load(testPDFInBytes)){
//            assertEquals(1, testPDF.getNumberOfPages());
//            PDFTextStripper stripper = new PDFTextStripper();
//            String content = stripper.getText(testPDF);
//            String compareString = "Id:\n1\nName:\nSin\nComputerCase model:\nCos\nCalc id:\n133\n";
//            assertEquals(compareString, content);
//
//        } catch (IOException e) {
//            throw new RuntimeException("There was unexpected error in PDDocument",e);
//        }
//    }
//
//    @Test
//    public void patchComputerWithCorrectValues(){
//        Computer computer = new Computer("SIN","COS");
//
//        when(computerRepository.findById(1L)).thenReturn(Optional.of(computer));
//        when(stringUtilsService.upper("tg")).thenReturn("TG");
//        when(stringUtilsService.upper("TG")).thenReturn("TG");
//        when(stringUtilsService.upper("ctg")).thenReturn("CTG");
//        when(stringUtilsService.upper("lol")).thenReturn("LOL");
//
//        Map<String, Object> testValues = new HashMap<>();
//        testValues.put("name","tg");
//        testValues.put("computerCaseModel","ctg");
//
//        this.service.patchComputer(1L, testValues);
//
//        assertEquals("TG", computer.getName());
//        assertEquals("CTG", computer.getComputerCaseModel());
//
//        testValues.put("name","");
//        testValues.put("computerCaseModel","lol");
//
//        this.service.patchComputer(1L, testValues);
//        int a = 0;
//        assertEquals("TG", computer.getName());
//        assertEquals("LOL", computer.getComputerCaseModel());
//    }
//
//    @Test
//    public void patchComputerWithIncorrectValues(){
//        Computer computer = new Computer("SIN","COS");
//        when(computerRepository.findById(1L)).thenReturn(Optional.of(computer));
//        when(stringUtilsService.upper("tg")).thenReturn("TG");
//        when(stringUtilsService.upper("ctg")).thenReturn("CTG");
//        Map<String, Object> testValues = new HashMap<>();
//
//        testValues.put("name", null);
//        assertThrows(ComputerNewFieldValueIsEmptyException.class, () -> this.service.patchComputer(1L, testValues));
//
//        testValues.put("name", 1);
//        assertThrows(ComputerFieldDoesntExistsException.class, () -> this.service.patchComputer(1L, testValues));
//
//        testValues.put("car", "Nissan");
//        assertThrows(ComputerFieldDoesntExistsException.class, () -> this.service.patchComputer(1L, testValues));
//    }
//
//    @Test
//    public void deleteComputerWithIdInRange(){
//        when(computerRepository.existsById(1L)).thenReturn(true);
//        this.service.deleteComputer(1L);
//
//        verify(computerRepository, times(1)).deleteById(1L);
//    }
//
//    @Test
//    public void everyComputerNoFoundException(){
//        var exception = ComputerNoFoundException.class;
//        //delete
//        when(computerRepository.existsById(1L)).thenReturn(false);
//        assertThrows(exception, () -> this.service.deleteComputer(1L));
//        //getComputer
//        assertThrows(exception, () -> this.service.getComputer(1L));
//        //getComputerByName
//        assertThrows(exception, () -> this.service.getComputerByName("sin"));
//        //getComputerByComputerCaseModel
//        assertThrows(exception, () -> this.service.getComputerByComputerCaseModel("cos"));
//        //patchComputer
//        assertThrows(exception, () -> this.service.patchComputer(1L, null));
//
//    }
//

}
