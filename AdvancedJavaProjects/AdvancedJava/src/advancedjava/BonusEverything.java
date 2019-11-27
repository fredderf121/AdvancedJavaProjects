/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedjava;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Emperor Master Chen
 */
public class BonusEverything  {

    public enum Pet {
        DOG(0, 0), 
        CAT(0, 0), 
        BIRD(0, 0), 
        FISH(0, 0), 
        REPTILE(0, 0), 
        HORSE(0, 0);
        
         double mass;   // in kilograms
    
     double cost; // in meters

    Pet(double mass, double cost) {
        this.mass = mass;
        this.cost = cost;
    }
    void setName(double mass, double cost){
        this.mass = mass;
        this.cost = cost;
    }

   
    }
    
    private static void WriteToFile(ArrayList<Pet> list, String path) throws IOException {
        ArrayList<String> newList = new ArrayList<String>();
        list.forEach((i) -> newList.add(i.name()));
        Path file = Paths.get(path);
        Files.write(file, newList, StandardCharsets.UTF_8);
        
    }
    private static double validateInput(){
        Scanner sc = new Scanner(System.in);
        while (true){
            try{
                double temp = Double.parseDouble(sc.next());
                return temp;
            }catch(IllegalArgumentException e){
                System.out.println("Please enter in a number!");
                
            }
        }
    }
    
    public static void run() throws IOException {
        
        File f = new File("pets.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        
        Scanner s = new Scanner(f);
        
        ArrayList<Pet> names = new ArrayList<>();
        while (s.hasNext()) {
            names.add((Pet.valueOf(s.next())));
        }
        s.close();
        
        
        
        System.out.println("Hi, this is a program for a pet store. One can add animals to the directory and"
                + " also sort by cost and weight");       
        
        loop:
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("What would you like to do?"
                    + "\nEnter in 'view' to see all of one species"
                    + "\nEnter in 'add' to add another animal to the directory"
                    + "\nEnter in 'all' to see every animal in stock "
                    + "\nEnter in exit to exit the program.");
            String choice = sc.nextLine();
            switch (choice) {
                case "add":
                    System.out.println("Please enter in the animal you would like to add:");
                    try{
                        Pet pet = Pet.valueOf(sc.next());
                        System.out.println("Please enter in the weight of this animal");
                        pet.mass = validateInput();
                    }catch(IllegalArgumentException e){
                        System.out.println("Not a valid animal name!");
                    }
                    
                   /*try {
                        names.add((Pet.valueOf(sc.next())));
                        WriteToFile(names, "fish.txt");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Not a fish in our database! Remember to write in all caps!");
                    }*/
                    break;
                case "all":
                    names.forEach((i) -> System.out.println(i.name()));
                    break;
                case "view":
                    break;
                case "exit":
                    break loop;
                default:
                    
            }
            
        }
    }
    


/**
 *
 * @author Emperor Master Chen
 */

    
}
