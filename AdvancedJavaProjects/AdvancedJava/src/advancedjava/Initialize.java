/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava;
import java.io.IOException;
import java.util.Scanner;
/**
 *
 * @author Emperor Master Chen
 */
public class Initialize {
    
    public static void main(String[] args) throws IOException{
        System.out.println("Enter 1 for FileIO and Searching, 2 for Sorting and Recursion, and 3 for Enum and FileIO");
        System.out.println("There is no error handling for only this class as it serves only as a directory to the actual"
                + " files and is not being marked");
        Scanner sc = new Scanner(System.in);
        switch(sc.nextInt()){
            case 1:
                FileIOandLinearSearching.run();
                break;
            case 2:
                SortingAndRecursion.run();
                break;
            case 3:
                EnumAndFileIO.run();
                break;
            
        }
                
        
    }
}
