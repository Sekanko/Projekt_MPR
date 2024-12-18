package pl.edu.pjatk.Projekt_MPR.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pl.edu.pjatk.Projekt_MPR.exception.*;
import pl.edu.pjatk.Projekt_MPR.model.Computer;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class ComputerService {
    @Autowired
    private final RestClient restClient;

    public ComputerService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Computer> getAll() {
        List<Computer> computers = restClient.get()
                .uri("/computer/all")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (computers == null) {
            throw new ComputerNoFoundException();
        }

        return computers;
    }

    public void createComputer(Computer computer) {
        restClient.post()
                .uri("computer")
                .contentType(MediaType.APPLICATION_JSON)
                .body(computer)
                .retrieve()
                .toBodilessEntity();
    }

    public void deleteComputer(Long id) {
        restClient.delete()
                .uri("/computer/delete/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

    public Computer getComputer(Long id) {
        Computer computer = restClient.get()
                .uri("computer/get/id/{id}", id)
                .retrieve()
                .body(Computer.class);

        if (computer == null) {
            throw new ComputerNoFoundException();
        }

        return computer;
    }

    public List<Computer> getComputerByName(String name) {
        List<Computer> computers = restClient.get()
                .uri("computer/get/name/{name}", name)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (computers == null) {
            throw new ComputerNoFoundException();
        }
        return computers;
    }

    public List<Computer> getComputerByComputerCaseModel(String computerCaseModel) {
        List<Computer> computers = restClient.get()
                .uri("computer/get/computerCaseModel/{computerCaseModel}", computerCaseModel)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (computers == null) {
            throw new ComputerNoFoundException();
        }
        return computers;
    }

    public void patchComputer(Long id, Map<String, Object> patch) {
        restClient.patch()
                .uri("/computer/patch/{id}", id)
                .body(patch)
                .retrieve()
                .toBodilessEntity();
    }

    public byte[] getInfo(Long id) {
        return restClient.get()
                .uri("computer/get/info/{id}", id)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}