package com.salat;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:virtuoso://localhost:1111";

        VirtGraph set = new VirtGraph(url, "dba", "1");

        Query sparql = QueryFactory.create("SELECT * FROM <urn:graph:suvorov> WHERE {?s ?p ?o}");

        try (VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, set)) {
            ResultSet results = vqe.execSelect();
            while (results.hasNext()) {
                QuerySolution result = results.nextSolution();
                RDFNode s = result.get("s");
                RDFNode p = result.get("p");
                RDFNode o = result.get("o");
                System.out.printf("{%s %s %s}%n", s.toString(), p.toString(), o.toString());
            }
        }
    }
}