package com.salat.queries;

import com.salat.config.Configs;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

public class Query3 {
    public static void main(String[] args) {
        VirtGraph set = new VirtGraph(Configs.getUrl(), Configs.getUsername(), Configs.getPassword());

        Query sparql = QueryFactory.create("""
                Prefix owl:	    <http://www.w3.org/2002/07/owl#>
                Prefix rdf:	    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                Prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#>
                Prefix ent:	    <http://www.menthor.net/myontology#>
                \s
                SELECT ?ticketName ?type ?price
                FROM <urn:graph:suvorov>
                WHERE {
                    ?ticketName rdf:type ?type .
                    ?type rdfs:subClassOf ent:Ticket .
                    ?ticketName ent:price ?price FILTER (?price > 100)
                }
                """);

        try (VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, set)) {
            ResultSet results = vqe.execSelect();
            while (results.hasNext()) {
                QuerySolution result = results.nextSolution();
                RDFNode ticketName = result.get("ticketName");
                RDFNode type = result.get("type");
                RDFNode price = result.get("price");
                System.out.printf("{%s %s %s}%n", ticketName.toString(), type.toString(), price.toString());
            }
        }
    }
}
