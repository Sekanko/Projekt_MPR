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
        computer.setCalcId(computer.calcualteId());

        if (isCalculatedIdTaken(computer.getCalcId())) {
            throw new ComputerTakenCalculatedIdException();
        }

        this.computerRepository.save(computer);
    }

    public void deleteComputer(Long id) {
        if (computerRepository.findById(id).isEmpty()){
            throw new ComputerNoFoundException();
        }

        this.computerRepository.deleteById(id);
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

        patch.forEach((key, value) -> {
            if (value == "" || value == null){
                throw new ComputerNewFieldValueIsEmptyException();
            }
            switch (key) {
                case "name":
                    computer.setName((String) value);
                    break;
                case "computerCaseModel":
                    computer.setComputerCaseModel((String) value);
                    break;
                default:
                    throw new ComputerFieldDoesntExistsException();
            }
        });

        computer.setCalcId(computer.calcualteId());

        if (isCalculatedIdTaken(computer.getCalcId())) {
            throw new ComputerTakenCalculatedIdException();
        }

        computerRepository.save(computer);
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

    private boolean isCalculatedIdTaken(int calcId){
        List<Computer> computers = (ArrayList <Computer>) computerRepository.findAll();
        return !computers.stream()
                .filter(computer -> computer.getCalcId() == calcId)
                .toList()
                .isEmpty();
    }
}
