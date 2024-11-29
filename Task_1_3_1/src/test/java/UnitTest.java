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

    // Тест: проверка поиска в файле с ASCII символами
    public static void testASCII() throws IOException {
        String testFile = "test_file_ascii.txt";
        String searchSubstring = "abc";
        long repeatCount = 1_000_000L; // Количество повторений

        // Генерация файла с повторяющимся контентом
        System.out.println("File generation...");
        generateLargeFile(testFile, "xyzabcxyz" + searchSubstring + "xyz", repeatCount);

        // Запуск поиска
        System.out.println("Substring Search...");
        List<Integer> positions = SubstringFinder.find(testFile, searchSubstring);

        // Проверка результата
        System.out.println("The first 10 positions: " + positions.subList(0, Math.min(10, positions.size())));
    }

    // Тест: проверка поиска в файле с дополнительными символами Unicode (например, с диакритическими знаками)
    public static void testUnicode() throws IOException {
        String testFile = "test_file_unicode.txt";
        String searchSubstring = "é";
        long repeatCount = 1_000_000L; // Количество повторений

        // Генерация файла с повторяющимся контентом
        System.out.println("File generation...");
        generateLargeFile(testFile, "haystackhaystack" + searchSubstring + "haystack", repeatCount);

        // Запуск поиска
        System.out.println("Substring Search...");
        List<Integer> positions = SubstringFinder.find(testFile, searchSubstring);

        // Проверка результата
        System.out.println("The first 10 positions: " + positions.subList(0, Math.min(10, positions.size())));
    }

    // Тест: проверка поиска в файле с китайскими иероглифами
    public static void testChinese() throws IOException {
        String testFile = "test_file_chinese.txt";
        String searchSubstring = "你好";
        long repeatCount = 1_000_000L; // Количество повторений

        // Генерация файла с повторяющимся контентом
        System.out.println("File generation...");
        generateLargeFile(testFile, "世界你好世界" + searchSubstring + "世界", repeatCount);

        // Запуск поиска
        System.out.println("Substring Search...");
        List<Integer> positions = SubstringFinder.find(testFile, searchSubstring);

        // Проверка результата
        System.out.println("The first 10 positions: " + positions.subList(0, Math.min(10, positions.size())));
    }

    // Тест: проверка поиска в файле с эмодзи
    public static void testEmojis() throws IOException {
        String testFile = "test_file_emojis.txt";
        String searchSubstring = "😊";
        long repeatCount = 1_000_000L; // Количество повторений

        // Генерация файла с повторяющимся контентом
        System.out.println("File generation...");
        generateLargeFile(testFile, "🙂😉😊😎" + searchSubstring + "🙂😉", repeatCount);

        // Запуск поиска
        System.out.println("Substring Search...");
        List<Integer> positions = SubstringFinder.find(testFile, searchSubstring);

        // Проверка результата
        System.out.println("Results:");
        System.out.println("The number of found subwords: " + positions.size());
        System.out.println("The first 10 positions: " + positions.subList(0, Math.min(10, positions.size())));
    }

    public static void main(String[] args) {
        try {
            System.out.println("Running test for ASCII characters...");
            testASCII();
            System.out.println("\nRunning test for Unicode characters...");
            testUnicode();
            System.out.println("\nRunning test for Chinese characters...");
            testChinese();
            System.out.println("\nRunning test for Emojis...");
            testEmojis();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
