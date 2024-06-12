package com.salat.company_model;

import com.salat.utils.CSVParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ExperimentsCSVParser implements CSVParser<Experiment> {
    private static Experiment parseExperiment(CSVRecord record) {
        return new Experiment(
                Integer.parseInt(record.get("runNumber")),
                Integer.parseInt(record.get("totalCustomersNumber")),
                Integer.parseInt(record.get("salary")),
                Integer.parseInt(record.get("productCosts")),
                Integer.parseInt(record.get("startingBalance")),
                Integer.parseInt(record.get("startingRevenue")),
                Float.parseFloat(record.get("resultRevenue")),
                Float.parseFloat(record.get("resultCosts")),
                Float.parseFloat(record.get("resultProductPrice")),
                Integer.parseInt(record.get("ticksPassed"))
        );
    }

    @Override
    public List<Experiment> getEntitiesFromCSVFile(String fileName) {
        try {
            URL resource = ExperimentsCSVParser.class.getResource("/" + fileName);
            if (resource == null) {
                throw new NullPointerException("Cannot load file");
            }

            return CSVFormat.DEFAULT.builder()
                    .setHeader("runNumber", "totalCustomersNumber", "salary", "productCosts", "startingBalance", "startingRevenue", "step", "resultRevenue", "resultCosts", "resultProductPrice", "ticksPassed")
                    .setSkipHeaderRecord(true)
                    .build()
                    .parse(new FileReader(resource.getFile()))
                    .stream()
                    .map(record -> {
                        try {
                            return parseExperiment(record);
                        } catch (Exception e) {
                            e.printStackTrace(System.out);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Collections.emptyList();
        }
    }
}
