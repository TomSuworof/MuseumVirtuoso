package com.salat.generator;

import org.apache.jena.graph.Triple;

import java.util.LinkedList;
import java.util.List;

import static com.salat.generator.TripleGenerator.*;

public record Ticket(
        Visitor visitor,
        Long number,
        Integer price
) implements Entity {

    @Override
    public List<Triple> toTriples() {
        String ticketName = visitor().name() + "s" + "Ticket";

        List<Triple> triples = new LinkedList<>();
        triples.addAll(List.of(
                createTriple(myOntologyPrefix + ticketName, rdfsPrefix + "type", owlPrefix + "NamedIndividual"),
                createTriple(myOntologyPrefix + ticketName, rdfsPrefix + "type", myOntologyPrefix + visitor().type().getCapitalized() + "_ticket"),
                createTriple(myOntologyPrefix + ticketName, myOntologyPrefix + "number", number()),
                createTriple(myOntologyPrefix + ticketName, myOntologyPrefix + "price", price()),
                createTriple(myOntologyPrefix + ticketName, myOntologyPrefix + "type2", String.format("\"%s\"", visitor().type().getLowerCase()))
        ));
        triples.addAll(visitor().toTriples());
        triples.add(createTriple(myOntologyPrefix + visitor().name(), myOntologyPrefix + "ticket", myOntologyPrefix + ticketName));
        return triples;
    }
}
