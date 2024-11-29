package org.example;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            List<Integer> positions = SubstringFinder.find("input.txt", "needle");
            System.out.println(positions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}