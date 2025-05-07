package pl.edu.pjatk.Projekt_MPR.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.edu.pjatk.Projekt_MPR.model.Computer;
import pl.edu.pjatk.Projekt_MPR.repository.ComputerRepository;

@Configuration
@Profile("test")
public class TestDataInitializer {
    @Bean
    public CommandLineRunner initData(ComputerRepository repository) {
        return args -> {
            repository.save(new Computer("First Test Computer", "Test Case Model"));
            repository.save(new Computer("Second Test Computer", "Test Case Model"));
        };
    }
}