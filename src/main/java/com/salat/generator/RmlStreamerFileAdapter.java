package com.salat.generator;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

public class RmlStreamerFileAdapter {
    public static void main(String[] args) throws IOException {
        System.out.println("Waiting for RML-Streamer to connect...");
        try (
                ServerSocket inputSocket = new ServerSocket(9999); // start socket that will server as input for RML-Streamer
                PrintWriter out = new PrintWriter(inputSocket.accept().getOutputStream(), true); // output to socket
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            while (true) {
                System.out.println("Enter file name, located in /src/main/resources. Or enter 'exit' to exit");
                String line = consoleReader.readLine();
                if (line.equals("exit")) break;

                TicketList tickets = new TicketList(new TicketsCSVParser().getEntitiesFromCSVFile(line));

                System.out.println("Ticket list object: " + tickets);

                String ticketsJson = new ObjectMapper().writeValueAsString(tickets);
                System.out.println("Ticket list JSON: " + ticketsJson);

                out.println(ticketsJson);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
