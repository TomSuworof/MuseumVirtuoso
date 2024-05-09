package com.salat.virtuoso_examples;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.*;
import org.apache.jena.testing_framework.NodeCreateUtils;
import org.apache.jena.vocabulary.RDFS;
import virtuoso.jena.driver.VirtInfGraph;
import virtuoso.jena.driver.VirtModel;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import java.util.StringTokenizer;

public class VirtuosoSPARQLExample13 {
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

        /* LOADING data to http://exmpl13 graph  */

        VirtModel modelData = VirtModel.openDatabaseModel("http://exmpl13", url, "dba", "1");
        modelData.removeAll();

        Statement st;

        st = statement(modelData, "http://localhost:8890/dataspace http://www.w3.org/1999/02/22-rdf-syntax-ns#type http://rdfs.org/sioc/ns#Space");
        modelData.add(st);

        st = statement(modelData, "http://localhost:8890/dataspace http://rdfs.org/sioc/ns#link http://localhost:8890/ods");
        modelData.add(st);

        st = statement(modelData, "http://localhost:8890/dataspace/test2/weblog/test2tWeblog http://www.w3.org/1999/02/22-rdf-syntax-ns#type http://rdfs.org/sioc/types#Weblog");
        modelData.add(st);
        st = statement(modelData, "http://localhost:8890/dataspace/test2/weblog/test2tWeblog http://rdfs.org/sioc/ns#link http://localhost:8890/dataspace/test2/weblog/test2tWeblog");
        modelData.add(st);

        st = statement(modelData, "http://localhost:8890/dataspace/discussion/oWiki-test1Wiki http://www.w3.org/1999/02/22-rdf-syntax-ns#type http://rdfs.org/sioc/types#MessageBoard");
        modelData.add(st);
        st = statement(modelData, "http://localhost:8890/dataspace/discussion/oWiki-test1Wiki http://rdfs.org/sioc/ns#link http://localhost:8890/dataspace/discussion/oWiki-test1Wiki");
        modelData.add(st);

        // Query string.
        String queryString = "SELECT * WHERE {?s ?p ?o}";
        System.out.println("Execute query=\n" + queryString);
        System.out.println();

        try (QueryExecution execution = VirtuosoQueryExecutionFactory.create(queryString, modelData)) {
            ResultSet rs = execution.execSelect();
            while (rs.hasNext()) {
                QuerySolution result = rs.nextSolution();
                RDFNode s = result.get("s");
                RDFNode p = result.get("p");
                RDFNode o = result.get("o");
                System.out.println(" { " + s + " " + p + " " + o + " . }");
            }
        }

        modelData.removeRuleSet("exmpl13_rules", "http://:exmpl13_schema");

        /* LOADING rule to http://exmpl13_schema graph  */

        VirtModel modelRule = VirtModel.openDatabaseModel("http://exmpl13_schema", url, "dba", "1");
        modelRule.removeAll();

        Resource r1 = modelRule.createResource("http://rdfs.org/sioc/ns#Space");
        r1.addProperty(RDFS.subClassOf, rdfNode(modelRule, "http://www.w3.org/2000/01/rdf-schema#Resource"));

        r1 = modelRule.createResource("http://rdfs.org/sioc/ns#Container");
        r1.addProperty(RDFS.subClassOf, rdfNode(modelRule, "http://rdfs.org/sioc/ns#Space"));

        r1 = modelRule.createResource("http://rdfs.org/sioc/ns#Forum");
        r1.addProperty(RDFS.subClassOf, rdfNode(modelRule, "http://rdfs.org/sioc/ns#Container"));

        r1 = modelRule.createResource("http://rdfs.org/sioc/types#Weblog");
        r1.addProperty(RDFS.subClassOf, rdfNode(modelRule, "http://rdfs.org/sioc/ns#Forum"));

        r1 = modelRule.createResource("http://rdfs.org/sioc/types#MessageBoard");
        r1.addProperty(RDFS.subClassOf, rdfNode(modelRule, "http://rdfs.org/sioc/ns#Forum"));

        r1 = modelRule.createResource("http://rdfs.org/sioc/ns#link");
        r1.addProperty(RDFS.subPropertyOf, rdfNode(modelRule, "http://rdfs.org/sioc/ns"));

        modelRule.close();

        modelData.createRuleSet("exmpl13_rules", "http://exmpl13_schema");
        modelData.close();

        VirtInfGraph infGraph = new VirtInfGraph("exmpl13_rules", false, "http://exmpl13", url, "dba", "1");
        InfModel model = ModelFactory.createInfModel(infGraph);

        queryString = "SELECT ?s " +
                "FROM <http://exmpl13> " +
                "WHERE {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>  <http://rdfs.org/sioc/ns#Space> } ";
        System.out.println("\n\nExecute query=\n" + queryString);
        System.out.println();

        try (QueryExecution execution = VirtuosoQueryExecutionFactory.create(queryString, model)) {
            ResultSet rs = execution.execSelect();
            while (rs.hasNext()) {
                QuerySolution result = rs.nextSolution();
                RDFNode s = result.get("s");
                System.out.println(" " + s);
            }
        }


        queryString = "SELECT * " +
                "FROM <http://exmpl13> " +
                "WHERE " +
                "{ " +
                " ?s ?p <http://rdfs.org/sioc/ns#Space> . " +
                " ?s ?p1 <http://localhost:8890/dataspace/test2/weblog/test2tWeblog> . " +
                "} ";

        System.out.println("\n\nExecute query=\n" + queryString);
        System.out.println();

        try (QueryExecution execution = VirtuosoQueryExecutionFactory.create(queryString, model)) {
            ResultSet rs = execution.execSelect();
            while (rs.hasNext()) {
                QuerySolution result = rs.nextSolution();
                RDFNode s = result.get("s");
                RDFNode p = result.get("p");
                RDFNode p1 = result.get("p1");
                System.out.println(" " + s + " " + p + " " + p1);
            }
        }

        model.close();
    }

    public static Statement statement(Model model, String fact) {
        StringTokenizer st = new StringTokenizer(fact);
        Resource subject = resource(model, st.nextToken());
        Property predicate = property(model, st.nextToken());
        RDFNode object = rdfNode(model, st.nextToken());
        return model.createStatement(subject, predicate, object);
    }

    public static Resource resource(Model model, String s) {
        return (Resource) rdfNode(model, s);
    }

    public static Property property(Model model, String s) {
        return rdfNode(model, s).as(Property.class);
    }

    public static RDFNode rdfNode(Model model, String s) {
        return model.asRDFNode(NodeCreateUtils.create(model, s));
    }
}