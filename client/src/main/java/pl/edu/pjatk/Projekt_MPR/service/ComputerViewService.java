package pl.edu.pjatk.Projekt_MPR.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pl.edu.pjatk.Projekt_MPR.model.ComputerDto;

import java.util.List;
import java.util.Map;

@Service
public class ComputerViewService {
    private final RestClient restClient;

    public ComputerViewService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<ComputerDto> getAll() {
        List<ComputerDto> computers = restClient.get()
                .uri("/computer/all")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        return computers;
    }

    public String createComputer(ComputerDto computer) {
        try{
            restClient.post()
                    .uri("/computer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(computer)
                    .retrieve()
                    .toBodilessEntity();
            return "success";
        } catch (Exception e){
            return getMessageWithoutCodeFromException(e.getMessage());
        }

    }

    public void deleteComputer(Long id) {
        restClient.delete()
                .uri("/computer/delete/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

    public ComputerDto getComputer(Long id) {
        return restClient.get()
                .uri("/computer/get/id/{id}", id)
                .retrieve()
                .body(ComputerDto.class);
    }

    public List<ComputerDto> getComputerByName(String name) {
        List<ComputerDto> computers = restClient.get()
                .uri("/computer/get/name/{name}", name)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
        return computers;
    }

    public List<ComputerDto> getComputerByComputerCaseModel(String computerCaseModel) {
        List<ComputerDto> computers = restClient.get()
                .uri("/computer/get/computerCaseModel/{computerCaseModel}", computerCaseModel)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        return computers;
    }

    public String patchComputer(Long id, Map<String, Object> patch) {
        try {
            restClient.patch()
                    .uri("/computer/patch/{id}", id)
                    .body(patch)
                    .retrieve()
                    .toBodilessEntity();
            return "success";
        } catch (Exception e){
            return getMessageWithoutCodeFromException(e.getMessage());
        }
    }

    public byte[] getInfo(Long id) {
        return restClient.get()
                .uri("/computer/get/info/{id}", id)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    private String getMessageWithoutCodeFromException(String exceptionMessage){
        int start = exceptionMessage.indexOf('"') + 1;
        int end = exceptionMessage.indexOf('"', start);

        return exceptionMessage.substring(start, end);
    }
}