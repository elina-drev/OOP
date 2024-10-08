package org.example;

import java.util.Scanner;

public class Main {
    private static int[] scan_mas(){
        Scanner scan = new Scanner(System.in);
        int len = scan.nextInt();
        int[] arr = new int[len];
        for(int i = 0; i < len; ++i){
            arr[i] = scan.nextInt();
        }
        return arr;
    }
    private static void print_mas(int[] mas){
        System.out.println("\nSorted array is");
        for (int i = 0; i < mas.length; ++i){
            System.out.print(mas[i]+" ");
        }
        System.out.println();
    }
    public static void main(String[] args){
        int[] arr = scan_mas();

        Heapsort ob = new Heapsort();
        int[] mas = ob.sort(arr, arr.length);

        print_mas(mas);
    }
}


