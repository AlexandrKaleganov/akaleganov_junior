package ru.job4j.architecture;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidateServiceTest {

    @Test
    public void findById() {
        Validate validate = ValidateService.getInstance();

       String valida = "kdsjAfjkl1";
        System.out.println(valida.matches("[a-z-A-Z]{1,10}"));
        System.out.println(valida.matches("[\\w]{1,10}"));
    }
}