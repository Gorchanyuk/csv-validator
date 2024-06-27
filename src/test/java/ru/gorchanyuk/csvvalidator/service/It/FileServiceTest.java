package ru.gorchanyuk.csvvalidator.service.It;

import ru.gorchanyuk.csvvalidator.exception.FileAlreadyExistsRuntimeException;
import ru.gorchanyuk.csvvalidator.props.FolderProperty;
import ru.gorchanyuk.csvvalidator.service.impl.FileServiceImpl;
import ru.gorchanyuk.csvvalidator.validator.ContentValidator;
import ru.gorchanyuk.csvvalidator.validator.FileNameValidator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FileServiceTest {
    @Mock
    private ResourceBundle resourceBundle;
    @Mock
    private FolderProperty folderProperty;
    @Mock
    private ContentValidator contentValidator;
    @Mock
    private FileNameValidator fileNameValidator;
    @InjectMocks
    private FileServiceImpl fileService;

    private MultipartFile file;
    private String tempDir;
    private File resFile;

    @BeforeEach
    @SneakyThrows
    private void init() {
        file = mock(MultipartFile.class);
        String fileName = "train_12.12.2023.csv"; //С этим названием должен быть вызван метод folderProperty.getTrain()
        tempDir = "testDirectory";
        when(file.getOriginalFilename()).thenReturn(fileName);
        when(file.getBytes()).thenReturn(new byte[1024]);
        when(fileNameValidator.validate(anyString())).thenReturn(true);
        when(contentValidator.validate(anyString())).thenReturn(true);
        when(folderProperty.getCar()).thenReturn(tempDir);
        resFile = new File(tempDir + File.separator + fileName);
    }

    @AfterEach
    @SneakyThrows
    private void afterTest(){

        Files.deleteIfExists(resFile.toPath());
        Files.deleteIfExists(Path.of(tempDir));
    }

    @Test
    @SneakyThrows
    public void saveAndValidateFileShouldReturn() {

        fileService.saveAndValidateFile(file);

        assertTrue(resFile.exists());
        verify(fileNameValidator).validate(anyString());
        verify(contentValidator).validate(anyString());
        verify(folderProperty).getCar();

    }

    @Test
    @SneakyThrows
    public void saveAndValidateFileShouldThrowFileAlreadyExistsRuntimeException() {
        //Создаем файл после чего пытакмся провалидировать и записать файл с таким же именем,
        // должна вернуться ошибка FileAlreadyExistsRuntimeException

        Files.createDirectories(Path.of(tempDir));
        Files.createFile(resFile.toPath());

        assertThrows(FileAlreadyExistsRuntimeException.class,
                () -> fileService.saveAndValidateFile(file));
    }
}
