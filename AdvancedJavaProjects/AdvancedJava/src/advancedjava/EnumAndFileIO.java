/*
 * A program that tracks the fish you have.
 */
package advancedjava;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Fred Chen
 */
public class EnumAndFileIO {

    public enum Fish {
        SALMON,
        CARP,
        GOLDFISH,
        GUPPY,
        PIKE,
        BASS,
        PERCH,
        TROUT,
        COD;
    }
    
    private static void WriteToFile(ArrayList<Fish> list, String path) throws IOException {
        ArrayList<String> newList = new ArrayList<>();
        list.forEach((i) -> newList.add(i.name()));//convert the list of enum fish to string so that it can be written to file
        Path file = Paths.get(path);
        Files.write(file, newList, StandardCharsets.UTF_8);
        
    }
    
    public static void run() throws IOException {
        
        File f = new File("files/fish.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        
        ArrayList<Fish> names;
        try (Scanner s = new Scanner(f)) {
            names = new ArrayList<>();
            while (s.hasNext()) {
                names.add((Fish.valueOf(s.next())));
            }
        }
        
        
        
        System.out.println("Hi, this is a fishing program. It tracks all the amazing types of fish you have catched!");
        loop:
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("What would you like to do?"
                    + "\nEnter in 'list' to see all the fish you have"
                    + "\nEnter in 'add' to add another fish you've caught to your list"
                    + "\nEnter in anything else to exit the program.");
            switch (sc.nextLine()) {
                case "add":
                    System.out.println("Please enter in the fish you have caught! Please write the fish in all caps.");
                    try {
                        names.add((Fish.valueOf(sc.next())));
                        System.out.println("Fish added!\n\n");
                        WriteToFile(names, "files/fish.txt");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Not a fish in our database! Remember to write in all caps!");
                    }
                    break;
                case "list":
                    System.out.println("Here is your list\n");
                    names.forEach((i) -> System.out.println(i.name()));
                    break;
                default:
                    break loop;
            }
            
        }
    }
    
}
