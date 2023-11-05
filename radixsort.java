import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// code for radixsort func is from geekforgeeks.org
public class radixsort {
    // A utility function to get maximum value in arr[]
    static int getMax(int arr[], int n)
    {
        int mx = arr[0];
        for (int i = 1; i < n; i++)
            if (arr[i] > mx)
                mx = arr[i];
        return mx;
    }
 
    // A function to do counting sort of arr[] according to
    // the digit represented by exp.
    static void CountSort(int arr[], int n, int exp)
    {
        int output[] = new int[n]; // output array
        int i;
        int count[] = new int[10];
        Arrays.fill(count, 0);
 
        // Store count of occurrences in count[]
        for (i = 0; i < n; i++)
            count[(arr[i] / exp) % 10]++;
 
        // Change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];
 
        // Build the output array
        for (i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }
 
        // Copy the output array to arr[], so that arr[] now
        // contains sorted numbers according to current
        // digit
        for (i = 0; i < n; i++)
            arr[i] = output[i];
    }
 
    // The main function to that sorts arr[] of
    // size n using Radix Sort
    static void RadixSort(int arr[], int n)
    {
        // Find the maximum number to know number of digits
        int m = getMax(arr, n);
 
        // Do counting sort for every digit. Note that
        // instead of passing digit number, exp is passed.
        // exp is 10^i where i is current digit number
        for (int exp = 1; m / exp > 0; exp *= 10)
            CountSort(arr, n, exp);
    }
 
    // A utility function to print an array
    static void print(int arr[], int n)
    {
        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
    }
 
    // Main driver method
    public static void main(String[] args)
    {
        int arr1[] = { 170, 45, 75, 90, 802, 24, 2, 66 };
        int n = arr1.length;
 
        // Function Call
        RadixSort(arr1, n);
        System.out.println(Arrays.toString(arr1));

        int[] sizes = {1000, 10000, 100000};
            for (int size : sizes) {
                for (String arrayType : new String[]{"random", "sorted", "reverse sorted"}) {
                    List<Integer> list;
                    if (arrayType.equals("random")) {
                        list = new ArrayList<>();
                        Random random = new Random();
                        for (int i = 0; i < size; i++) {
                            list.add(random.nextInt(size));
                        }
                    } else if (arrayType.equals("sorted")) {
                        list = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            list.add(i);
                        }
                    } else {
                        list = new ArrayList<>();
                        for (int i = size; i > 0; i--) {
                            list.add(i);
                        }
                        
                    }
                    int[] arr = list.stream().mapToInt(Integer::intValue).toArray();
                System.out.println("Type: "+arrayType + ", Size : " + size);
                long startTime = System.currentTimeMillis();
                RadixSort(arr, arr.length);
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                System.out.println("Execution time in milliseconds: " + elapsedTime);

                Runtime runtime = Runtime.getRuntime();
                long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
                RadixSort(arr,  arr.length);
                long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
                long memoryUsed = memoryAfter - memoryBefore;
                System.out.println("Memory used in bytes: " + memoryUsed);

            }
            System.out.println("----------------------------------------");
        }
    }
}
