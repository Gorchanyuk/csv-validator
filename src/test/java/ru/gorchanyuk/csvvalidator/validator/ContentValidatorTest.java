package ru.gorchanyuk.csvvalidator.validator;

import ru.gorchanyuk.csvvalidator.exception.FileContentInvalidException;
import ru.gorchanyuk.csvvalidator.exception.InvalidContentFormatException;
import ru.gorchanyuk.csvvalidator.model.Content;
import ru.gorchanyuk.csvvalidator.service.ContentService;
import ru.gorchanyuk.csvvalidator.util.ContentCreator;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ContentValidatorTest {

    @Autowired
    private ContentCreator contentCreator;
    @Mock
    private Validator validator;
    @Spy
    private ContentService contentService;
    @Mock
    private ResourceBundle resourceBundle;
    @InjectMocks
    private ContentValidator contentValidator;

    @Test
    @SneakyThrows
    public void validateShouldReturnTrue() {
        Content content = contentCreator.createValidRegistry();
        List<Content> contentList = List.of(content);
        when(contentService.getRegistries(anyString())).thenReturn(contentList);

        boolean result = contentValidator.validate(anyString());

        assertTrue(result);
    }

    @Test
    @SneakyThrows
    public void validateShouldThrowInvalidContentFormatException(){

        when(contentService.getRegistries(anyString())).thenThrow(InvalidFormatException.class);

        assertThrows(InvalidContentFormatException.class,
                ()->contentValidator.validate(anyString()));
    }

    @Test
    @SneakyThrows
    public void validateShouldThrowFileContentInvalidException_1(){

        when(contentService.getRegistries(anyString())).thenThrow(IOException.class);

        assertThrows(FileContentInvalidException.class,
                ()->contentValidator.validate(anyString()));
    }

    @Test
    @SneakyThrows
    public void validateShouldThrowFileContentInvalidException_2(){

        Content content = contentCreator.createRegistryWithInvalidSnils();
        List<Content> contentList = List.of(content);
        when(contentService.getRegistries(anyString())).thenReturn(contentList);
        ConstraintViolation<Content> violation = mock(ConstraintViolation.class);
        when(violation.getInvalidValue()).thenReturn("поле");
        when(violation.getMessage()).thenReturn("сообщение об ошибке");
        when(validator.validate(content)).thenReturn(Set.of(violation));

        assertThrows(FileContentInvalidException.class,
                ()->contentValidator.validate(anyString()));
    }


}
