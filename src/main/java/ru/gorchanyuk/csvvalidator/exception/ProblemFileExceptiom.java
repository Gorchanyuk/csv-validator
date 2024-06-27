package ru.gorchanyuk.csvvalidator.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProblemFileExceptiom extends RuntimeException {

    public ProblemFileExceptiom(String msg) {
        super(msg);
    }

}
