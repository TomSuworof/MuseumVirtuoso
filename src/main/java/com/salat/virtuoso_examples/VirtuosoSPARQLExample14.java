package com.salat.virtuoso_examples;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.util.PrintUtil;
import org.apache.jena.vocabulary.RDFS;
import virtuoso.jena.driver.VirtModel;

public class VirtuosoSPARQLExample14 {
    private static String URL = "jdbc:virtuoso://localhost:1111";
    private static final String uid = "dba";
    private static final String pwd = "1";

    public static void print_model(String header, Model m) {
        String h = header == null ? "Model" : header;
        System.out.println("===========[" + h + "]==========");
        StmtIterator it = m.listStatements(null, null, (RDFNode) null);
        while (it.hasNext()) {
            Statement st = it.nextStatement();
            System.out.println(st);
        }
        System.out.println("============================\n");
    }

    public static void print_model(String header, StmtIterator it) {
        String h = header == null ? "Model iterator" : header;
        System.out.println("===========[" + h + "]==========");
        while (it.hasNext()) {
            Statement st = it.nextStatement();
            System.out.println(st);
        }
        System.out.println("============================\n");
    }

    public static void exec_select(String header, Model m, String query) {
        String h = header == null ? "" : header;
        System.out.println("===========[" + h + "]==========");
        System.out.println("Exec: " + query);
        Query jquery = QueryFactory.create(query);
        try (QueryExecution execution = QueryExecutionFactory.create(jquery, m)) {
            ResultSet results = execution.execSelect();
            ResultSetFormatter.out(System.out, results, jquery);
            execution.close();
            System.out.println("============================\n");
        }
    }

    public static void main(String[] args) {
        if (args.length != 0) {
            URL = args[0];
        }

        try {
            test1();
            test2();
            test3();
            test4();
        } catch (Exception e) {
            System.out.println("ERROR Test Failed.");
            e.printStackTrace(System.out);
        }
    }

    public static void test1() {
        try {
            System.out.println("--------------- TEST 1 -------------------");
            VirtModel virtData = VirtModel.openDatabaseModel("test:inf1", URL, uid, pwd);
            virtData.removeAll();

            String NS = PrintUtil.egNS;
            Resource c1 = virtData.createResource(NS + "C1");
            Resource c2 = virtData.createResource(NS + "C2");
            Resource c3 = virtData.createResource(NS + "C3");
            virtData.add(c2, RDFS.subClassOf, c3);
            InfModel im = ModelFactory.createInfModel(ReasonerRegistry.getRDFSReasoner(), virtData);
            print_model("Data in DB", virtData);
            print_model("Data in Inference Model", im);

            Model premise = ModelFactory.createDefaultModel();
            premise.add(c1, RDFS.subClassOf, c2);
            print_model("Test listStatements", im.listStatements(c1, RDFS.subClassOf, null, premise));

        } catch (Exception e) {
            System.out.println("ERROR Test Failed.");
            e.printStackTrace(System.out);
        }
    }

    public static void test2() {
        try {
            System.out.println("--------------- TEST 2 -------------------");
            VirtModel virtData = VirtModel.openDatabaseModel("test:inf2", URL, uid, pwd);
            virtData.removeAll();

            String NS = PrintUtil.egNS;
            Resource c1 = virtData.createResource(NS + "C1");
            Resource c2 = virtData.createResource(NS + "C2");
            Resource c3 = virtData.createResource(NS + "C3");
            virtData.add(c2, RDFS.subClassOf, c3);
            OntModel om = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF, virtData);

            print_model("Data in DB", virtData);
            print_model("Data in Ontology Model", om);

            Model premise = ModelFactory.createDefaultModel();
            premise.add(c1, RDFS.subClassOf, c2);
            print_model("Test listStatements", om.listStatements(c1, RDFS.subClassOf, null, premise));

        } catch (Exception e) {
            System.out.println("ERROR Test Failed.");
            e.printStackTrace(System.out);
        }
    }

    public static void test3() {
        try {
            System.out.println("--------------- TEST 3 -------------------");
            VirtModel virtData = VirtModel.openDatabaseModel("test:inf3", URL, uid, pwd);
            virtData.removeAll();

            String NS = PrintUtil.egNS;
            Resource c1 = virtData.createResource(NS + "C1");
            Resource c2 = virtData.createResource(NS + "C2");
            Resource c3 = virtData.createResource(NS + "C3");
            virtData.add(c2, RDFS.subClassOf, c3);
            virtData.add(c1, RDFS.subClassOf, c2);
            InfModel im = ModelFactory.createInfModel(ReasonerRegistry.getRDFSReasoner(), virtData);

            exec_select("Data in DB", virtData, "select * where {?s ?p ?o}");

            exec_select("Data in Inference Model", im, "select * where {?s ?p ?o}");

            exec_select("Data in Inference Model", im, "select * where {<" + c1 + "> <" + RDFS.subClassOf + "> ?o}");

        } catch (Exception e) {
            System.out.println("ERROR Test Failed.");
            e.printStackTrace(System.out);
        }
    }

    public static void test4() {
        try {
            System.out.println("--------------- TEST 4 -------------------");
            VirtModel virtData = VirtModel.openDatabaseModel("test:inf4", URL, uid, pwd);
            virtData.removeAll();

            String NS = PrintUtil.egNS;
            Resource c1 = virtData.createResource(NS + "C1");
            Resource c2 = virtData.createResource(NS + "C2");
            Resource c3 = virtData.createResource(NS + "C3");
            virtData.add(c2, RDFS.subClassOf, c3);
            virtData.add(c1, RDFS.subClassOf, c2);
            OntModel om = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF, virtData);

            exec_select("Data in DB", virtData, "select * where {?s ?p ?o}");

            exec_select("Data in Ontology Model", om, "select * where {?s ?p ?o}");

            exec_select("Data in Ontology", om, "select * where {<" + c1 + "> <" + RDFS.subClassOf + "> ?o}");

        } catch (Exception e) {
            System.out.println("ERROR Test Failed.");
            e.printStackTrace(System.out);
        }
    }
}
