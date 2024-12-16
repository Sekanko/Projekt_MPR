package pl.edu.pjatk.Projekt_MPR.service;

import org.springframework.stereotype.Service;

@Service
public class StringUtilsService {
    public String upper(String name){
        return name.toUpperCase();
    }

    public String lowerExceptFirst(String name){
        return name.charAt(0) + name.substring(1).toLowerCase();
    }
}
