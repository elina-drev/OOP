package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//представление графа через матрицу смежности
public class Adjacency_matrix implements Graph {

    private boolean[][] adjacencyMatrix;
    /*
    2-мерная матрица adjacencyMatrix хранит булевы значения (true или false)
    adjacencyMatrix[i][j] == true => существует ребро из вершины i в вершину j.
    adjacencyMatrix[i][j] == false => такого ребра нет.
     */
    private int Count_of_vertex;

    //Конструктор
    public Adjacency_matrix() {
        adjacencyMatrix = new boolean[0][0];
        Count_of_vertex = 0;
    }

    @Override
    public void add_vertex(int vertex) {
        if (vertex >= Count_of_vertex) {
            boolean[][] newMatrix = new boolean[vertex + 1][vertex + 1];
            for (int i = 0; i < Count_of_vertex; i++) {
                System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0, Count_of_vertex);
            }
            adjacencyMatrix = newMatrix;
            Count_of_vertex = vertex + 1;
        }
    }

    @Override
    public void rem_vertex(int vertex) {
        if (vertex < Count_of_vertex) {
            for (int i = 0; i < Count_of_vertex; i++) {
                adjacencyMatrix[vertex][i] = false;
                adjacencyMatrix[i][vertex] = false;
            }
        }
    }

    @Override
    public void add_edge(int from, int to) {
        if (from < Count_of_vertex && to < Count_of_vertex) {
            adjacencyMatrix[from][to] = true;
        }
    }

    @Override
    public void rem_edge(int from, int to) {
        if (from < Count_of_vertex && to < Count_of_vertex) {
            adjacencyMatrix[from][to] = false;
        }
    }

    @Override
    public List<Integer> neighbors_of_vertex(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < Count_of_vertex; i++) {
            if (adjacencyMatrix[vertex][i]) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    @Override
    public void read_from_file(String filename) {
        /*
        формат файла
        n  // количество вершин
        i j // ребро между вершинами i и j
        i k // ребро между вершинами i и k

        5
        0 1
        3 4
         */
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));

            // Первая строка файла содержит количество вершин
            int vertices = Integer.parseInt(reader.readLine().trim());
            adjacencyMatrix = new boolean[vertices][vertices];
            Count_of_vertex = vertices;

            String line;
            while ((line = reader.readLine()) != null) {
                String[] edge = line.trim().split(" ");
                if (edge.length != 2) {
                    throw new IllegalArgumentException("Неверный формат строки: " + line);
                }

                try {
                    int from = Integer.parseInt(edge[0]);
                    int to = Integer.parseInt(edge[1]);

                    // Добавляем ребро между вершинами u и v
                    add_edge(from, to);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Неверный формат чисел в строке: " + line);
                }
            }

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка формата данных: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Ошибка закрытия файла: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void print_graph() {
        System.out.println("Матрица смежности:");
        for (int i = 0; i < Count_of_vertex; i++) {
            for (int j = 0; j < Count_of_vertex; j++) {
                System.out.print((adjacencyMatrix[i][j] ? "1 " : "0 "));
            }
            System.out.println();
        }
    }

    @Override
    public boolean equals(Object obj) {
        // Проверяем, не является ли объект самим собой
        if (this == obj) {
            return true;
        }

        // Проверяем, является ли объект экземпляром того же класса
        if (!(obj instanceof Adjacency_matrix)) {
            return false;
        }

        // Приводим объект к типу AdjacencyMatrixGraph
        Adjacency_matrix other = (Adjacency_matrix) obj;

        // Проверяем количество вершин
        if (this.Count_of_vertex != other.Count_of_vertex) {
            return false;
        }

        // Сравниваем матрицы смежности
        for (int i = 0; i < Count_of_vertex; i++) {
            for (int j = 0; j < Count_of_vertex; j++) {
                if (this.adjacencyMatrix[i][j] != other.adjacencyMatrix[i][j]) {
                    return false;
                }
            }
        }
        // Если все проверки пройдены, графы равны
        return true;
    }

    @Override
    // Реализация топологической сортировки на основе алгоритма Кана
    public List<Integer> topologicalSort() {
        // Массив для подсчета входящих рёбер для каждой вершины
        int[] inDegree = new int[Count_of_vertex];

        // Подсчет количества входящих рёбер для каждой вершины
        for (int i = 0; i < Count_of_vertex; i++) {
            for (int j = 0; j < Count_of_vertex; j++) {
                if (adjacencyMatrix[i][j]) {
                    inDegree[j]++;
                }
            }
        }

        // Очередь для вершин с входной степенью 0
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < Count_of_vertex; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        // Список для хранения результата топологической сортировки
        List<Integer> sortedList = new ArrayList<>();

        // Пока в очереди есть вершины
        while (!queue.isEmpty()) {
            int current = queue.poll();
            sortedList.add(current);

            // Обновляем степени входа для всех соседей текущей вершины
            for (int i = 0; i < Count_of_vertex; i++) {
                if (adjacencyMatrix[current][i]) {
                    inDegree[i]--;
                    if (inDegree[i] == 0) {
                        queue.add(i);
                    }
                }
            }
        }
        // Если количество отсортированных вершин меньше, чем количество вершин в графе, значит, есть цикл
        if (sortedList.size() != Count_of_vertex) {
            throw new RuntimeException("Граф содержит цикл");
        }

        return sortedList;
    }
}
