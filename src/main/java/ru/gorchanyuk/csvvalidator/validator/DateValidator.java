package ru.gorchanyuk.csvvalidator.validator;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class DateValidator {

    public boolean validate(String dateFile) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            LocalDate localDate = LocalDate.parse(dateFile, formatter);
            String dateString = localDate.format(formatter);
            return dateString.equals(dateFile); //что бы гарантировать правильность перобразования, например 30.02.2023
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
