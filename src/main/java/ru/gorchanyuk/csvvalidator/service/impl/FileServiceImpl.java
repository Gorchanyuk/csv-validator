package ru.gorchanyuk.csvvalidator.service.impl;

import ru.gorchanyuk.csvvalidator.exception.FileAlreadyExistsRuntimeException;
import ru.gorchanyuk.csvvalidator.exception.ProblemFileExceptiom;
import ru.gorchanyuk.csvvalidator.exception.StorageSaveDirectoryException;
import ru.gorchanyuk.csvvalidator.exception.StorageSaveFileException;
import ru.gorchanyuk.csvvalidator.props.FolderProperty;
import ru.gorchanyuk.csvvalidator.service.FileService;
import ru.gorchanyuk.csvvalidator.validator.ContentValidator;
import ru.gorchanyuk.csvvalidator.validator.FileNameValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final ResourceBundle resourceBundle;
    private final FolderProperty folderProperty;
    private final ContentValidator contentValidator;
    private final FileNameValidator fileNameValidator;

    @Override
    public void saveAndValidateFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String tempFile = saveFileInTempDir(file, fileName);
        try {
            boolean nameIsValid = fileNameValidator.validate(fileName);
            boolean contentIsValid = contentValidator.validate(tempFile);
            if (!nameIsValid || !contentIsValid) {
                log.error("Невозможное условие валидации файла: {}. Необходимо проверить работу валидаторов.", fileName);
                throw new ProblemFileExceptiom(resourceBundle.getString("IMPOSSIBLE_PROBLEM"));
            }
            saveFile(tempFile, fileName);
        } finally {
            deleteTempFile(tempFile);
        }
    }

    private void deleteTempFile(String tempFile) {
        File file = new File(tempFile);
        boolean isDelete = file.delete();
        if (isDelete) {
            log.debug("Файл {} удален.", tempFile);
        }
    }

    private void saveFile(String tempFile, String fileName) {
        String dir = getDirectory(fileName);
        checkAndCreateDirectory(dir);
        try {
            Path destinationPath = Paths.get(dir, fileName);
            Files.move(Path.of(tempFile), destinationPath);
            log.info("Файл: {} успешно сохранен.", fileName);
        } catch (FileAlreadyExistsException e) {
            throw new FileAlreadyExistsRuntimeException(resourceBundle.getString("FILE_ALREADY_EXIST"));
        } catch (IOException e) {
            throw new StorageSaveFileException(resourceBundle.getString("SAVE_FILE_EXCEPTION") + " " + fileName);
        }
    }

    private String getDirectory(String fileName) {
        if (fileName.toLowerCase().startsWith("train_")) {
            return folderProperty.getCar();
        }
        return folderProperty.getTrain();
    }

    private void checkAndCreateDirectory(String dir) {
        try {
            Path directory = Path.of(dir);
            if (Files.notExists(directory)) {
                Files.createDirectories(directory);
                log.info("Директория {} успешно создана", dir);
            }
        } catch (IOException e) {
            throw new StorageSaveDirectoryException(resourceBundle.getString("SAVE_DIRECTORY_EXCEPTION") + " " + dir);
        }
    }

    private String saveFileInTempDir(MultipartFile file, String fileName) {

        Path tempPath;
        try {
            tempPath = Files.createTempFile(null, fileName);
        } catch (IOException e) {
            log.error("Ошибка при создании файла: {} во временной дирректории", fileName);
            throw new StorageSaveFileException(resourceBundle.getString("SAVE_TEMP_FILE_EXCEPTION"));
        }
        try {
            Files.write(tempPath, file.getBytes());
        } catch (IOException e) {
            log.error("Ошибка при записи файла: {}", fileName);
            throw new ProblemFileExceptiom(resourceBundle.getString("PROBLEM_WRITE_FILE"));
        }
        log.info("Файл {} сохранен во временную дирректорию", fileName);
        return tempPath.toString();
    }
}
