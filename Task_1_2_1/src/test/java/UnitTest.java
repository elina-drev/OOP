import org.example.Adjacency_List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {
    private Adjacency_List graph;

    @BeforeEach
    public void setUp() {
        graph = new Adjacency_List();
    }

    @Test
    //Проверяет, что добавление вершины работает и что у неё нет соседей
    public void testAddVertex() {
        graph.add_vertex(0);
        assertTrue(graph.neighbors_of_vertex(0).isEmpty());
    }
    //Проверяет, что после удаления вершины её рёбра также удаляются
    @Test
    public void testRemoveVertex() {
        graph.add_vertex(0);
        graph.add_vertex(1);
        graph.add_edge(0, 1);

        graph.rem_vertex(0);
        assertFalse(graph.neighbors_of_vertex(1).contains(0));
        assertTrue(graph.neighbors_of_vertex(0).isEmpty());
    }
    //Проверяет, что рёбра можно добавлять между вершинами и что они правильно сохраняются
    @Test
    public void testAddEdge() {
        graph.add_vertex(0);
        graph.add_vertex(1);
        graph.add_edge(0, 1);
        List<Integer> neighbors = graph.neighbors_of_vertex(0);
        assertTrue(neighbors.contains(1));
    }
    //Проверяет, что рёбра могут быть удалены, и что их отсутствие подтверждается
    @Test
    public void testRemoveEdge() {
        graph.add_vertex(0);
        graph.add_vertex(1);
        graph.add_edge(0, 1);
        graph.rem_edge(0, 1);

        assertFalse(graph.neighbors_of_vertex(0).contains(1));
    }
    //Проверяет, что граф можно правильно загрузить из файла и что структура графа соответствует ожидаемой
    @Test
    public void testReadFromFile() throws IOException {
        String filename = "test_graph.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("3\n");
            writer.write("0 1\n");
            writer.write("1 2\n");
        }

        graph.read_from_file(filename);
        assertEquals(List.of(1), graph.neighbors_of_vertex(0));
        assertEquals(List.of(2), graph.neighbors_of_vertex(1));
        assertTrue(graph.neighbors_of_vertex(2).isEmpty());

        // Clean up the test file
        Files.delete(Path.of(filename));
    }
    //Проверяет, что два графа считаются равными, если у них одинаковая структура
    @Test
    public void testEquals() {
        Adjacency_List anotherGraph = new Adjacency_List();
        anotherGraph.add_vertex(0);
        anotherGraph.add_vertex(1);
        anotherGraph.add_edge(0, 1);

        graph.add_vertex(0);
        graph.add_vertex(1);
        graph.add_edge(0, 1);

        assertEquals(graph, anotherGraph);
    }
    //Проверяет, что топологическая сортировка работает для ациклического графа
    @Test
    public void testTopologicalSort() {
        graph.add_vertex(0);
        graph.add_vertex(1);
        graph.add_vertex(2);
        graph.add_edge(0, 1);
        graph.add_edge(1, 2);

        List<Integer> sorted = graph.topologicalSort();
        assertArrayEquals(new Integer[]{0, 1, 2}, sorted.toArray(new Integer[0]));
    }
    //Проверяет, что метод выбрасывает исключение при попытке выполнить топологическую сортировку на графе с циклом
    @Test
    public void testTopologicalSortWithCycle() {
        graph.add_vertex(0);
        graph.add_vertex(1);
        graph.add_edge(0, 1);
        graph.add_edge(1, 0); // Create a cycle

        Exception exception = assertThrows(RuntimeException.class, () -> {
            graph.topologicalSort();
        });

        assertEquals("Граф содержит цикл", exception.getMessage());
    }
}
