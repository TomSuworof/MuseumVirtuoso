package com.salat.generator;

import org.apache.jena.graph.Triple;

import java.util.List;

public interface Entity {
    List<Triple> toTriples();
}
