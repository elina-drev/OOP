import org.example.Adjacency_List;
import org.example.Adjacency_matrix;
import org.example.Incidence_matrix;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestGraphs {
    private final Adjacency_List graph_adj_list = new Adjacency_List();

    @Test
    //Проверяет, что добавление вершины работает и что у неё нет соседей
    // а также на неправильный ввод
    public void testAddVertex_adj_list() {
        graph_adj_list.add_vertex(0);
        assertTrue(graph_adj_list.neighbors_of_vertex(0).isEmpty());
        graph_adj_list.add_vertex(-1);
        assertTrue(graph_adj_list.neighbors_of_vertex(-1).isEmpty());
    }
    //Проверяет, что после удаления вершины её рёбра также удаляются
    @Test
    public void testRemoveVertex_adj_list() {
        graph_adj_list.add_vertex(0);
        graph_adj_list.add_vertex(1);
        graph_adj_list.add_edge(0, 1);

        graph_adj_list.rem_vertex(0);
        assertFalse(graph_adj_list.neighbors_of_vertex(1).contains(0));
        assertTrue(graph_adj_list.neighbors_of_vertex(0).isEmpty());
    }
    //Проверяет, что рёбра можно добавлять между вершинами и что они правильно сохраняются
    @Test
    public void testAddEdge_adj_list() {
        graph_adj_list.add_vertex(0);
        graph_adj_list.add_vertex(1);
        graph_adj_list.add_edge(0, 1);
        List<Integer> neighbors = graph_adj_list.neighbors_of_vertex(0);
        assertTrue(neighbors.contains(1));
    }
    //Проверяет, что рёбра могут быть удалены, и что их отсутствие подтверждается
    @Test
    public void testRemoveEdge_adj_list() {
        graph_adj_list.add_vertex(0);
        graph_adj_list.add_vertex(1);
        graph_adj_list.add_edge(0, 1);
        graph_adj_list.rem_edge(0, 1);

        assertFalse(graph_adj_list.neighbors_of_vertex(0).contains(1));
    }
    //Проверяет, что граф можно правильно загрузить из файла и что структура графа соответствует ожидаемой
    @Test
    public void testReadFromFile_adj_list() throws IOException {
        String filename = "test_graph.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("3\n");
            writer.write("0 1\n");
            writer.write("1 2\n");
        }

        graph_adj_list.read_from_file(filename);
        assertEquals(List.of(1), graph_adj_list.neighbors_of_vertex(0));
        assertEquals(List.of(2), graph_adj_list.neighbors_of_vertex(1));
        assertTrue(graph_adj_list.neighbors_of_vertex(2).isEmpty());
    }
    //Проверяет, что два графа считаются равными, если у них одинаковая структура
    @Test
    public void testEquals_adj_list() {
        Adjacency_List anotherGraph = new Adjacency_List();
        anotherGraph.add_vertex(0);
        anotherGraph.add_vertex(1);
        anotherGraph.add_edge(0, 1);

        graph_adj_list.add_vertex(0);
        graph_adj_list.add_vertex(1);
        graph_adj_list.add_edge(0, 1);

        assertEquals(graph_adj_list, anotherGraph);
    }
    //Проверяет, что топологическая сортировка работает для ациклического графа
    @Test
    public void testTopologicalSort_adj_list() {
        graph_adj_list.add_vertex(0);
        graph_adj_list.add_vertex(1);
        graph_adj_list.add_vertex(2);
        graph_adj_list.add_edge(0, 1);
        graph_adj_list.add_edge(1, 2);

        List<Integer> sorted = graph_adj_list.topologicalSort();
        assertArrayEquals(new Integer[]{0, 1, 2}, sorted.toArray(new Integer[0]));
    }
    //Проверяет, что метод выбрасывает исключение при попытке выполнить топологическую сортировку на графе с циклом
    @Test
    public void testTopologicalSortWithCycle_adj_list() {
        graph_adj_list.add_vertex(0);
        graph_adj_list.add_vertex(1);
        graph_adj_list.add_edge(0, 1);
        graph_adj_list.add_edge(1, 0); // Create a cycle

        Exception exception = assertThrows(RuntimeException.class, () -> {
            graph_adj_list.topologicalSort();
        });

        assertEquals("Graph has a cycle. Topological sorting is not possible.", exception.getMessage());
    }

    private final Adjacency_matrix graph_adj_matrix = new Adjacency_matrix();

    @Test
    // Проверяет, что добавление вершины работает и что у неё нет соседей
    public void testAddVertex_adj_matrix() {
        graph_adj_matrix.add_vertex(0);
        assertTrue(graph_adj_matrix.neighbors_of_vertex(0).isEmpty());
        graph_adj_matrix.add_vertex(1);
        assertTrue(graph_adj_matrix.neighbors_of_vertex(1).isEmpty());
    }

    @Test
    // Проверяет, что после удаления вершины её рёбра также удаляются
    public void testRemoveVertex_adj_matrix() {
        graph_adj_matrix.add_vertex(0);
        graph_adj_matrix.add_vertex(1);
        graph_adj_matrix.add_edge(0, 1);

        graph_adj_matrix.rem_vertex(0);
        assertFalse(graph_adj_matrix.neighbors_of_vertex(1).contains(0));
        assertTrue(graph_adj_matrix.neighbors_of_vertex(0).isEmpty());
    }

    @Test
    // Проверяет, что рёбра можно добавлять между вершинами и что они правильно сохраняются
    public void testAddEdge_adj_matrix() {
        graph_adj_matrix.add_vertex(0);
        graph_adj_matrix.add_vertex(1);
        graph_adj_matrix.add_edge(0, 1);

        List<Integer> neighbors = graph_adj_matrix.neighbors_of_vertex(0);
        assertTrue(neighbors.contains(1));
    }

    @Test
    // Проверяет, что рёбра могут быть удалены, и что их отсутствие подтверждается
    public void testRemoveEdge_adj_matrix() {
        graph_adj_matrix.add_vertex(0);
        graph_adj_matrix.add_vertex(1);
        graph_adj_matrix.add_edge(0, 1);
        graph_adj_matrix.rem_edge(0, 1);

        assertFalse(graph_adj_matrix.neighbors_of_vertex(0).contains(1));
    }

    @Test
    // Проверяет, что граф можно правильно загрузить из файла и что структура графа соответствует ожидаемой
    public void testReadFromFile_adj_matrix() throws IOException {
        String filename = "test_graph.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("3\n");
            writer.write("0 1\n");
            writer.write("1 2\n");
        }

        graph_adj_matrix.read_from_file(filename);
        assertEquals(List.of(1), graph_adj_matrix.neighbors_of_vertex(0));
        assertEquals(List.of(2), graph_adj_matrix.neighbors_of_vertex(1));
        assertTrue(graph_adj_matrix.neighbors_of_vertex(2).isEmpty());
    }

    @Test
    // Проверяет, что два графа считаются равными, если у них одинаковая структура
    public void testEquals_adj_matrix() {
        Adjacency_matrix anotherGraph = new Adjacency_matrix();
        anotherGraph.add_vertex(0);
        anotherGraph.add_vertex(1);
        anotherGraph.add_edge(0, 1);

        graph_adj_matrix.add_vertex(0);
        graph_adj_matrix.add_vertex(1);
        graph_adj_matrix.add_edge(0, 1);

        assertEquals(graph_adj_matrix, anotherGraph);
    }

    @Test
    // Проверяет, что топологическая сортировка работает для ациклического графа
    public void testTopologicalSort_adj_matrix() {
        graph_adj_matrix.add_vertex(0);
        graph_adj_matrix.add_vertex(1);
        graph_adj_matrix.add_vertex(2);
        graph_adj_matrix.add_edge(0, 1);
        graph_adj_matrix.add_edge(1, 2);

        List<Integer> sorted = graph_adj_matrix.topologicalSort();
        assertArrayEquals(new Integer[]{0, 1, 2}, sorted.toArray(new Integer[0]));
    }

    @Test
    // Проверяет, что метод выбрасывает исключение при попытке выполнить топологическую сортировку на графе с циклом
    public void testTopologicalSortWithCycle_adj_matrix() {
        graph_adj_matrix.add_vertex(0);
        graph_adj_matrix.add_vertex(1);
        graph_adj_matrix.add_edge(0, 1);
        graph_adj_matrix.add_edge(1, 0); // Create a cycle

        Exception exception = assertThrows(RuntimeException.class, () -> {
            graph_adj_matrix.topologicalSort();
        });

        assertEquals("Graph has a cycle. Topological sorting is not possible.", exception.getMessage());
    }

    private final Incidence_matrix graph_inc_matrix = new Incidence_matrix();

    @Test
    void testAddVertex_inc_matrix() {
        graph_inc_matrix.add_vertex(0); // Добавляем первую вершину
        assertTrue(graph_inc_matrix.neighbors_of_vertex(0).isEmpty());
        graph_inc_matrix.add_vertex(1);
        assertTrue(graph_inc_matrix.neighbors_of_vertex(1).isEmpty());
    }

    @Test
    void testRemoveVertex_inc_matrix() {
        graph_inc_matrix.add_vertex(0);
        graph_inc_matrix.add_vertex(1);
        graph_inc_matrix.add_edge(0, 1);

        graph_inc_matrix.rem_vertex(0);
        assertFalse(graph_inc_matrix.neighbors_of_vertex(1).contains(0));
        assertTrue(graph_inc_matrix.neighbors_of_vertex(0).isEmpty());  // Количество вершин уменьшилось на 1
    }

    @Test
    void testAddEdge_inc_matrix() {
        graph_inc_matrix.add_vertex(0);
        graph_inc_matrix.add_vertex(1);
        graph_inc_matrix.add_edge(0, 1);

        List<Integer> neighbors = graph_inc_matrix.neighbors_of_vertex(0);
        assertTrue(neighbors.contains(1));  // Количество рёбер должно быть 1
    }

    @Test
    void testRemoveEdge_inc_matrix() {
        graph_inc_matrix.add_vertex(0);
        graph_inc_matrix.add_vertex(1);
        graph_inc_matrix.add_edge(0, 1);
        graph_inc_matrix.rem_edge(0, 1);  // Удаляем ребро от 0 к 1
        assertFalse(graph_inc_matrix.neighbors_of_vertex(0).contains(1));  // Количество рёбер должно быть 0
    }

    @Test
    void testReadFromFile() throws IOException {
        String filename = "test_graph.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("3\n");
            writer.write("0 1\n");
            writer.write("1 2\n");
        }

        graph_inc_matrix.read_from_file(filename);
        assertEquals(List.of(1), graph_inc_matrix.neighbors_of_vertex(0));
        assertEquals(List.of(2), graph_inc_matrix.neighbors_of_vertex(1));
        assertTrue(graph_inc_matrix.neighbors_of_vertex(2).isEmpty());
    }

    @Test
    // Проверяет, что два графа считаются равными, если у них одинаковая структура
    public void testEquals_inc_matrix() {
        Incidence_matrix anotherGraph = new Incidence_matrix();
        anotherGraph.add_vertex(0);
        anotherGraph.add_vertex(1);
        anotherGraph.add_edge(0, 1);

        graph_inc_matrix.add_vertex(0);
        graph_inc_matrix.add_vertex(1);
        graph_inc_matrix.add_edge(0, 1);

        assertEquals(graph_inc_matrix, anotherGraph);
    }

    @Test
    void testTopologicalSort() {
        graph_inc_matrix.add_vertex(0);
        graph_inc_matrix.add_vertex(1);
        graph_inc_matrix.add_vertex(2);
        graph_inc_matrix.add_edge(0, 1);
        graph_inc_matrix.add_edge(1, 2);

        List<Integer> sorted = graph_inc_matrix.topologicalSort();
        assertArrayEquals(new Integer[]{0, 1, 2}, sorted.toArray(new Integer[0]));
    }

    @Test
    void testTopologicalSortWithCycle() {
        graph_inc_matrix.add_vertex(0);
        graph_inc_matrix.add_vertex(1);
        graph_inc_matrix.add_edge(0, 1);
        graph_inc_matrix.add_edge(1, 0); // Create a cycle

        Exception exception = assertThrows(RuntimeException.class, () -> {
            graph_inc_matrix.topologicalSort();
        });

        assertEquals("Graph has a cycle. Topological sorting is not possible.", exception.getMessage());
    }

}
