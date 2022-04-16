package com.techelevator.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.System.exit;

public class Inventory {
    private final ArrayList<String> stock;
    private final HashMap<String, Integer> quantity;

    public Inventory() {
        stock = new ArrayList<>();
        quantity = new HashMap<>();
    }

    public void reStock() {
        final int slotMax = 5;
        File vendingMachineInventory = new File("capstone\\src\\main\\java\\com\\techelevator\\util\\vendingmachine.csv");
        Scanner fileReader = null;

        try {
            fileReader = new Scanner(vendingMachineInventory);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found: " + vendingMachineInventory.getAbsolutePath() + e.getMessage());
            exit(1);
        }

        while (fileReader.hasNextLine()){
            String lineWithItem = fileReader.nextLine();
            stock.add(lineWithItem);
        }

        for (String line: stock) {
            String[] details = line.split("\\|");

            quantity.put(details[0], slotMax);
        }
    }

    protected boolean dispenseStock(String code) {
        for (String line: stock) {
            String[] data = line.split("\\|");
            String key = data[0];

            if (key.equalsIgnoreCase(code)) {
                int q = quantity.get(key);

                if (q > 0) {
                    quantity.put(key, q - 1);

                    return true;
                } else {
                    System.out.println("SOLD OUT! Please select another.");
                    System.out.print(">>> ");
                }

                return false;
            }
        }

        System.out.println("Product code not found! Please try again.");
        System.out.print(">>> ");

        return false;
    }

    protected String removeReact(String code) {
        String react = "Om Nom Nom"; //shouldn't see this

        for (String line: stock) {
            String[] data = line.split("\\|");

            if (data[0].equalsIgnoreCase(code)) {
                switch (data[3]) {
                    case "Drink": react = "Glug Glug"; break;
                    case "Candy": react = "Munch Munch"; break;
                    case "Chip": react = "Crunch Crunch"; break;
                    case "Gum": react = "Chew Chew";
                }
            }
        }

        return react + ", Yum!";
    }

    public void printStock() {
        System.out.println("\nPlease make a selection");

        for (String line: stock) {
            String[] data = line.split("\\|"); // need to escape the pipe character
            String key = data[0];
            int value = quantity.get(key);

            System.out.print(key + ". " + data[1] + " - $" + data[2] + " (");
            System.out.print((value > 0) ? value + " Available" : "SOLD OUT");
            System.out.print(")\n");
        }
    }

    public ArrayList<String> getStock() {
        return stock;
    }

    public String getName(String code) {
        for (String line: stock) {
            String[] data = line.split("\\|");

            if (data[0].equalsIgnoreCase(code)) return data[1];
        }

        return null;
    }

    public int getPrice(String code) {
        for (String line: stock) {
            String[] data = line.split("\\|");

            if (data[0].equalsIgnoreCase(code)) {
                double price = Double.parseDouble(data[2]); // make a double

                return (int) (price * 100); // convert to int
            }
        }

        return 0;
    }
}
