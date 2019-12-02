/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author S332896109
 */
public class SortingAndRecursion {
    
    public static int partition(ArrayList<Integer> list, int min, int max) {
        int center = list.get(max);
        int i = (min - 1); 
        for (int j = min; j < max; j++) {           
            if (list.get(j) < center) {
                i++;       
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i+1, max);       

        return i + 1;
    }

   
    
    public static ArrayList<Integer> quickSort(ArrayList<Integer> list, int low, int high){
    
        if (low < high) {
         
            int pi = partition(list, low, high);

            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
        return list;
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
        quickSort(numList, 0, numList.size()-1).forEach(
                (i) -> {System.out.println(i);}
        );
    }

}
