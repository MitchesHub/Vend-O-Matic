package com.techelevator.util;

import com.techelevator.VendingMachine;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Purchase {
    private int balance;
    private FileWriter log;

    public Purchase() {
        try {
            log = new FileWriter("capstone\\src\\main\\java\\com\\techelevator\\Log.txt", true);
        } catch (IOException e) {
            System.out.println("Unable to create system log!");
            exit(1);
        }

        balance = 0; // until you put in money
    }

    public void feedMoney() {
        int oldBalance = balance;
        int bill = 0;

        System.out.println("\nInsert moneys please!");

        do {
            try {
                Scanner userInput = new Scanner(System.in);
                bill = userInput.nextInt();
            } catch (InputMismatchException ignored) {}

            switch (bill) {
                case 1: balance += 100; break;
                case 2: balance += 200; break;
                case 5: balance += 500; break;
                case 10: balance += 1000; break;
                case 20: balance += 2000; break;
                default: System.out.println("Please insert 1/2/5/10/20 dollar bills!");
            }
        } while (bill < 1 || (bill > 2 && bill <5) || (bill > 5 && bill < 10) || (bill > 10 && bill < 20) || bill > 20);

        writeLog("FEED MONEY:" + VendingMachine.format(oldBalance) + VendingMachine.format(balance));
    }

    public void selectProduct(Inventory i) {
        boolean valid = false;

        i.printStock();
        System.out.println("\nSelect Product");

        do {
            Scanner userInput = new Scanner(System.in);
            String code = userInput.nextLine();
            int price = i.getPrice(code);

            if ((balance - price) >= 0) {
                if (i.dispenseStock(code)) {
                    int oldBalance = balance;
                    balance -= price;
                    valid = true;

                    System.out.println("\nItem dispensed! " + i.removeReact(code));
                    System.out.println("Product: " + i.getName(code));
                    System.out.println("Purchase cost: $" + String.format("%.2f", (price / (double) 100)));
                    writeLog(i.getName(code) + " " + code.toUpperCase() + VendingMachine.format(oldBalance) + VendingMachine.format(balance));
                }
            } else {
                System.out.println("\nInsufficient funds!");
                break;
            }
        } while (!valid);
    }

    public void dispenseChange(int dispenseAmount) {
        int quarters = dispenseAmount / 25;
        int dimes = (dispenseAmount % 25) / 10;
        int nickels = (dispenseAmount % 25 % 10) / 5;
        int oldBalance = balance;
        balance = 0;

        System.out.println("\nYour change is: " + quarters + " quarter(s), " + dimes + " dime(s), and " + nickels + " nickel(s)");
        writeLog("GIVE CHANGE:" + VendingMachine.format(oldBalance) + VendingMachine.format(balance));
    }

    public int getBalance() { return balance; }

    private void writeLog(String action) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
        LocalDateTime now = LocalDateTime.now();
        String line = dtf.format(now) + " " + action;

        try {
            log.write(line + System.lineSeparator());
            log.flush();
        } catch (IOException e) {
            System.out.println("Unable to create log!");
            exit(1);
        }
    }
}
