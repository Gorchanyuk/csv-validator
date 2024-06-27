package ru.gorchanyuk.csvvalidator.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidContentFormatException extends RuntimeException{

    public InvalidContentFormatException(String msg){
        super(msg);
    }
}
