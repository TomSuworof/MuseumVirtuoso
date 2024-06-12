package com.salat.company_model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.salat.config.Configs;
import com.salat.utils.TransactionWrapper;
import org.apache.jena.graph.GraphUtil;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;
import org.apache.jena.util.iterator.ExtendedIterator;
import virtuoso.jena.driver.VirtGraph;

import java.util.List;

public class ExperimentsGenerator {
    private static VirtGraph getGraph() {
        return new VirtGraph(
                "urn:graph:companyexperiments",
                Configs.getUrl(),
                Configs.getUsername(),
                Configs.getPassword()
        );
    }

    private static void printGraphContents(VirtGraph graph) {
        System.out.println("graph.isEmpty() = " + graph.isEmpty());
        System.out.println("graph.getCount() = " + graph.getCount());

        ExtendedIterator<Triple> iterator = graph.find(Node.ANY, Node.ANY, Node.ANY);
        System.out.println("graph.find(Node.ANY, Node.ANY, Node.ANY) Result:");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("graph.getGraphName() = " + graph.getGraphName());
    }

    public static void main(String[] args) throws JsonProcessingException {
        VirtGraph graph = getGraph();

        List<Triple> triples = new ExperimentsCSVParser()
                .getEntitiesFromCSVFile("company_extended_experiment_table_normalized.csv")
                .stream()
                .map(Experiment::toTriples)
                .flatMap(List::stream)
                .toList();

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
