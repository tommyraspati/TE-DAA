import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class peekSort {
    public static void main(String[] args) {
        int[][] arrayOfArrays = {{5, 3, 8, 2, 1, 9, 4, 7, 6}, {1,2,3,4,5,6,7,8,9}, {9,8,7,6,5,4,3,2,1},
                                    {17,2,9,140,1,0,6,9,22,52,69,29}};
        for (int[] arr : arrayOfArrays) {
                int l = 0; int r = arr.length - 1;
                System.out.print("Before: ");
                System.out.println(Arrays.toString(arr));        
                PeekSort(arr, l, r); 
                System.out.print("After: "); 
                System.out.println(Arrays.toString(arr));        
            }
        
        
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
                PeekSort(arr, 0, arr.length - 1);
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                
                System.out.println("Execution time in milliseconds: " + elapsedTime);

                Runtime runtime = Runtime.getRuntime();
                long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
                PeekSort(arr, 0, arr.length - 1);
                long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
                long memoryUsed = memoryAfter - memoryBefore;
                System.out.println("Memory used in bytes: " + memoryUsed);           
            }
            System.out.println("----------------------------------------");
        }
    }
    public static void PeekSort(final int[] a, final int l, final int r) {
  
        int n = r - l + 1;
        PeekSort(a, l, r, l, r, new int[n]);
      
       }
    public static void PeekSort(int[] arr, int l, int r, int e, int s, int[] B) {
        if (e == r || s == l) {
            return;
        }
        int m = l + (r - l) / 2;
        if (m <= e) {
            PeekSort(arr, e + 1, r, e + 1, s,B);
            merge(arr, l, e+1, r,B);
        } else if (m >= s) {
            PeekSort(arr, l, s - 1, e, s - 1,B);
            merge(arr, l, s , r,B);
        } else {
			final int i, j;
            if (arr[m] <= arr[m+1]) {
				i = ExtendRunLeft(arr, m, e + 1);
				j = m+1 == s ? m : ExtendRunRight(arr, m+1, s - 1);
			} else {
				i = ExtendRunLeft(arr, m, e + 1);
				j = m+1 == s ? m : ExtendRunRight(arr, m+1,s - 1);
				reverseRange(arr, i, j);
			}
            if (i == l && j == r) {
                return;
            }
            if (m - i < j - m) {
                PeekSort(arr, l, i - 1, e, i - 1,B);
                PeekSort(arr, i, r, j, s,B);
                merge(arr, l, i, r,B);
            } else {
                PeekSort(arr, l, j, e, i,B);
                PeekSort(arr, j + 1, r, j + 1, s,B);
                merge(arr, l, j+1, r,B);
            }
        }
    }

    public static int ExtendRunLeft(int[] arr, int m, int l) {
        int i = m;
        while (i > l && arr[i - 1] <= arr[i]) {
            i -= 1;
        }
        return i;
    }

    public static int ExtendRunRight(int[] arr, int m, int r) {
        int i = m;
        while (i < r && arr[i] <= arr[i + 1]) {
            i += 1;
        }
        return i;
    }

    public static void reverseRange(int[] a, int lo, int hi) {
		while (lo < hi) {
			int t = a[lo]; a[lo++] = a[hi]; 
            a[hi--] = t;
		}
	}
    public static void merge(int[] A, int l, int m, int r, int[] B) {
		--m;// 
		int i, j;
		assert B.length >= r+1;
		for (i = m+1; i > l; --i) B[i-1] = A[i-1];
		for (j = m; j < r; ++j) B[r+m-j] = A[j+1];
		for (int k = l; k <= r; ++k)
			A[k] = B[j] < B[i] ? B[j--] : B[i++];
	}
}


