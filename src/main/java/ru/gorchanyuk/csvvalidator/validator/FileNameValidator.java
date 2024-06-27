package ru.gorchanyuk.csvvalidator.validator;

import ru.gorchanyuk.csvvalidator.exception.FileNameInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class FileNameValidator {

    private final DateValidator dateValidator;
    private final ResourceBundle resourceBundle;

    public boolean validate(String fileName) {
        Pattern pattern = Pattern.compile("(?i)^(car_|train_)(?<date>.*?)\\.csv$");
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            String dateFile = matcher.group("date");
            boolean isValid = dateValidator.validate(dateFile);
            if (isValid) {
                return true;
            }
        }
        throw new FileNameInvalidException(resourceBundle.getString("NAME_FILE_INVALID"));
    }
}
