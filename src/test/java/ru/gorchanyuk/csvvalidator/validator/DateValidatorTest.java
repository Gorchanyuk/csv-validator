package ru.gorchanyuk.csvvalidator.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DateValidatorTest {

    @Autowired
    private DateValidator dateValidator;

    @Test
    public void validateShouldReturnTrue(){
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = date.format(formatter);

        boolean result = dateValidator.validate(formattedDate);

        assertTrue(result);
    }

    @Test
    public void validateShouldReturnFalse(){
        List<String> dates = new ArrayList<>();
        dates.add("5.12.2023");     //Число представлено одной цифрой
        dates.add("07.13.2012");    //Невозможное значение месяца
        dates.add("31.01.2o10");    //Буквы в строке
        dates.add("30.02.2021");    //Невозможная дата

        dates.stream()
                .map(date -> dateValidator.validate(date))
                .forEach(Assertions::assertFalse);
    }
}
