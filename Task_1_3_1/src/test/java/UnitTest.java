import org.example.SubstringFinder;

import java.io.*;
import java.util.List;

public class UnitTest {
    // Метод для создания тестового файла большого размера
    private static void generateLargeFile(String fileName, String content, long repeatCount) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (long i = 0; i < repeatCount; i++) {
                writer.write(content);
            }
        }
    }

    // Тест: проверка поиска в большом файле
    public static void testLargeFile() throws IOException {
        String testFile = "large_test_file.txt";
        String searchSubstring = "needle";
        long repeatCount = 10_000_000L; // Количество повторений

        // Генерация файла с повторяющимся контентом
        System.out.println("File generation...");
        generateLargeFile(testFile, "haystackhaystack" + searchSubstring + "haystack", repeatCount);

        // Запуск поиска
        System.out.println("Substring Search...");
        List<Integer> positions = SubstringFinder.find(testFile, searchSubstring);

        // Проверка результата
        System.out.println("Results:");
        System.out.println("The number of found subwords: " + positions.size());
        System.out.println("The first 10 positions: " + positions.subList(0, Math.min(10, positions.size())));
    }

    // Основной метод для запуска тестов
    public static void main(String[] args) {
        try {
            testLargeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
