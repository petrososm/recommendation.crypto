package com.exercise.recommendation.crypto.utils.csvparser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class CsvReader {

    public <T> List<T> readValues(InputStream inputStream, Class<? extends T> clazz) throws CsvParseException {
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            CsvToBean<T> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(clazz)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<T> objs = csvToBean.parse();
            return objs;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CsvParseException("Unable to parse csv file ", ex);
        }
    }
}
