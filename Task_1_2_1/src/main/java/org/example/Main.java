package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Adjacency_List();
        graph.read_from_file("graph.txt");
        graph.print_graph();
//        graph.add_vertex(0);
//        graph.add_vertex(1);
//        graph.add_edge(0, 1);

        graph.rem_vertex(0);
        int vert = 0;
        System.out.println("Vertex " + vert + ": " + graph.neighbors_of_vertex(vert));

        //сортировка
        try {
            List<Integer> sorted = graph.topologicalSort();
            System.out.println("Topological Sort: " + sorted);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }

    }
}