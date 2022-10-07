package com.volvo.congestion.tax.calculator.repository.csv;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
public class CsvParser {

    public <T> List<T> getBeans(String resourceName, Class<T> clazz) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(getResource(resourceName)),
                StandardCharsets.UTF_8)) {
            HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(clazz);
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(bufferedReader)
                    .withMappingStrategy(strategy)
                    .build();
            return csvToBean.parse();
        } catch (Exception exception) {
            log.error("Unexpected exception occurred while parsing csv file");
            throw new RuntimeException(exception);
        }
    }

    public List<String> getList(String resourceName) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(getResource(resourceName)),
                StandardCharsets.UTF_8); CSVReader reader = new CSVReader(bufferedReader)) {
            return Arrays.asList(reader.readNext());
        } catch (Exception exception) {
            log.error("Unexpected exception occurred while parsing csv file");
            throw new RuntimeException(exception);
        }
    }

    private URI getResource(String resourceName) {
        try {
            return ResourceUtils.getURL("classpath:" + resourceName).toURI();
        } catch (Exception e) {
            log.error("Unexpected exception occurred while getting resource: {}", resourceName);
            throw new RuntimeException(e);
        }
    }
}
