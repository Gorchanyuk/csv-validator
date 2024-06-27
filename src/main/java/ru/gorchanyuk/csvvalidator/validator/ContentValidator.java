package ru.gorchanyuk.csvvalidator.validator;

import ru.gorchanyuk.csvvalidator.exception.FileContentInvalidException;
import ru.gorchanyuk.csvvalidator.exception.InvalidContentFormatException;
import ru.gorchanyuk.csvvalidator.model.Content;
import ru.gorchanyuk.csvvalidator.service.ContentService;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentValidator {

    private final Validator validator;
    private final ContentService service;
    private final ResourceBundle resourceBundle;

    public boolean validate(String fileName) {

        List<Content> elements;
        try {
            elements = service.getRegistries(fileName);
        } catch (InvalidFormatException e) {
            throw new InvalidContentFormatException(resourceBundle.getString("PROBLEM_CONTENT"));
        } catch (IOException e) {
            log.info("Возникла ошибка во время чтения файла: {}. {}", fileName, e.getMessage());
            throw new FileContentInvalidException(resourceBundle.getString("PROBLEM_READ_FILE"));
        }
        for (Content element : elements) {
            Set<ConstraintViolation<Content>> violations = validator.validate(element);
            if (!violations.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                violations.forEach(violation ->
                        sb.append(violation.getInvalidValue())
                                .append(" - ")
                                .append(violation.getMessage())
                                .append(";\n"));
                throw new FileContentInvalidException(sb.toString());
            }
        }
        return true;
    }
}
