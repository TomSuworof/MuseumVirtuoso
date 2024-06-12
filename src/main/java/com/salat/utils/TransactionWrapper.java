package com.salat.utils;

import virtuoso.jena.driver.VirtGraph;

public class TransactionWrapper implements AutoCloseable {
    private final VirtGraph graph;

    public TransactionWrapper(VirtGraph graph) {
        this.graph = graph;
        System.out.println("=== Transaction ===");
        graph.getTransactionHandler().begin();
        System.out.println("begin Transaction.");
    }

    public void commit() {
        graph.getTransactionHandler().commit();
        System.out.println("commit Transaction.");
    }

    @Override
    public void close() {
        graph.getTransactionHandler().abort();
        System.out.println("abort Transaction.");
    }
}
