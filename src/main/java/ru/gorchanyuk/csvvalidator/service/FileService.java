package ru.gorchanyuk.csvvalidator.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void saveAndValidateFile(MultipartFile file);

}
