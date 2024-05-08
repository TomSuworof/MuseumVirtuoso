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

public class Query6 {
    public static void main(String[] args) {
        VirtGraph set = new VirtGraph(Configs.getUrl(), Configs.getUsername(), Configs.getPassword());

        Query sparql = QueryFactory.create("""
                Prefix owl:	    <http://www.w3.org/2002/07/owl#>
                Prefix rdf:	    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                Prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#>
                Prefix ent:	    <http://www.menthor.net/myontology#>
                Prefix xsd:     <http://www.w3.org/2001/XMLSchema#>
                \s
                select ?ind ?type ?bd (strdt(concat(substr(?bd, 7, 4), "-" , substr(?bd, 1, 2), "-", substr(?bd, 0, 2)), xsd:date) as ?bd_iso)
                FROM <urn:graph:suvorov>
                where
                {
                ?ind rdf:type ?type .
                ?type rdfs:subClassOf ent:Museum_employee .
                ?ind ent:birthday ?bd
                }
                order by ASC(?bd_iso)
                """);

        try (VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, set)) {
            ResultSet results = vqe.execSelect();
            while (results.hasNext()) {
                QuerySolution result = results.nextSolution();
                RDFNode ind = result.get("ind");
                RDFNode type = result.get("type");
                RDFNode bd = result.get("bd");
                RDFNode bdIso = result.get("bd_iso");
                System.out.printf("{%s %s %s %s}%n", ind.toString(), type.toString(), bd.toString(), bdIso.toString());
            }
        }
    }
}
