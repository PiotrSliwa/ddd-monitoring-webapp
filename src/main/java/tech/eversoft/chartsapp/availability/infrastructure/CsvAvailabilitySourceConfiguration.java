package tech.eversoft.chartsapp.availability.infrastructure;

import org.apache.commons.csv.CSVFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.eversoft.chartsapp.availability.application.StringRow;
import tech.eversoft.chartsapp.availability.application.StringTable;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Configuration
public class CsvAvailabilitySourceConfiguration {

    @Value("${csvSourceFile}") private String csvSourceFile;

    @Bean
    public StringTable availabilitySource() throws IOException {
        var reader = new FileReader(csvSourceFile);
        var records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
        var rows = new ArrayList<StringRow>();
        for (var record : records) {
            rows.add(new StringRow(record.toMap()));
        }
        return new StringTable(rows);
    }

}
