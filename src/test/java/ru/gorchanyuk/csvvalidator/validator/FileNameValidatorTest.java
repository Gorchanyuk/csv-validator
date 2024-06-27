package ru.gorchanyuk.csvvalidator.validator;

import ru.gorchanyuk.csvvalidator.exception.FileNameInvalidException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FileNameValidatorTest {

    @Autowired
    private FileNameValidator fileNameValidator;

    @ParameterizedTest
    @ValueSource(strings = {
            "car_25.12.2023.csv",
            "train_07.05.2012.csv",
            "TRain_31.01.2010.CSV",
            "tRaIn_23.10.2021.cSv"})
    public void validateShouldBeSuccessful(String fileName) {

        boolean result = fileNameValidator.validate(fileName);

        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "q_25.12.2023.csv",
            "r_07.05.2012.csv",
            "t_31.01.2010.doc",
            "t_29.02.2021.csv"})
    public void validateShouldByFalse(String fileName) {

        assertThrows(FileNameInvalidException.class,
                () -> fileNameValidator.validate(fileName));
    }
}
