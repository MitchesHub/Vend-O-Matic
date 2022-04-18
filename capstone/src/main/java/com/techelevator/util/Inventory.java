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

    // this method resets inventory max when program runs
    public void reStock() {
        final int slotMax = 5;
        File vendingMachineInventory = new File("src/main/resources/vendingmachine.csv");
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
    // method to dispense inventory and decrement its quantity
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
                    System.out.print("SOLD OUT! Please select another product. ");
                }

                return false;
            }
        }

        System.out.print("Product code not found! Please try again. ");

        return false;
    }
    // method to add a reaction to the customer after purchasing a product
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
    // method to print inventory to user
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
