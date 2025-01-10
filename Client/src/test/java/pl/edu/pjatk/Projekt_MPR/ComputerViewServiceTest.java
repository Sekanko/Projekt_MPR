package pl.edu.pjatk.Projekt_MPR;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.MockServerRestClientCustomizer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;
import pl.edu.pjatk.Projekt_MPR.model.Computer;
import pl.edu.pjatk.Projekt_MPR.service.ComputerViewService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RestClientTest
public class ComputerViewServiceTest {
    private ComputerViewService computerViewService;
    private MockServerRestClientCustomizer customizer = new MockServerRestClientCustomizer();
    private final RestClient.Builder builder = RestClient.builder();

    @BeforeEach
    public void setUp(){
        customizer.customize(builder);
        this.computerViewService = new ComputerViewService(builder.build());
    }

    @Test
    public void createComputer(){
        Computer computer = new Computer("sin", "cos");

        customizer.getServer()
                .expect(MockRestRequestMatchers.requestTo("/computer"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.CREATED));

        computerViewService.createComputer(computer);

        customizer.getServer().verify();
    }

    @Test
    public void deleteComputer(){
        customizer.getServer()
                .expect(MockRestRequestMatchers.requestTo("/computer/delete/1"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.DELETE))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK));
        computerViewService.deleteComputer(1L);

        customizer.getServer().verify();
    }

    @Test
    public void getComputerById(){
        customizer.getServer()
                .expect(MockRestRequestMatchers.requestTo("/computer/get/id/1"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess("""
                        {"id":1 ,"name":"sin", "computerCaseModel":"cos", "calcId":133}
                        """, MediaType.APPLICATION_JSON));
        Computer computer = computerViewService.getComputer(1L);
        assertEquals("sin", computer.getName());
        assertEquals("cos", computer.getComputerCaseModel());
        assertEquals(133, computer.getCalcId());
        customizer.getServer().verify();
    }

    @Test
    public void getComputerByName(){
        customizer.getServer()
                .expect(MockRestRequestMatchers.requestTo("/computer/get/name/sin"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess("""
                        [
                        {"id":1 ,"name":"sin", "computerCaseModel":"cos", "calcId":133},
                        {"id":2 ,"name":"sin", "computerCaseModel":"arcsin", "calcId":200}
                        ]
                        """, MediaType.APPLICATION_JSON));

        List<Computer> computers = computerViewService.getComputerByName("sin");
        assertEquals(2,computers.size());
        assertEquals("sin",computers.get(0).getName());
        assertEquals("sin",computers.get(1).getName());
        customizer.getServer().verify();
    }

    @Test
    public void getComputerByComputerCaseModel(){
        customizer.getServer()
                .expect(MockRestRequestMatchers.requestTo("/computer/get/computerCaseModel/cos"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess("""
                        [
                        {"id":1 ,"name":"sin", "computerCaseModel":"cos", "calcId":133},
                        {"id":2 ,"name":"tg", "computerCaseModel":"cos", "calcId":200}
                        ]
                        """, MediaType.APPLICATION_JSON));
        List<Computer> computers = computerViewService.getComputerByComputerCaseModel("cos");
        assertEquals(2,computers.size());
        assertEquals("cos",computers.get(0).getComputerCaseModel());
        assertEquals("cos",computers.get(1).getComputerCaseModel());
        customizer.getServer().verify();
    }


}
