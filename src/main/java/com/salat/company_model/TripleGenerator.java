package com.salat.company_model;

import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.testing_framework.NodeCreateUtils;

public class TripleGenerator {
    public static final String myOntologyPrefix = "http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/";
    public static final String rdfsPrefix = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static final String owlPrefix = "http://www.w3.org/2002/07/owl#";

    public static Triple createTriple(String subject, String predicate, Object object) {
        return Triple.create(
                NodeFactory.createURI(subject),
                NodeFactory.createURI(predicate),
                NodeCreateUtils.create(String.valueOf(object))
        );
    }
}
