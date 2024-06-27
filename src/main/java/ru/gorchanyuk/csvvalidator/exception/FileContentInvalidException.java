package ru.gorchanyuk.csvvalidator.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileContentInvalidException extends RuntimeException{

    public FileContentInvalidException(String msg){
        super(msg);
    }
}
