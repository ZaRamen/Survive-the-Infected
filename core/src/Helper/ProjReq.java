package Helper;

import java.util.ArrayList;
import java.util.Arrays;

public class ProjReq
{
    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(1, 2, 4, 5, 1));
        sort(arr, 0, arr.size() - 1);
        System.out.println(arr);
        System.out.println(recursion(10));
        System.out.println(binarySearch(arr, 0, arr.size()-1, 2)); //use binary search

        System.out.println("Congruence between iteration and recursion");
        iteration(10);
        recursion2(10);

        int[] arr2 = {1, 2, 3, 4, 1, 2};
        sort(arr2, 0, arr2.length - 1);//Sorts an array using merge sort.
        System.out.println("Sorted Array");
        print(arr2);
        recursiveArr(arr2, 3); //starting index is 3
        System.out.println("Recursive ArrayList");
        recursiveArrList(arr, 0);


    }
    //Recursively traverses an array.
    public static void recursiveArr(int[] arr, int start)
    {
        if(start > arr.length - 1 || start < 0)
        {
            return;
        }
        System.out.println(arr[start]);
        recursiveArr(arr, start + 1);
    }
    //Recursively traverses an ArrayList.
    public static  void recursiveArrList(ArrayList<Integer> arr, int start)
    {
        if(start > arr.size() - 1 || start < 0)
        {
            return;
        }
        System.out.println(arr.get(start));
        recursiveArrList(arr, start + 1);
    }
    //Demonstrates the congruence between iteration and recursion.
    public static void iteration(int num)
    {
        while (num > 0)
        {
            System.out.println(num);
            num--;
        }

    }
    public static void recursion2(int num)
    {
        if(num == 0)
        {
            return;
        }
        System.out.println(num);
        recursion2(num - 1);
    }

    public  static int recursion(int num)
    {
        if(num == 1) //two base cases
            return num;
        if(num > 100)
            return num;
        return num * recursion(num - 1);
    }
    public static void merge(int arr[], int l, int m, int r)
    {
        //Determine sizes of two subarrays to be merged.
        int n1 = m - l + 1;
        int n2 = r - m;
        //Create temporary arrays.
        int left[] = new int[n1];
        int right[] = new int[n2];
        //Copy data to temporary arrays.
        for (int i = 0; i < n1; i++)
            left[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            right[j] = arr[m + 1 + j];
        //Merge the temporary arrays in ascending order.
        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2)
        {
            if (left[i] <= right[j])
            {
                arr[k] = left[i];
                i++;
            }
            else
            {
                arr[k] = right[j];
                j++;
            }
            k++;
        }
        //Copy remaining elements of the left array if any.
        while (i < n1)
        {
            arr[k] = left[i];
            i++;
            k++;
        }
        //Copy remaining elements of the right array if any.
        while (j < n2)
        {
            arr[k] = right[j];
            j++;
            k++;
        }
    }
    public static void sort(int arr[], int l, int r)
    {
        if (l < r) {
            //Find the middle.
            int m =(l + r)/2;
            //Split the array recursively.
            sort(arr, l, m);
            sort(arr, m + 1, r);
            //Merge the sorted halves.
            merge(arr, l, m, r);
        }
    }
    public static void print(int arr[])
    {
        for (int i : arr)
            System.out.print(i + " ");
        System.out.println();
    }
    public static void merge(ArrayList arr, int l, int m, int r) //sorts arraylist with mergesort
    {
        //Determine sizes of two subarrays to be merged.
        int n1 = m - l + 1;
        int n2 = r - m;
        //Create temporary arrays.
        int left[] = new int[n1];
        int right[] = new int[n2];
        //Copy data to temporary arrays.
        for (int i = 0; i < n1; i++)
            left[i] = (int) arr.get(l + i);
        for (int j = 0; j < n2; ++j)
            right[j] = (int) arr.get(m + 1 + j);
        //Merge the temporary arrays in ascending order.
        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2)
        {
            if (left[i] <= right[j])
            {
                arr.set(k, left[i]);
                i++;
            }
            else
            {
                arr.set(k, right[j]);
                j++;
            }
            k++;
        }
        //Copy remaining elements of the left array if any.
        while (i < n1)
        {
            arr.set(k, left[i]);
            i++;
            k++;
        }
        //Copy remaining elements of the right array if any.
        while (j < n2)
        {
            arr.set(k, right[j]);
            j++;
            k++;
        }
    }
    public static void sort(ArrayList<Integer> arr, int l, int r)
    {
        if (l < r) {
            //Find the middle.
            int m =(l + r)/2;
            //Split the array recursively.
            sort(arr, l, m);
            sort(arr, m + 1, r);
            //Merge the sorted halves.
            merge(arr, l, m, r);
        }
    }
    public static int binarySearch(ArrayList<Integer> myList, int low,
                                   int high, int target)
    {
        int mid = (high + low) / 2;
        if (target < myList.get(mid))
        {
            return binarySearch(myList, low, mid - 1, target);
        }
        else if (target > myList.get(mid))
        {
            return binarySearch(myList, mid + 1, high, target);
        }
        else if (myList.get(mid).equals(target))
        {
            return mid;
        }
        return -1;
    }
}
