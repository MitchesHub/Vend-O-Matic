package com.techelevator;

import com.techelevator.util.Inventory;
import com.techelevator.util.Purchase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import com.techelevator.util.Sounds;
import java.lang.Thread;


import static java.lang.System.exit;

public class VendingMachine extends Thread{
    private final Inventory i;
    private Purchase p;

    public VendingMachine() {
        i = new Inventory();
        i.reStock();

        System.out.println("                        __.|.__");
        System.out.println("                    .n887.d8`qb`\"-.                     888");
        System.out.println("                  .d88' .888  q8b. `.                   888");
        System.out.println("                 d8P'  .8888   .88b. \\              888 888");
        System.out.println("                d88_._ d8888_.._9888 _\\             888 888");
        System.out.println("                  '   '   888   '   '               888 888");
        System.out.println("                          888                       888 888");
        System.out.println("                          888                       888 888");
        System.out.println("888     888 88888b.d88b.  88888b.  888d888  .d88b.  888 888  8888b.");
        System.out.println("888     888 888 \"888 \"88b 888 \"88b 888P\"   d8P  Y8b 888 888     \"88b");
        System.out.println("888     888 888  888  888 888  888 888     88888888 888 888 .d888888");
        System.out.println("Y88b. .d88P 888  888  888 888 d88P 888     Y8b.     888 888 888  888");
        System.out.println(" \"Y88888P\"  888  888  888 88888P\"  888      \"Y8888  888 888 \"Y888888");
        System.out.println("************************** Vendo-Matic 800 *************************");
    }

    public void mainMenu() throws InterruptedException{
        Scanner userInput;
        int choice = 0;

        System.out.println("\n------- Main Menu ------- \n");
        System.out.println("(1) Display Vending Machine Items");
        System.out.println("(2) Purchase");
        System.out.println("(3) Exit\n");

        System.out.print("Enter menu selection: ");

        do {
            try {
                userInput = new Scanner(System.in);
                choice = userInput.nextInt();
            } catch (InputMismatchException ignored) {}

            if (choice < 1 || choice > 4) {
                Sounds.playSoundError();
                System.out.print("Please enter a choice between 1 and 3! ");
            }
        } while (choice < 1 || choice > 4);

        switch (choice) {
            case 1:
                Sounds.playSoundBeep();
                i.printStock();
                mainMenu();
                break;
            case 2:
                Sounds.playSoundBeep();
                p = new Purchase();
                purchaseMenu();
                break;
            case 3:
                Sounds.playSoundClose();
                System.out.println("Thanks for using me!");
                Thread.sleep(1500);  // wait 1.5 seconds before closing to allow sound to play
                exit(0); // donezo
                break;
            case 4: //secret option 4 to create sales report
                createReport();
                mainMenu();
        }
    }

    public void purchaseMenu() throws InterruptedException{
        Scanner userInput;
        int choice = 0;

        System.out.println("\n------- Purchase Menu ------- \n");
        System.out.println("(1) Feed Moneys");
        System.out.println("(2) Select Product");
        System.out.println("(3) Finish Transaction\n");

        System.out.println("Current balance:" + format(p.getBalance()) + "\n");

        System.out.print("\nEnter menu selection: ");

        do {
            try {
                userInput = new Scanner(System.in);
                choice = userInput.nextInt();
            } catch (InputMismatchException ignored) {}

            if (choice < 1 || choice > 3) {
                Sounds.playSoundError();
                System.out.print("Please enter a choice between 1 and 3! ");
            }
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1:
                Sounds.playSoundBeep();
                p.feedMoney();
                break;
            case 2:
                Sounds.playSoundBeep();
                p.selectProduct(i);
                break;
            case 3:
                Sounds.playSoundChangeReturn();
                p.dispenseChange(p.getBalance());
                mainMenu();
        }

        System.out.println("\nCurrent balance:" + format(p.getBalance()));

        purchaseMenu();
    }

    // method to create a sales report when hidden menu selection is accessed
    public void createReport() {
        Scanner fileInput = null;

        try {
            fileInput = new Scanner(new File("src/main/resources/Log.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("\nLog file not found for reading! Perhaps this machine is new?");
            exit(1);
        }

        FileWriter report = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yy_HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();

        try { // noinspection resource
            report = new FileWriter("src/main/resources/Report_" + dtf.format(now) + ".txt", false);
        } catch (IOException e) {
            System.out.println("\nUnable to create sales report!");
            exit(1);
        }

        TreeMap<String, Integer> sales = new TreeMap<>(); // map order matters

        for (String line: i.getStock()) {
            String[] data = line.split("\\|"); // need to escape pipe character

            sales.put(data[0], 0);
        }

        long total = 0;

        while (fileInput.hasNextLine()) {
            String line = fileInput.nextLine();

            for (String key: sales.keySet()) {
                if (line.contains(i.getName(key))) {
                    sales.put(key, sales.get(key) + 1); // increment value associated with key

                    total += i.getPrice(key);
                }
            }
        }

        try {
            for (Map.Entry<String, Integer> m: sales.entrySet()) {
                report.write(i.getName(m.getKey()) + "|" + m.getValue() + System.lineSeparator());
            }

            report.write(System.lineSeparator() + "TOTAL SALES:" + format(total));
            report.flush();
        } catch (IOException e) {
            System.out.println("\nUnable to write to sales report!");
            exit(1);
        }

        System.out.println("\nSales report created!");
    }

    /*
     * Helper method to create a decimal out of a long, displays 2 trailing decimal values as this is money
     */
    public static String format(long value) {
        return " $" + String.format("%.2f", (double) value / 100);
    }

    public static void main(String[] args) throws InterruptedException {
        VendingMachine vm = new VendingMachine();
        Sounds.playSoundOpen();
        vm.mainMenu();
    }
}
