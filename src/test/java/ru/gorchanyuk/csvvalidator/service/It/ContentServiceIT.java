package ru.gorchanyuk.csvvalidator.service.It;

import ru.gorchanyuk.csvvalidator.model.Content;
import ru.gorchanyuk.csvvalidator.service.ContentService;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.SneakyThrows;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ContentServiceIT {

    @Autowired
    private ContentService contentService;

    @Test
    public void getRegistriesShouldReturnEmptyList() throws IOException {
        String fileName = "test.csv";
        List<Content> expectedRegistries = new ArrayList<>();
        File file = File.createTempFile(fileName, null);//Создание пустого файла

        //Должен быть возвращен пустой список, так как файл пуст
        List<Content> actualRegistries = contentService.getRegistries(file.getAbsolutePath());

        assertEquals(expectedRegistries, actualRegistries);
        boolean success = file.delete();
        assertTrue(success);
    }

    @Test
    public void getRegistriesShouldReturnNotEmptyList() throws IOException, URISyntaxException {
        URL resourceUrl = getClass().getResource("/static/train_07.12.2023_valid.csv");
        File file = new File(Objects.requireNonNull(resourceUrl).toURI());//инициализация файла

        List<Content> actualRegistries = contentService.getRegistries(file.getAbsolutePath());

        assertEquals(2, actualRegistries.size());
    }

    @Test
    public void getRegistriesShouldThrowIOException() {
        String nonExistentFile = RandomString.make();

        Assertions.assertThrows(IOException.class,
                () -> contentService.getRegistries(nonExistentFile));
    }

    @Test
    @SneakyThrows
    public void getRegistriesShouldThrowInvalidFormatException(){
        URL resourceUrl = getClass().getResource("/static/train_07.12.2023_invalid.csv");
        File file = new File(Objects.requireNonNull(resourceUrl).toURI());//инициализация файла

        Assertions.assertThrows(InvalidFormatException.class,
                () -> contentService.getRegistries(file.getAbsolutePath()));
    }
}
