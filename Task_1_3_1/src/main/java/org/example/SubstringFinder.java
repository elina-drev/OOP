package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SubstringFinder {
    public static List<Integer> find(String fileName, String substring) throws IOException {
        List<Integer> result = new ArrayList<>();
        int bufferSize = 1024; // Размер буфера для чтения файла
        char[] buffer = new char[bufferSize];
        int substringLength = substring.length();
        char[] window = new char[substringLength];
        int windowPos = 0; // Позиция окна в файле
        int filePos = 0; // Глобальная позиция в файле

        try (Reader reader = new InputStreamReader(new FileInputStream(fileName), "UTF-8")) {
            int bytesRead;
            StringBuilder overflow = new StringBuilder(); // Для перекрытия между чтениями
            while ((bytesRead = reader.read(buffer)) != -1) {
                // Добавить остаток предыдущего чтения к текущему буферу
                String currentChunk = overflow.toString() + new String(buffer, 0, bytesRead);
                overflow.setLength(0);

                // Проверяем, есть ли пересечение с концом предыдущего буфера
                if (currentChunk.length() < substringLength) {
                    overflow.append(currentChunk);
                    break;
                }

                // Проходим по текущему блоку, сдвигая окно
                for (int i = 0; i <= currentChunk.length() - substringLength; i++) {
                    String windowString = currentChunk.substring(i, i + substringLength);
                    if (windowString.equals(substring)) {
                        result.add(filePos + i);
                    }
                }

                // Сохраняем перекрытие для следующей итерации
                overflow.append(currentChunk.substring(currentChunk.length() - substringLength + 1));
                filePos += currentChunk.length() - substringLength + 1;
            }
        }

        return result;
    }
}

