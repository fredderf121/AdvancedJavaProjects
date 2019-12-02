/*
 * Pet tracker program for a pet store
 * 
 */
package advancedjava;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.FileWriter;
import java.io.FileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 *
 * @author Fred Chen
 */
class Pet {//Contains a pet species/type, name, weight, and cost

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
        this.type = PetType.valueOf(str);//from str (user input) to enum
    }
}

public class BonusEverything {

    public static int partition(ArrayList<Pet> list, int min, int max, int ref) {//Exact same quicksort algorithm, however is tailored to if the user wants to sort between cost or weight
        double center;
        if (ref == 2) {
            center = list.get(max).cost;//Sort by cost
        } else {
            center = list.get(max).mass;//Sort by weight
        }
        int i = (min - 1);
        for (int j = min; j < max; j++) {
            if (ref == 2 && list.get(j).cost < center) {
                i++;
                Collections.swap(list, i, j);
            } else if (ref == 3 && list.get(j).mass < center) {
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
        //One line per pet, in this format:
        //SPECIES, name, cost, mass
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
        //Creates list of object pet from file
        ArrayList<Pet> pets = new ArrayList<>();
        File f = new File("files/pets.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        FileReader file = new FileReader(f);

        try (BufferedReader txtReader = new BufferedReader(file)) {
            String line = txtReader.readLine();

            while (line != null) {
                String row = line;
                String[] data = row.split(",");

                Pet pet = new Pet(data[0], data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3]));

                pets.add(pet);
                line = txtReader.readLine();

            }
        } catch (FileNotFoundException e) {

        }

        return pets;
    }

    private static void displayResults(ArrayList<Pet> list, String search, int searchBy) {
        //Prints and sorts the results
        System.out.printf("%-10s %-10s %-10s %-10s%n%n", "Species", "Name", "Cost", "Weight");
        ArrayList<Pet> newNames = list;

        newNames.removeIf(e -> !(e.type.toString().equals(search)));//creates a new list where only one pet species is contained
        List<Pet> finalList = null;
        switch (searchBy) {//1 is sort by name, 2 is by cost, 3 is by weight
            case 1:
                finalList = new ArrayList<>(newNames.stream()
                        .sorted(Comparator.comparing(Pet::getName))
                        .collect(Collectors.toList()));//Comparators compare each name alphabetically and sorts the list
                break;
            case 2:
                finalList = quickSort(newNames, 0, newNames.size() - 1, 2);//quicksort algorithm
                break;
            case 3:
                finalList = quickSort(newNames, 0, newNames.size() - 1, 3);
                break;

        }

        finalList.forEach((Pet i) -> {//Prints results
            System.out.printf(
                    "%-10s %-10s $%-10.2f %-10.3fkg%n", i.type.toString(), i.name, i.cost, i.mass);
        });
    }

    public static void run() throws IOException {

        System.out.println("Hi, this is a program for a pet store. One can add animals to the directory and"
                + " also sort by cost and weight");

        loop:
        while (true) {
            ArrayList<Pet> names = readFile("files/pets.txt");
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
                        pet.setType(sc.next().toUpperCase());//flexibility in the input
                        System.out.println("Please enter in the name of this animal");
                        pet.name = sc.next();
                        System.out.println("Please enter in the weight of this animal");
                        pet.mass = validateInput();
                        System.out.println("Please enter in the cost of this animal");
                        pet.cost = validateInput();
                        names.add(pet);
                        System.out.println("Pet added!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Not a valid animal name!");
                    }
                    WriteToFile(names, "files/pets.txt");

                    break;
                case "all":
                    System.out.printf("%-10s %-10s %-10s %-10s%n%n", "Species", "Name", "Cost", "Weight");
                    names.forEach((Pet i) -> {
                        System.out.printf(
                                "%-10s %-10s $%-10.2f %-10.3fkg%n", i.type.toString(), i.name, i.cost, i.mass);
                    });
                    break;

                case "view":
                    while (true) {
                        System.out.println("Which species would you like to view?");
                        String search = sc.next().toUpperCase();
                        if (Arrays.asList(Arrays.stream(Pet.PetType.values()).map(Enum::name).toArray(String[]::new)).contains(search)) {//converts the enum.values function array to a list of strings 
                            //and searches for if what the user entered is a valid animal species (just a fun one-liner)
                            System.out.println("Would you like to sort by name, cost, or mass? Enter 1, 2, or 3 respectively");
                            while (true) {
                                try {
                                    Integer sortBy = sc.nextInt();
                                    if (sortBy >= 1 && sortBy <= 3) {
                                        displayResults(names, search, sortBy);
                                        break;
                                    } else {
                                        throw new IllegalArgumentException();
                                    }
                                } catch (IllegalArgumentException | InputMismatchException e) {
                                    System.out.println("Not a valid response! Enter in 1, 2, or 3");
                                    sc.next();
                                }
                            }
                            break;
                        } else {
                            System.out.println("Not a valid response!");

                        }
                    }
                    break;

                case "exit":
                    break loop;

            }

        }
    }

}
