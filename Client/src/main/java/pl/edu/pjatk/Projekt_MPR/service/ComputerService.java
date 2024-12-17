package pl.edu.pjatk.Projekt_MPR.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pl.edu.pjatk.Projekt_MPR.exception.*;
import pl.edu.pjatk.Projekt_MPR.model.Computer;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class ComputerService {

    private final StringUtilsService stringUtilsService;
    private final RestClient restClient;

    public ComputerService(StringUtilsService stringUtilsService) {
        this.stringUtilsService = stringUtilsService;
        this.restClient = RestClient.create("http://localhost:8081");
    }

    public List<Computer> getAll() {
        List<Computer> computers = restClient.get()
                .uri("/computer/all")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (computers == null) {
            throw new ComputerNoFoundException();
        }

        return view(computers);
    }

    public void createComputer(Computer computer) {
        saveComputer(computer);
    }

    public void deleteComputer(Long id) {

    }

    public Computer getComputer(Long id) {
        Computer computer = restClient.get()
                .uri("computer/get/id/{id}", id)
                .retrieve()
                .body(Computer.class);

        if (computer == null){
            throw new ComputerNoFoundException();
        }

        return view(Collections.singletonList(computer)).getFirst();
    }

    public List<Computer> getComputerByName(String name) {
        List<Computer> computers = restClient.get()
                .uri("computer/get/name/{name}", name)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (computers == null) {
            throw new ComputerNoFoundException();
        }
        return view(computers);
    }

    public List<Computer> getComputerByComputerCaseModel(String computerCaseModel) {
        List<Computer> computers = restClient.get()
                .uri("computer/get/computerCaseModel/{computerCaseModel}", computerCaseModel)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (computers == null){
            throw new ComputerNoFoundException();
        }
        return view(computers);
    }

    public void patchComputer(Long id, Map<String, Object> patch) {
        Computer computer = getComputer(id);

        var fieldNames = Arrays.stream(Computer.class
                .getDeclaredFields())
                .filter(field -> !field.getName().equals("id") && !field.getName().equals("calcId"))
                .toList();

        patch.forEach((fieldName, fieldNewValue) -> {
            if (fieldNewValue == null){
                throw new ComputerNewFieldValueIsEmptyException();
            }

            if (!fieldNewValue.equals("") && fieldNames.stream().anyMatch(field -> field.getName().equals(fieldName))){
                computerSetValueByFieldName(fieldName, fieldNewValue, computer);
            }
        });
        saveComputer(computer);
    }

    public byte[] getInfo(Long id) {
        Computer computer = getComputer(id);
        try (PDDocument infoPDF = new PDDocument()){
            PDPage page = new PDPage();
            infoPDF.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(infoPDF,page);
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(50, 750);

            for (Field field : Computer.class.getDeclaredFields()){
                field.setAccessible(true);

                String fieldName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.showText(stringToSave(fieldName) + ":");
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText(String.valueOf(field.get(computer)));
                contentStream.newLine();
                contentStream.newLine();

                field.setAccessible(false);
            }

            contentStream.endText();
            contentStream.close();

            ByteArrayOutputStream result = new ByteArrayOutputStream();
            infoPDF.save(result);

            return result.toByteArray();
        } catch (Exception e){
            throw new ComputerPDFInfoWasntCreatedException();
        }
    }

    private String stringToSave(String string){
        StringBuilder stringBuilder = new StringBuilder();

        for (char c : string.toCharArray()){
            if (c == string.charAt(0)) {
                stringBuilder.append(c);
            } else if (Character.isUpperCase(c)){
                stringBuilder.append(' ');
                stringBuilder.append(Character.toLowerCase(c));
            } else {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
    }

    private List<Computer> view(List<Computer> list){
        list.forEach(computer -> {
            computer.setName(stringUtilsService.lowerExceptFirst(computer.getName()));
            computer.setComputerCaseModel(stringUtilsService.lowerExceptFirst(computer.getComputerCaseModel()));
        });
        return list;
    }

    private boolean isCalculatedIdTaken(int calcId, Computer currentComputer) {
        List<Computer> computers = getAll();
        return computers.stream()
                .anyMatch(computer -> !computer.equals(currentComputer) && computer.getCalcId() == calcId);
    }

    private void saveComputer (Computer computer) {
        for (Field field : computer.getClass().getDeclaredFields()) {
            if (field.getType().equals(String.class)) {
                try {
                    field.setAccessible(true);

                    String fieldNewValue = this.stringUtilsService.upper(String.valueOf(field.get(computer)));
                    computerSetValueByFieldName(field.getName(), fieldNewValue, computer);

                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Unexpected IllegalAccessException",e);
                }
            }
        }

        computer.setCalcId();

        if (isCalculatedIdTaken(computer.getCalcId(), computer)){
            throw new ComputerTakenCalculatedIdException();
        } else {
        }
    }

    private <T> void computerSetValueByFieldName(String fieldName, T value, Computer computer) {
         Field field = Arrays.stream(Computer.class.getDeclaredFields())
                .filter(f -> f.getName().equals(fieldName))
                .filter(f -> f.getType().equals(value.getClass()))
                .findFirst()
                 .orElseThrow(ComputerFieldDoesntExistsException::new);
        try {
            field.setAccessible(true);
            field.set(computer, value);
            field.setAccessible(false);
        } catch (Exception e){
            throw new RuntimeException("Unexpected exception",e);
        }
    }
}
