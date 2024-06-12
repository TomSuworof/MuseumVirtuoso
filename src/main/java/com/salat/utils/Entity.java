package com.salat.utils;

import org.apache.jena.graph.Triple;

import java.util.List;

public interface Entity {
    List<Triple> toTriples();
}
