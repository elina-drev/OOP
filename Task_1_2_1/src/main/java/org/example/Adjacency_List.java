package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Adjacency_List implements Graph{
    private final Map<Integer, List<Integer>> adjacencyList;
    private int Count_of_vertex;
    public Adjacency_List() {
        adjacencyList = new HashMap<>();
        Count_of_vertex = 0;
    }

    @Override
    public void add_vertex(int vertex){
        if(vertex >= 0 && vertex <= Count_of_vertex){
            Count_of_vertex++;
            adjacencyList.putIfAbsent(vertex, new ArrayList<>());
        }

    }
    @Override
    public void rem_vertex(int vertex){
        if (vertex >= 0 && vertex < Count_of_vertex) {
            adjacencyList.remove(vertex);
            for (List<Integer> neighbors : adjacencyList.values()) {
                neighbors.remove((Integer) vertex);
            }
            Count_of_vertex--;
        }
    }
    @Override
    public void add_edge(int from, int to){
        if (from < Count_of_vertex && from >= 0 && to < Count_of_vertex && to >= 0) {
            adjacencyList.get(from).add(to);
        }
    }
    @Override
    public void rem_edge(int from, int to){
        if (from < Count_of_vertex && from >= 0 && to < Count_of_vertex && to >= 0) {
            adjacencyList.get(from).remove((Integer) to);
        }
    }
    @Override
    public List<Integer> neighbors_of_vertex(int vertex){
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }
    /*
        формат файла
        n  // количество вершин
        i j // ребро между вершинами i и j
        i k // ребро между вершинами i и k

        5
        0 1
        3 4
         */
    @Override
    public void read_from_file(String filename) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));

            // Первая строка файла содержит количество вершин
            int vertices = Integer.parseInt(reader.readLine().trim());

            // Инициализация списка смежности для всех вершин
            for (int i = 0; i < vertices; i++) {
                add_vertex(i); // Добавляем вершину в граф
            }

            String line;
            // Чтение всех строк с ребрами
            while ((line = reader.readLine()) != null) {
                String[] edge = line.trim().split(" ");
                if (edge.length != 2) {
                    throw new IllegalArgumentException("Invalid string format: " + line);
                }

                try {
                    int from = Integer.parseInt(edge[0]);
                    int to = Integer.parseInt(edge[1]);

                    // Добавляем ребро из вершины from в вершину to
                    add_edge(from, to);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid format of numbers in a string: " + line);
                }
            }

        } catch (IOException e) {
            System.err.println("File reading error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Data format error: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("File closing error: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void print_graph() {
        System.out.println("Adjacency List:");
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            Integer vertex = entry.getKey();
            List<Integer> neighbors = entry.getValue();

            // Форматируем список соседей в виде {neighbor1, neighbor2}
            StringBuilder neighborsString = new StringBuilder();
            neighborsString.append("{");

            for (int i = 0; i < neighbors.size(); i++) {
                neighborsString.append(neighbors.get(i));
                if (i < neighbors.size() - 1) {
                    neighborsString.append(", ");
                }
            }
            neighborsString.append("}");

            // Вывод вершины и её соседей
            System.out.println(vertex + " " + neighborsString);
        }
    }
    @Override
    public boolean equals(Object o) { // Сравнение графов
        // Проверяем, не является ли объект самим собой
        if (this == o) {
            return true;
        }

        // Проверяем, является ли объект экземпляром того же класса
        if (!(o instanceof Adjacency_List)) {
            return false;
        }

        // Приводим объект к типу Adjacency_List
        Adjacency_List other = (Adjacency_List) o;

        // Сравниваем количество вершин
        if (this.Count_of_vertex != other.Count_of_vertex) {
            return false;
        }

        // Сравниваем списки смежности
        return this.adjacencyList.equals(other.adjacencyList);
    }

    // Реализация топологической сортировки на основе алгоритма Кана
    @Override
    public List<Integer> topologicalSort() {
        // Массив для подсчета входящих рёбер для каждой вершины
        int[] inDegree = new int[Count_of_vertex];

        // Подсчет количества входящих рёбер для каждой вершины
        for (List<Integer> neighbors : adjacencyList.values()) {
            for (int neighbor : neighbors) {
                inDegree[neighbor]++;
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

            // Для каждого соседа текущей вершины уменьшаем степень входа
            for (int neighbor : adjacencyList.getOrDefault(current, new ArrayList<>())) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
        }

        class CycleInGraphException extends RuntimeException {
            public CycleInGraphException(String message) {
                super(message);
            }
        }

        // Если количество отсортированных вершин меньше, чем количество вершин в графе, значит, есть цикл
        if (sortedList.size() != Count_of_vertex) {
            throw new CycleInGraphException("Graph has a cycle. Topological sorting is not possible.");
        }

        return sortedList;
    }
}
