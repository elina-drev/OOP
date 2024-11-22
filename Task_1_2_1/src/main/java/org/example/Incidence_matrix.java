package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Incidence_matrix implements Graph{
    private int[][] IncidenceMatrix;
    private int Count_of_vertex;
    private int edgeCount;


    //Конструктор
    public Incidence_matrix() {
        IncidenceMatrix = new int[0][0];// Инициализация с нулевым количеством рёбер
        edgeCount = 0;
        Count_of_vertex = 0;
    }
    @Override
    public void add_vertex(int vertex){
        // Добавляем новую строку для вершины в матрицу инцидентности
        if (vertex == Count_of_vertex) {
            int[][] newMatrix = new int[vertex + 1][edgeCount];
            for (int i = 0; i < Count_of_vertex; i++) {
                System.arraycopy(IncidenceMatrix[i], 0, newMatrix[i], 0, edgeCount);
            }
            IncidenceMatrix = newMatrix;
            Count_of_vertex++;
        }
    }
    @Override
    public void rem_vertex(int vertex){
        if (vertex < Count_of_vertex && vertex >= 0) {
            // Удаляем вершину из матрицы (удаляем строку)
            int[][] newMatrix = new int[Count_of_vertex - 1][edgeCount];
            int newRow = 0;
            for (int i = 0; i < Count_of_vertex; i++) {
                if (i == vertex) continue; // Пропускаем удаляемую вершину
                System.arraycopy(IncidenceMatrix[i], 0, newMatrix[newRow], 0, edgeCount);
                newRow++;
            }
            IncidenceMatrix = newMatrix;
            Count_of_vertex--;
        }
    }
    @Override
    public void add_edge(int from, int to){
        if (from < Count_of_vertex && from >= 0 && to < Count_of_vertex && to >= 0) {
            // Добавляем новый столбец для нового ребра
            int[][] newMatrix = new int[Count_of_vertex][edgeCount + 1];
            for (int i = 0; i < Count_of_vertex; i++) {
                System.arraycopy(IncidenceMatrix[i], 0, newMatrix[i], 0, edgeCount);
            }

            // Устанавливаем инцидентности для вершины 'from' и 'to'
            newMatrix[from][edgeCount] = 1; // 1 для начальной вершины
            newMatrix[to][edgeCount] = -1; // -1 для конечной вершины

            IncidenceMatrix = newMatrix;
            edgeCount++;
        }
    }
    @Override
    public void rem_edge(int from, int to){
        if (from < Count_of_vertex && from >= 0 && to < Count_of_vertex && to >= 0) {
            // Ищем соответствующее ребро в матрице
            for (int j = 0; j < edgeCount; j++) {
                if (IncidenceMatrix[from][j] == 1 && IncidenceMatrix[to][j] == -1) {
                    // Удаляем ребро (столбец)
                    int[][] newMatrix = new int[Count_of_vertex][edgeCount - 1];
                    for (int i = 0; i < Count_of_vertex; i++) {
                        int newCol = 0;
                        for (int k = 0; k < edgeCount; k++) {
                            if (k == j) continue;  // Пропускаем удаляемое ребро
                            newMatrix[i][newCol] = IncidenceMatrix[i][k];
                            newCol++;
                        }
                    }
                    IncidenceMatrix = newMatrix;
                    edgeCount--;
                    return;
                }
            }
        }
    }
    @Override
    public List<Integer> neighbors_of_vertex(int vertex){
        List<Integer> neighbors = new ArrayList<>();
        if (vertex >= 0 && vertex < Count_of_vertex) {
            // Проходим по столбцам (рёбрам) и ищем инцидентные вершины
            for (int j = 0; j < edgeCount; j++) {
                if (IncidenceMatrix[vertex][j] == 1) {
                    // Если вершина инцидентна ребру, находим другую вершину
                    for (int i = 0; i < Count_of_vertex; i++) {
                        if (i != vertex && IncidenceMatrix[i][j] == -1) {
                            neighbors.add(i);
                            break;
                        }
                    }
                }
            }
        }
        return neighbors;
    }
    @Override
    public void read_from_file(String filename) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));

            // Первая строка файла содержит количество вершин
            int vertices = Integer.parseInt(reader.readLine().trim());
            Count_of_vertex = vertices;
            IncidenceMatrix = new int[Count_of_vertex][0];  // Инициализация матрицы с нулевым количеством рёбер

            String line;
            List<int[]> edges = new ArrayList<>();  // Список для хранения рёбер

            // Чтение всех строк с рёбрами
            while ((line = reader.readLine()) != null) {
                String[] edge = line.trim().split(" ");
                if (edge.length != 2) {
                    throw new IllegalArgumentException("Invalid string format: " + line);
                }

                try {
                    int from = Integer.parseInt(edge[0]);
                    int to = Integer.parseInt(edge[1]);

                    // Добавляем ребро в список
                    edges.add(new int[]{from, to});
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid format of numbers in a string: " + line);
                }
            }

            // После того как мы прочитали все рёбра, добавляем их в матрицу инцидентности
            for (int[] edge : edges) {
                add_edge(edge[0], edge[1]);
            }

        } catch (IOException e) {
            System.err.println("File reading error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Data format error: " + e.getMessage());
        }
    }
    @Override
    public void print_graph(){
        StringBuilder sb = new StringBuilder();

        System.out.println("Incidence matrix:");

        for (int i = 0; i < Count_of_vertex; i++) {
            sb.append(i).append("| "); // Добавляем номер вершины в первый столбец
            for (int j = 0; j < edgeCount; j++) {
                sb.append(IncidenceMatrix[i][j]).append(" ");
            }
            sb.append("\n"); // Переход на новую строку после каждой строки матрицы
        }
        System.out.println(sb); // Один раз выводим всё содержимое
    }
    @Override
    public boolean equals(Object obj) {
        // Проверяем, не является ли объект самим собой
        if (this == obj) {
            return true;
        }

        // Проверяем, является ли объект экземпляром того же класса
        if (!(obj instanceof Incidence_matrix)) {
            return false;
        }

        // Приводим объект к типу Incidence_matrix
        Incidence_matrix other = (Incidence_matrix) obj;

        // Проверяем количество вершин
        if (this.Count_of_vertex != other.Count_of_vertex) {
            return false;
        }

        // Сравниваем матрицы инцидентности
        for (int i = 0; i < Count_of_vertex; i++) {
            for (int j = 0; j < edgeCount; j++) {
                if (this.IncidenceMatrix[i][j] != other.IncidenceMatrix[i][j]) {
                    return false;
                }
            }
        }

        // Если все проверки пройдены, графы равны
        return true;
    }
    @Override
    public List<Integer> topologicalSort(){
        // Массив для подсчета входящих рёбер для каждой вершины
        int[] inDegree = new int[Count_of_vertex];

        // Подсчет количества входящих рёбер для каждой вершины
        for (int j = 0; j < edgeCount; j++) { // Проходим по всем рёбрам
            for (int i = 0; i < Count_of_vertex; i++) { // Проходим по всем вершинам
                if (IncidenceMatrix[i][j] == -1) {
                    // Если найдено, увеличиваем входную степень для вершины i
                    inDegree[i]++;
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
            for (int j = 0; j < edgeCount; j++) {
                if (IncidenceMatrix[current][j] == 1) {
                    // Если это ребро ведет от текущей вершины
                    for (int i = 0; i < Count_of_vertex; i++) {
                        if (IncidenceMatrix[i][j] == -1) {
                            inDegree[i]--;
                            if (inDegree[i] == 0) {
                                queue.add(i);
                            }
                        }
                    }
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
