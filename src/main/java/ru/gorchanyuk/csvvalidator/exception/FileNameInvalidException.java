package ru.gorchanyuk.csvvalidator.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileNameInvalidException extends RuntimeException{

    public FileNameInvalidException(String msg){
        super(msg);
    }

}
