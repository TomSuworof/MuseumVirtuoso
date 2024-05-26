package com.salat.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salat.config.Configs;
import virtuoso.jena.driver.VirtGraph;

public class RmlStreamerGenerator {
    private static VirtGraph getGraph() {
        return new VirtGraph(Configs.getUrl(), Configs.getUsername(), Configs.getPassword());
    }

    public static void main(String[] args) throws JsonProcessingException {
        TicketList tickets = new TicketList(CSVParser.getTicketsFromCSVFile("data.csv"));

        System.out.println("Ticket list object: " + tickets);

        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Ticket list JSON: " + mapper.writeValueAsString(tickets));
    }
}
