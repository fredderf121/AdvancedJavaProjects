/*
 *Quicksort algorithm with arraylists and recursion
 */
package advancedjava;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Fred Chen
 */
public class SortingAndRecursion {

    public static int partition(ArrayList<Integer> list, int min, int max) {
        //We choose the last number as the partition point and we rearrange the list so that
        //those less than it are placed left and those greater are placed right of it
        int center = list.get(max);
        int i = (min - 1);
        for (int j = min; j < max; j++) {
            if (list.get(j) < center) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, max);

        return i + 1;//the location of the pivot point in the sorted array
    }

    public static ArrayList<Integer> quickSort(ArrayList<Integer> list, int low, int high) {

        if (low < high) {

            int pi = partition(list, low, high);//find the location of the pivot point in the final array

            quickSort(list, low, pi - 1);//quicksort the lower half
            quickSort(list, pi + 1, high);//quicksort the upper half (both will recursively sort the smaller and smaller groups)
        }
        return list;//The list is sorted!
    }

    /**
     */
    public static void run() {
        ArrayList<Integer> numList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("This is a program that sorts integers");
        while (true) {
            System.out.println("Please enter in an integer, 'exit' to sort the array");
            String num = sc.nextLine();
            try {
                numList.add(Integer.parseInt(num));

            } catch (NumberFormatException e) {
                if (num.equals("exit")) {
                    break;

                } else {
                    System.out.println("Please enter a valid number!");
                }
            }
        }
        System.out.println("The sorted list is as follows");
        quickSort(numList, 0, numList.size() - 1).forEach(//Lambda to print out sorted list on same line as quicksort being called
                (i) -> {
                    System.out.println(i);
                }
        );
    }

}
