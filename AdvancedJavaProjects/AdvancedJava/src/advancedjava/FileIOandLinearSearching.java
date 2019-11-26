/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.ArrayList;
// somewhere in your code

/**
 *
 * @author Emperor Master Chen
 */
public class FileIOandLinearSearching {

    private static boolean LinearSearch(ArrayList<String> list, String keyword) {
        for (String i : list) {
            if (i.equals(keyword)) {
                return true;
            }
        }
        return false;
    }

    private static void WriteToFile(ArrayList<String> list) throws IOException {
        Path file = Paths.get("file.txt");
        Files.write(file, list, StandardCharsets.UTF_8);

    }

    public static void main(String[] args) throws IOException {

        File f = new File("file.txt");
        if (!f.exists()) {
            f.createNewFile();
        }

        Scanner s = new Scanner(f);

        ArrayList<String> names = new ArrayList<>();
        while (s.hasNext()) {
            names.add(s.next());
        }
        s.close();

        Scanner input = new Scanner(System.in);
        loop:
        while (true) {
            System.out.println("Would you like to search or input for a name? 's' for search, 'i' for input, anything else for no");
            switch (input.nextLine()) {
                case ("s"):
                    System.out.println("Please enter the name you are looking for");
                    String name = input.nextLine();
                    if (LinearSearch(names, name)) {
                        System.out.println("We have found a match!");

                    } else {
                        System.out.println("No match :(");
                    }
                    break;
                case ("i"):
                    System.out.println("Please enter the name you would like to enter");
                    name = input.nextLine();
                    if (!LinearSearch(names, name)) {
                        names.add(name);
                        WriteToFile(names);
                        System.out.println("Name added!");

                    } else {
                        System.out.println("This name already exists!");
                    }
                default:
                    break loop;
            }

        }

    }

}
