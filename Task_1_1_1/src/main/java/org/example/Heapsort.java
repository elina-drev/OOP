package org.example;

public class Heapsort {
    // Сортировка исходного массива длины len методом пирамидальной сортировки
    public int[] sort(int[] mas, int len)
    {
        int[] arr = mas.clone();
        for (int i = len / 2 - 1; i >= 0; i--)
            heapify(arr, len, i);

        for (int i=len-1; i>=0; i--)
        {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }

        return arr;
    }

    // Процедура для преобразования в двоичную кучу поддерева с корневым узлом i, что является индексом в arr[]. n - размер кучи
    private void heapify(int[] arr, int n, int i)
    {
        int largest = i;
        int l = 2*i + 1;
        int r = 2*i + 2;

        if (l < n && arr[l] > arr[largest])
            largest = l;

        if (r < n && arr[r] > arr[largest])
            largest = r;

        if (largest != i)
        {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            heapify(arr, n, largest);
        }
    }
}