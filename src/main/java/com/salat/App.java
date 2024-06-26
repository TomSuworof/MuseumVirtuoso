package com.salat;

import com.salat.config.Configs;
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
        VirtGraph set = new VirtGraph(Configs.getUrl(), Configs.getUsername(), Configs.getPassword());

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