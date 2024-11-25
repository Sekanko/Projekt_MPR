package pl.edu.pjatk.Projekt_MPR.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.Projekt_MPR.exception.*;
import pl.edu.pjatk.Projekt_MPR.model.Computer;
import pl.edu.pjatk.Projekt_MPR.repository.ComputerRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Service
public class ComputerService {

    private ComputerRepository computerRepository;
    private StringUtilsService stringUtilsService;

    public ComputerService(ComputerRepository computerRepository, StringUtilsService stringUtilsService) {
        this.computerRepository = computerRepository;
        this.stringUtilsService = stringUtilsService;

        computerRepository.save(new Computer("IDK", "TITANIC"));
        computerRepository.save(new Computer("IDK","NIERRA"));
        computerRepository.save(new Computer("KOMP", "LATUN"));
    }

    public List<Computer> getAll() {
        return view((List<Computer>) computerRepository.findAll());
    }

    public void createComputer(Computer computer) {
        computer.setName(stringUtilsService.upper(computer.getName()));
        computer.setComputerCaseModel(stringUtilsService.upper(computer.getComputerCaseModel()));
        saveComputer(computer);
    }

    public void deleteComputer(Long id) {
        if (this.computerRepository.existsById(id)) {
            this.computerRepository.deleteById(id);
        } else {
            throw  new ComputerNoFoundException();
        }
    }

    public Computer getComputer(Long id) {
        Optional<Computer> computer = computerRepository.findById(id);

        if (computer.isEmpty()){
            throw new ComputerNoFoundException();
        }

        return computer.map(value -> view(Collections.singletonList(value))
                .getFirst()).get();
    }


    public List<Computer> getComputerByName(String name) {
        List<Computer> computers = computerRepository.findByName(stringUtilsService.upper(name));
        if (computers.isEmpty()){
            throw new ComputerNoFoundException();
        }
        return view(computers);
    }

    public List<Computer> getComputerByComputerCaseModel(String computerCaseModel) {
        List<Computer> computers = computerRepository.findByComputerCaseModel(stringUtilsService.upper(computerCaseModel));
        if (computers.isEmpty()){
            throw new ComputerNoFoundException();
        }
        return view(computers);
    }

    public void patchComputer(Long id, Map<String, Object> patch) {
        Optional<Computer> optionalComputer = computerRepository.findById(id);

        if (optionalComputer.isEmpty()){
            throw new ComputerNoFoundException();
        }

        Computer computer = optionalComputer.get();

        var fieldNames = Arrays.stream(Computer.class
                .getDeclaredFields())
                .filter(field -> !field.getName().equals("id") && !field.getName().equals("calcId"))
                .toList();


        patch.forEach((fieldName, fieldNewValue) -> {
            if (fieldNewValue == "" || fieldNewValue == null){
                throw new ComputerNewFieldValueIsEmptyException();
            }

            if (fieldNames.stream().anyMatch(field -> field.getName().equals(fieldName))){
                computerSetterByFieldName(fieldName, fieldNewValue, computer);
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
                Method getter = Computer.class.getDeclaredMethod("get" + fieldName);

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.showText(stringToSave(fieldName) + ":");
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText(getter.invoke(computer).toString());
                contentStream.newLine();
                contentStream.newLine();

                field.setAccessible(false);
            }

            contentStream.endText();
            contentStream.close();

            ByteArrayOutputStream result = new ByteArrayOutputStream();
            infoPDF.save(result);

            return result.toByteArray();
        } catch (IOException e){
            throw new ComputerPDFInfoWasntCreatedException();
        } catch (Exception e){
            throw new RuntimeException(e);
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
        List<Computer> computers = (ArrayList <Computer>) computerRepository.findAll();
        return computers.stream()
                .anyMatch(computer -> !computer.equals(currentComputer) && computer.getCalcId() == calcId);
    }

    private void saveComputer (Computer computer) {
        Arrays.stream(computer.getClass().getDeclaredFields())
                .filter(field -> field.getType().equals(String.class))
                        .forEach(field -> {
                            try {
                                field.setAccessible(true);
                                computerSetterByFieldName(field.getName(), stringUtilsService.upper(String.valueOf(field.get(computer))), computer);
                                field.setAccessible(false);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        });

        computer.setCalcId();

        if (isCalculatedIdTaken(computer.getCalcId(), computer)){
            throw new ComputerTakenCalculatedIdException();
        } else {
            computerRepository.save(computer);
        }
    }

    private <T> void computerSetterByFieldName(String fieldName, T value, Computer computer) {
         Arrays.stream(Computer.class.getDeclaredFields())
                .filter(f -> f.getName().equals(fieldName))
                .filter(f -> f.getType().equals(value.getClass()))
                .findFirst()
                 .orElseThrow(() -> new ComputerFieldDoesntExistsException());
        try {
            String toMethodFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method setter = Computer.class.getDeclaredMethod("set" + toMethodFieldName, value.getClass());
            setter.invoke(computer, value);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
