package com.salat.generator;

import com.salat.utils.CSVParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TicketsCSVParser implements CSVParser<Ticket> {
    private static Ticket parseTicket(CSVRecord record) throws ParseException {
        return new Ticket(
                new Visitor(
                        record.get("visitor_name"),
                        Visitor.Type.valueOf(record.get("visitor_type")),
                        new SimpleDateFormat("dd.MM.yyyy").parse(record.get("visitor_birthday"))
                ),
                Long.parseLong(record.get("number")),
                Integer.parseInt(record.get("price"))
        );
    }

    @Override
    public List<Ticket> getEntitiesFromCSVFile(String fileName) {
        try {
            URL resource = TicketsCSVParser.class.getResource("/" + fileName);
            if (resource == null) {
                throw new NullPointerException("Cannot load file");
            }

            return CSVFormat.DEFAULT.builder()
                    .setHeader("number", "price", "visitor_name", "visitor_type", "visitor_birthday")
                    .setSkipHeaderRecord(true)
                    .build()
                    .parse(new FileReader(resource.getFile()))
                    .stream()
                    .map(record -> {
                        try {
                            return parseTicket(record);
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
