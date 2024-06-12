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
    /*
<http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/runNumber/1> rdf:type <http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/Experiment>
<http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/runNumber/1> rdfs:label "Experiment 1"
<http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/runNumber/1> <http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/run_number> 1
<http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/runNumber/1> <http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/total_customers_number> 1
<http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/runNumber/1>  <http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/salary> 1
<http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/runNumber/1> <http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/product_costs> 1
<http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/runNumber/1> <http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/starting_balance> 1
<http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/runNumber/1> <http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/starting_revenue> 10000
<http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/runNumber/1> <http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/result_revenue> 11070.268090757792
<http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/runNumber/1> <http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/result_costs> 10994.6196763185
<http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/runNumber/1> <http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/result_product_price> 1.1142705314999999
<http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/runNumber/1> <http://www.semanticweb.org/rc199/ontologies/2024/5/company-model/ticks_passed> 32123
     */

    @Override
    public List<Triple> toTriples() {
        return List.of(
                createTriple(myOntologyPrefix + "runNumber/" + runNumber(), rdfPrefix + "type", myOntologyPrefix + "Experiment"),
                createTriple(myOntologyPrefix + "runNumber/" + runNumber(), rdfsPrefix + "label", "Experiment " + runNumber()),
                createTriple(myOntologyPrefix + "runNumber/" + runNumber(), myOntologyPrefix + "run_number", runNumber()),
                createTriple(myOntologyPrefix + "runNumber/" + runNumber(), myOntologyPrefix + "total_customers_number", totalCustomersNumber()),
                createTriple(myOntologyPrefix + "runNumber/" + runNumber(), myOntologyPrefix + "salary", salary()),
                createTriple(myOntologyPrefix + "runNumber/" + runNumber(), myOntologyPrefix + "product_costs", productCosts()),
                createTriple(myOntologyPrefix + "runNumber/" + runNumber(), myOntologyPrefix + "starting_balance", startingBalance()),
                createTriple(myOntologyPrefix + "runNumber/" + runNumber(), myOntologyPrefix + "starting_revenue", startingRevenue()),
                createTriple(myOntologyPrefix + "runNumber/" + runNumber(), myOntologyPrefix + "result_revenue", resultRevenue()),
                createTriple(myOntologyPrefix + "runNumber/" + runNumber(), myOntologyPrefix + "result_costs", resultCosts()),
                createTriple(myOntologyPrefix + "runNumber/" + runNumber(), myOntologyPrefix + "result_product_price", resultProductPrice()),
                createTriple(myOntologyPrefix + "runNumber/" + runNumber(), myOntologyPrefix + "ticks_passed", ticksPassed())
        );
    }
}
