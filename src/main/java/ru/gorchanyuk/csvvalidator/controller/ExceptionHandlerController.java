package ru.gorchanyuk.csvvalidator.controller;

import ru.gorchanyuk.csvvalidator.dto.ErrorResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.gorchanyuk.csvvalidator.exception.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController {

    @ExceptionHandler
    private ResponseEntity<ErrorResponseDTO> handleException(FileNameInvalidException e){
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponseDTO> handleException(InvalidContentFormatException e){
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponseDTO> handleException(FileContentInvalidException e){
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponseDTO> handleException(ProblemFileExceptiom e){
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponseDTO> handleException(StorageSaveFileException e){
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponseDTO> handleException(StorageSaveDirectoryException e){
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponseDTO> handleException(FileAlreadyExistsRuntimeException e){
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.TOO_MANY_REQUESTS);
    }
}
