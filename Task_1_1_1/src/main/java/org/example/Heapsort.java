package org.example;

import java.util.Scanner;

public class Heapsort {
    // Сортировка исходного массива длины len методом пирамидальной сортировки
    public void sort(int[] arr, int len)
    {
        // Построение кучи (перегруппируем массив)
        for (int i = len / 2 - 1; i >= 0; i--)
            heapify(arr, len, i);

        // Один за другим извлекаем элементы из кучи
        for (int i=len-1; i>=0; i--)
        {
            // Перемещаем текущий корень в конец
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Вызываем процедуру heap ify на уменьшенной куче
            heapify(arr, i, 0);
        }
    }

    // Процедура для преобразования в двоичную кучу поддерева с корневым узлом i, что является индексом в arr[]. n - размер кучи
    public void heapify(int[] arr, int n, int i)
    {
        int largest = i; // Инициализируем наибольший элемент как корень
        int l = 2*i + 1; // левый = 2*i + 1
        int r = 2*i + 2; // правый = 2*i + 2

        // Если левый дочерний элемент больше корня
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // Если правый дочерний элемент больше, чем самый большой элемент на данный момент
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // Если самый большой элемент не корень
        if (largest != i)
        {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Рекурсивно преобразуем в двоичную кучу затронутое поддерево
            heapify(arr, n, largest);
        }
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int len = scan.nextInt();
        int[] arr = new int[len];
        for(int i = 0; i < len; ++i){
            arr[i] = scan.nextInt();
        }

        Heapsort ob = new Heapsort();
        ob.sort(arr, len);

        System.out.println("\nSorted array is");
        for (int i = 0; i < len; ++i){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
}