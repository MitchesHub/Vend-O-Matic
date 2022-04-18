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
    private long balance; // use long in case the user is wealthy and over-feeds machine!
    private FileWriter log;

    // method to handles writing new purchases to the log file and initializing user balance
    public Purchase() {
        try {
            log = new FileWriter("src/main/resources/Log.txt", true);
        } catch (IOException e) {
            System.out.println("Unable to create system log!");
            exit(1);
        }

        balance = 0; // until you put in money, anyway
    }

    // method to handle accepting user money input
    public void feedMoney() {
        long oldBalance = balance;
        int bill = 0;

        System.out.print("\nFeed me some moneys, please! ");


        do {
            try {
                Scanner userInput = new Scanner(System.in);
                bill = userInput.nextInt();
            } catch (InputMismatchException ignored) {}

            switch (bill) {
                case 1: balance += 100;
                    Sounds.playSoundCurrencyAccepted();
                    break;
                case 2: balance += 200;
                    Sounds.playSoundCurrencyAccepted();
                    break;
                case 5: balance += 500;
                    Sounds.playSoundCurrencyAccepted();
                    break;
                case 10: balance += 1000;
                    Sounds.playSoundCurrencyAccepted();
                    break;
                case 20: balance += 2000;
                    Sounds.playSoundCurrencyAccepted();
                    break;
                default:
                    Sounds.playSoundError();
                    System.out.print("Please feed me only 1/2/5/10/20 dollars! ");
            }
        } while (bill < 1 || (bill > 2 && bill <5) || (bill > 5 && bill < 10) || (bill > 10 && bill < 20) || bill > 20);

        writeLog("FEED MONEY:" + VendingMachine.format(oldBalance) + VendingMachine.format(balance));
    }

    // method to handle user product selection. Will dispense product, subtract price from customer balance and decrement product quantity
    public void selectProduct(Inventory i) {
        boolean valid = false;

        i.printStock();
        System.out.print("\nSelect Product: ");

        do {
            Scanner userInput = new Scanner(System.in);
            String code = userInput.nextLine();
            int price = i.getPrice(code);

            if ((balance - price) >= 0) {
                if (i.dispenseStock(code)) {
                    long oldBalance = balance;
                    balance -= price;
                    valid = true;

                    Sounds.playSoundDispense();
                    System.out.println("\nItem dispensed! " + i.removeReact(code));
                    System.out.println("Product: " + i.getName(code));
                    System.out.println("Purchase cost: $" + String.format("%.2f", (price / (double) 100)));
                    writeLog(i.getName(code) + " " + code.toUpperCase() + VendingMachine.format(oldBalance) + VendingMachine.format(balance));
                }
            } else {
                Sounds.playSoundError();
                System.out.println("\nInsufficient funds!");
                break; // exit loop early
            }
        } while (!valid);
    }

    // method to return change to customer in the form of coins
    public void dispenseChange(long amount) {
        int quarters = (int) (amount / 25);
        int dimes = (int) ((amount % 25) / 10);
        int nickels = (int) ((amount % 25 % 10) / 5);
        long oldBalance = balance;
        balance = 0;

        System.out.println("\nYour change is: " + quarters + " quarter(s), " + dimes + " dime(s), and " + nickels + " nickel(s)");
        writeLog("GIVE CHANGE:" + VendingMachine.format(oldBalance) + VendingMachine.format(balance));
    }

    public long getBalance() { return balance; }

    // method to create a purchase log
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
