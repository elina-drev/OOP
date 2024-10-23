package org.example;

import java.util.List;
//граф ориентированный
public interface Graph {
    void add_vertex(int vertex);
    void rem_vertex(int vertex);
    void add_edge(int from, int to);
    void rem_edge(int from, int to);
    List<Integer> neighbors_of_vertex(int vertex);
    void read_from_file(String filename);
    void print_graph();
    boolean equals(Object o);         // Сравнение графов
    List<Integer> topologicalSort();
}
