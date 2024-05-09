package com.salat.virtuoso_examples;

import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import virtuoso.jena.driver.*;

import java.util.Iterator;

public class VirtuosoSPARQLExample9 {
    /**
     * Executes a SPARQL query against a virtuoso url and prints results.
     */
    public static void main(String[] args) {
        String url;
        if (args.length == 0) {
            url = "jdbc:virtuoso://localhost:1111";
        } else {
            url = args[0];
        }

        /*			STEP 1			*/
        VirtGraph set = new VirtGraph(url, "dba", "1");

        /*			STEP 2			*/
        String str = "CLEAR GRAPH <http://test1>";
        VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str, set);
        vur.exec();

        str = "INSERT INTO GRAPH <http://test1> { <http://aa> <http://bb> 'cc' . <http://aa1> <http://bb> 123. }";
        vur = VirtuosoUpdateFactory.create(str, set);
        vur.exec();


        /*		Select all data in virtuoso	*/
        Query sparql = QueryFactory.create("SELECT * FROM <http://test1> WHERE { ?s ?p ?o }");
        try (VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, set)) {
            ResultSet results = vqe.execSelect();
            System.out.println("\nSELECT results:");
            while (results.hasNext()) {
                QuerySolution rs = results.nextSolution();
                RDFNode s = rs.get("s");
                RDFNode p = rs.get("p");
                RDFNode o = rs.get("o");
                System.out.println(" { " + s + " " + p + " " + o + " . }");
            }
        }

        sparql = QueryFactory.create("DESCRIBE <http://aa> FROM <http://test1>");
        try (VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, set)) {
            Model model = vqe.execDescribe();
            Graph g = model.getGraph();
            System.out.println("\nDESCRIBE results:");
            for (Iterator<Triple> i = g.find(Node.ANY, Node.ANY, Node.ANY); i.hasNext(); ) {
                Triple t = i.next();
                System.out.println(" { " + t.getSubject() + " " +
                        t.getPredicate() + " " +
                        t.getObject() + " . }");
            }
        }

        sparql = QueryFactory.create("CONSTRUCT { ?x <http://test> ?y } FROM <http://test1> WHERE { ?x <http://bb> ?y }");
        try (VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, set)) {
            Model model = vqe.execConstruct();
            Graph g = model.getGraph();
            System.out.println("\nCONSTRUCT results:");
            for (Iterator<Triple> i = g.find(Node.ANY, Node.ANY, Node.ANY); i.hasNext(); ) {
                Triple t = i.next();
                System.out.println(" { " + t.getSubject() + " " +
                        t.getPredicate() + " " +
                        t.getObject() + " . }");
            }
        }

        sparql = QueryFactory.create("ASK FROM <http://test1> WHERE { <http://aa> <http://bb> ?y }");
        try (VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, set)) {
            boolean res = vqe.execAsk();
            System.out.println("\nASK results: " + res);
        }
    }
}
