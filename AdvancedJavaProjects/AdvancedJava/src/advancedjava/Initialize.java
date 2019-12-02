/*
Serves as the portal to each problem
 */
package advancedjava;
import java.io.IOException;
import java.util.Scanner;
/**
 *
 * @author Fred Chen
 */
public class Initialize {
    
    public static void main(String[] args) throws IOException{
        System.out.println("Enter 1 for FileIO and Searching, 2 for Sorting and Recursion, 3 for Enum and FileIO and 4 for the bonus program.");
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
            case 4:
                BonusEverything.run();
                break;
        }
                
        
    }
}
