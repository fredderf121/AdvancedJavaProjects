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
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.Scanner;
import java.util.Set;
import java.io.FileWriter;
import java.io.FileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 *
 * @author Emperor Master Chen
 */
class Pet {

    public enum PetType {
        DOG,
        CAT,
        BIRD,
        FISH,
        REPTILE,
        HORSE;

    }

    PetType type;
    String name;
    double mass;
    double cost;

    public String getName() {
        return name;
    }

    public double getMass() {
        return mass;
    }

    public double getCost() {
        return cost;
    }

    Pet(String type, String name, double mass, double cost) {

        this.mass = mass;
        this.cost = cost;
        this.name = name;
        this.type = PetType.valueOf(type);
    }

    Pet() {
    }

    public void setType(String str) {
        this.type = PetType.valueOf(str);
    }
}

public class BonusEverything {

    public static int partition(ArrayList<Pet> list, int min, int max, int ref) {
        double center;
        if (ref == 2) {
             center = list.get(max).cost;
        } else {
             center = list.get(max).mass;
        }
        int i = (min - 1);
        for (int j = min; j < max; j++) {
            if ( ref==2 && list.get(j).cost < center ) {
                i++;
                Collections.swap(list, i, j);
            }else if (ref == 3 && list.get(j).mass < center) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, max);

        return i + 1;
    }

    public static ArrayList<Pet> quickSort(ArrayList<Pet> list, int low, int high, int ref) {

        if (low < high) {

            int pi = partition(list, low, high, ref);

            quickSort(list, low, pi - 1, ref);
            quickSort(list, pi + 1, high, ref);
        }
        return list;
    }

    private static void WriteToFile(ArrayList<Pet> list, String path) throws IOException {
        try (FileWriter file = new FileWriter(path)) {
            for (Pet pet : list) {
                file.append(String.join(",", pet.type.toString(), pet.name, Double.toString(pet.cost), Double.toString(pet.mass)));
                file.append("\n");
            }
            file.flush();

        }

    }

    private static double validateInput() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                double temp = Double.parseDouble(sc.next());
                return temp;
            } catch (IllegalArgumentException e) {
                System.out.println("Please enter in a number!");

            }
        }
    }

    private static ArrayList<Pet> readFile(String path) throws FileNotFoundException, IOException {
        ArrayList<Pet> pets = new ArrayList<>();
        try (BufferedReader txtReader = new BufferedReader(new FileReader(path))) {
            String line = txtReader.readLine();

            while (line != null) {
                String row = line;
                String[] data = row.split(",");

                Pet pet = new Pet(data[0], data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3]));

                pets.add(pet);
                line = txtReader.readLine();

            }
        }

        return pets;
    }

    private static void displayResults(ArrayList<Pet> list, String search, int searchBy) {
        System.out.printf("%-10s %-10s %-10s %-10s%n%n", "Species", "Name", "Cost", "Weight");
        ArrayList<Pet> newNames = list;
        
        newNames.removeIf(e -> !(e.type.toString().equals(search)));
        List<Pet> finalList = null;
        switch (searchBy) {
            case 1:
                finalList = new ArrayList<>(newNames.stream()
                        .sorted(Comparator.comparing(Pet::getName))
                        .collect(Collectors.toList()));
                break;
            case 2:
                finalList = quickSort(newNames, 0, newNames.size() - 1, 2);
                break;
            case 3:
                finalList = quickSort(newNames, 0, newNames.size() - 1, 2);
                break;

        }

        newNames.forEach((Pet i) -> {
            System.out.printf(
                    "%-10s %-10s %-10.2f %-10.3fkg%n", i.type.toString(), i.name, i.cost, i.mass);
        });
    }

    public static void run() throws IOException {

        ArrayList<Pet> names = readFile("pets.txt");
     

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
                    try {
                        Pet pet = new Pet();
                        pet.setType(sc.next().toUpperCase());
                        System.out.println("Please enter in the name of this animal");
                        pet.name = sc.next();
                        System.out.println("Please enter in the weight of this animal");
                        pet.mass = validateInput();
                        System.out.println("Please enter in the cost of this animal");
                        pet.cost = validateInput();
                        names.add(pet);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Not a valid animal name!");
                    }
                    WriteToFile(names, "pets.txt");

                    break;
                case "all":
                    System.out.printf("%-10s %-10s %-10s %-10s%n%n", "Species", "Name", "Cost", "Weight");
                    names.forEach((Pet i) -> {
                        System.out.printf(
                                "%-10s %-10s $%-10.2f %-10.3fkg%n", i.type.toString(), i.name, i.cost, i.mass);
                    });
                    break;

                case "view":
                    System.out.println("Which species would you like to view?");
                    String search = sc.next();
                    if (Arrays.asList(Arrays.stream(Pet.PetType.values()).map(Enum::name).toArray(String[]::new)).contains(search)) {
                        System.out.println("Would you like to sort by name, cost, or mass? Enter 1, 2, or 3 respectively");
                        try {
                            Integer sortBy = sc.nextInt();
                            if (sortBy >= 1 && sortBy <= 3) {
                                displayResults(names, search, sortBy);
                            } else {
                                throw new IllegalArgumentException();
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Not a valid response!");
                        }

                    } else {
                        System.out.println("Not a valid response!");
                        
                    }break;

                case "exit":
                    break loop;

            }

        }
    }

    /**
     *
     * @author Emperor Master Chen
     */
}
