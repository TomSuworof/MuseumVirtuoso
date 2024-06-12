package com.salat.generator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.salat.utils.Entity;
import org.apache.jena.graph.Triple;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.salat.generator.TicketsTripleGenerator.*;

public record Visitor(
        String name,
        Type type,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        Date birthday
) implements Entity {
    public enum Type {
        ADULT,
        CHILD,
        ELDERLY;

        public String getLowerCase() {
            return name().toLowerCase();
        }

        public String getCapitalized() {
            return name().toLowerCase().substring(0, 1).toUpperCase() + name().toLowerCase().substring(1);
        }

        @Override
        @JsonValue
        public String toString() {
            return getLowerCase();
        }
    }

    @Override
    public List<Triple> toTriples() {
        return List.of(
                createTriple(myOntologyPrefix + name() + "Visitor", rdfsPrefix + "type", owlPrefix + "NamedIndividual"),
                createTriple(myOntologyPrefix + name() + "Visitor", rdfsPrefix + "type", myOntologyPrefix + type().getLowerCase() + "_museum_visitor"),
                createTriple(myOntologyPrefix + name() + "Visitor", myOntologyPrefix + "birthday", String.format("\"%s\"", new SimpleDateFormat("dd.MM.yyyy").format(birthday()))),
                createTriple(myOntologyPrefix + name() + "Visitor", myOntologyPrefix + "name2", String.format("\"%s\"", name())),
                createTriple(myOntologyPrefix + name() + "Visitor", myOntologyPrefix + "privileges_type", String.format("\"%s\"", type().getLowerCase())),
                createTriple(myOntologyPrefix + name() + "Visitor", myOntologyPrefix + "type1", String.format("\"%s\"", type().getLowerCase()))
        );
    }
}
