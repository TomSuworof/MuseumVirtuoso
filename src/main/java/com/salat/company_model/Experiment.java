package com.salat.company_model;

import com.salat.utils.Entity;
import org.apache.jena.graph.Triple;

import java.util.List;

import static com.salat.company_model.TripleGenerator.*;

public record Experiment(
        Integer runNumber,
        Integer totalCustomersNumber,
        Integer salary,
        Integer productCosts,
        Integer startingBalance,
        Integer startingRevenue,
        Float resultRevenue,
        Float resultCosts,
        Float resultProductPrice,
        Integer ticksPassed
) implements Entity {
    @Override
    public List<Triple> toTriples() {
        return List.of(
                createTriple(myOntologyPrefix + "experiment" + runNumber(), rdfsPrefix + "type", owlPrefix + "NamedIndividual"),
                createTriple(myOntologyPrefix + "experiment" + runNumber(), myOntologyPrefix + "run_number", runNumber()),
                createTriple(myOntologyPrefix + "experiment" + runNumber(), myOntologyPrefix + "total_customers_number", totalCustomersNumber()),
                createTriple(myOntologyPrefix + "experiment" + runNumber(), myOntologyPrefix + "salary", salary()),
                createTriple(myOntologyPrefix + "experiment" + runNumber(), myOntologyPrefix + "product_costs", productCosts()),
                createTriple(myOntologyPrefix + "experiment" + runNumber(), myOntologyPrefix + "starting_balance", startingBalance()),
                createTriple(myOntologyPrefix + "experiment" + runNumber(), myOntologyPrefix + "result_revenue", resultRevenue()),
                createTriple(myOntologyPrefix + "experiment" + runNumber(), myOntologyPrefix + "result_costs", resultCosts()),
                createTriple(myOntologyPrefix + "experiment" + runNumber(), myOntologyPrefix + "result_product_price", resultProductPrice()),
                createTriple(myOntologyPrefix + "experiment" + runNumber(), myOntologyPrefix + "ticks_passed", ticksPassed())
        );
    }
}
