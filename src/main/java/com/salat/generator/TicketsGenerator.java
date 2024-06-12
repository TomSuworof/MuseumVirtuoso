package com.salat.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.salat.config.Configs;
import com.salat.utils.TransactionWrapper;
import org.apache.jena.graph.GraphUtil;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;
import org.apache.jena.util.iterator.ExtendedIterator;
import virtuoso.jena.driver.VirtGraph;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TicketsGenerator {
    private static VirtGraph getGraph() {
        return new VirtGraph(Configs.getUrl(), Configs.getUsername(), Configs.getPassword());
    }

    private static void printGraphContents(VirtGraph graph) {
        System.out.println("graph.isEmpty() = " + graph.isEmpty());
        System.out.println("graph.getCount() = " + graph.getCount());

        ExtendedIterator<Triple> iterator = graph.find(Node.ANY, Node.ANY, Node.ANY);
        System.out.println("graph.find(Node.ANY, Node.ANY, Node.ANY) Result:");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private static Ticket getBaseTicket() {
        return new Ticket(
                new Visitor(
                        "Tom",
                        Visitor.Type.ADULT,
                        new GregorianCalendar(
                                2002,
                                Calendar.JULY,
                                4
                        ).getTime()),
                0L,
                300
        );
    }

    private static Ticket getTicketFromCommandLine() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter visitor name:");
            String name = reader.readLine();

            System.out.println("Enter visitor type (Adult, child, elderly):");
            Visitor.Type type = Visitor.Type.valueOf(reader.readLine().toUpperCase());

            System.out.println("Enter birthday in format dd.MM.yyyy: ");
            Date birthday = new SimpleDateFormat("dd.MM.yyyy").parse(reader.readLine());

            System.out.println("Enter number: ");
            Long number = Long.parseLong(reader.readLine());

            System.out.println("Enter price: ");
            Integer price = Integer.parseInt(reader.readLine());

            return new Ticket(
                    new Visitor(name, type, birthday),
                    number,
                    price
            );
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        Ticket ticket = getTicketFromCommandLine();

        if (ticket == null) {
            System.out.println("Wrong ticket");
            return;
        }

        VirtGraph graph = getGraph();

        List<Triple> triples = ticket.toTriples();

        try (TransactionWrapper transactionWrapper = new TransactionWrapper(graph)) {
            System.out.println("Add triples to graph");
            GraphUtil.add(graph, triples);
            transactionWrapper.commit();
        }

        printGraphContents(graph);

        try (TransactionWrapper transactionWrapper = new TransactionWrapper(graph)) {
            System.out.println("Remove triples from graph");
            GraphUtil.delete(graph, triples);
            transactionWrapper.commit();
        }

        printGraphContents(graph);
    }
}
