package com.salat.generator;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RmlStreamerFileAdapter {
    public static void main(String[] args) throws IOException {
        System.out.println("Waiting for client to connect...");
        try (
                ServerSocket serverSocket = new ServerSocket(9999); // starts socket globally
                Socket socket = serverSocket.accept(); // connects to socket
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // output to socket
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)) // console input
        ) {
            while (true) {
                System.out.println("Enter file name, located in /src/main/resources");
                String line = reader.readLine();
                if (line.equals("exit")) break;

                TicketList tickets = new TicketList(CSVParser.getTicketsFromCSVFile(line));

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
