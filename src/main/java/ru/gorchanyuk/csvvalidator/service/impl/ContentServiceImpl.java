package ru.gorchanyuk.csvvalidator.service.impl;

import ru.gorchanyuk.csvvalidator.model.Content;
import ru.gorchanyuk.csvvalidator.service.ContentService;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    @Override
    public List<Content> getRegistries(String fileName) throws IOException {

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper
                .schemaFor(Content.class)
                .withColumnSeparator(';')
                .withSkipFirstDataRow(true);

        try (Reader myReader = new FileReader(fileName);
             MappingIterator<Content> iterator = mapper
                     .readerFor(Content.class)
                     .with(schema)
                     .readValues(myReader)) {

            return iterator.readAll();
        }
    }


}
