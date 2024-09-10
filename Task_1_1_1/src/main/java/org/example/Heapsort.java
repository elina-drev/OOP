package org.example;

import java.util.Scanner;

public class Heapsort {
    // Sorting the original array of length len by the pyramid sorting method
    public void sort(int[] arr, int len)
    {
        for (int i = len / 2 - 1; i >= 0; i--) // Building a heap (rearranging the array)
            heapify(arr, len, i);

        // extracting items from the heap
        for (int i=len-1; i>=0; i--)
        {
            int temp = arr[0];// Moving the current root to the end
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);// // Calling the heapify procedure on a reduced heap
        }
    }

    // Converting to a binary heap of a subtree with root node i, which is an index in arr[].
    // n - size of heap
    public void heapify(int[] arr, int n, int i)
    {
        int largest = i; // Initialize the largest element as the root
        int l = 2*i + 1; // left
        int r = 2*i + 2; // right

        if (l < n && arr[l] > arr[largest])// If the left child element is larger than the root
            largest = l;

        if (r < n && arr[r] > arr[largest])// If the right child element is larger than the largest element at the moment
            largest = r;

        if (largest != i)// If the largest element is not the root
        {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest);// Recursively convert the affected subtree to a binary heap
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