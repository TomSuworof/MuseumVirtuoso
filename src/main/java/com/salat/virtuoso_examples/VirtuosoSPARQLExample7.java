package com.salat.virtuoso_examples;

import org.apache.jena.graph.GraphUtil;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.util.iterator.ExtendedIterator;
import virtuoso.jena.driver.VirtGraph;

import java.util.List;

/**
 * BulkUpdateHandler was removed in Jena 3.0. See latest appearance and recommendation for replacement:
 * <a href="https://www.javadoc.io/static/org.apache.jena/jena-core/2.13.0/com/hp/hpl/jena/graph/BulkUpdateHandler.html">BulkUpdateHandler JavaDoc</a>.
 */
public class VirtuosoSPARQLExample7 {
    public static void main(String[] args) {
        String url;
        if (args.length == 0) {
            url = "jdbc:virtuoso://localhost:1111";
        } else {
            url = args[0];
        }

        Node foo1 = NodeFactory.createURI("http://example.org/#foo1");
        Node bar1 = NodeFactory.createURI("http://example.org/#bar1");
        Node baz1 = NodeFactory.createURI("http://example.org/#baz1");

        Node foo2 = NodeFactory.createURI("http://example.org/#foo2");
        Node bar2 = NodeFactory.createURI("http://example.org/#bar2");
        Node baz2 = NodeFactory.createURI("http://example.org/#baz2");

        Node foo3 = NodeFactory.createURI("http://example.org/#foo3");
        Node bar3 = NodeFactory.createURI("http://example.org/#bar3");
        Node baz3 = NodeFactory.createURI("http://example.org/#baz3");

        List<Triple> triples1 = List.of(
                Triple.create(foo1, bar1, baz1),
                Triple.create(foo2, bar2, baz2),
                Triple.create(foo3, bar3, baz3)
        );

        List<Triple> triples2 = List.of(
                Triple.create(foo1, bar1, baz1),
                Triple.create(foo2, bar2, baz2)
        );

        VirtGraph graph = new VirtGraph("Example7", url, "dba", "1");

        graph.clear();

        System.out.println("graph.isEmpty() = " + graph.isEmpty());
        System.out.println("Add List with 3 triples to graph <Example7> via BulkUpdateHandler.");

        GraphUtil.add(graph, triples1);

        System.out.println("graph.isEmpty() = " + graph.isEmpty());
        System.out.println("graph.getCount() = " + graph.getCount());

        ExtendedIterator<Triple> iterator = graph.find(Node.ANY, Node.ANY, Node.ANY);
        System.out.println("\ngraph.find(Node.ANY, Node.ANY, Node.ANY) \nResult:");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("\n\nDelete List of 2 triples from graph <Example7> via BulkUpdateHandler.");

        GraphUtil.delete(graph, triples2);

        System.out.println("graph.isEmpty() = " + graph.isEmpty());
        System.out.println("graph.getCount() = " + graph.getCount());

        iterator = graph.find(Node.ANY, Node.ANY, Node.ANY);
        System.out.println("\ngraph.find(Node.ANY, Node.ANY, Node.ANY) \nResult:");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        graph.clear();
        System.out.println("\nCLEAR graph <Example7>");

    }
}
