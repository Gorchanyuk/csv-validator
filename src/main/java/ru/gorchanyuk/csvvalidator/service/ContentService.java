package ru.gorchanyuk.csvvalidator.service;

import ru.gorchanyuk.csvvalidator.model.Content;

import java.io.IOException;
import java.util.List;

public interface ContentService {

    List<Content> getRegistries(String fileName) throws IOException;
}
