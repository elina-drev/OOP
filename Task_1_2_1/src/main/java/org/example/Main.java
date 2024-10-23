package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Adjacency_matrix();
        graph.read_from_file("graph.txt");
        graph.print_graph();

        //сортировка
        try {
            List<Integer> sorted = graph.topologicalSort();
            System.out.println("Топологическая сортировка: " + sorted);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }

    }
}