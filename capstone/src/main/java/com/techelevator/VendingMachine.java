package com.techelevator;

import com.techelevator.util.Inventory;
import com.techelevator.util.Purchase;

import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.exit;

public class VendingMachine {
    private final Inventory i;
    private Purchase p;

    private VendingMachine() {
        i = new Inventory();
        i.reStock();
    }

    private void mainMenu() {
        Scanner userInput;
        int choice = 0;

        System.out.println("\n------- Main Menu ------- \n");
        System.out.println("(1) Display Vending Machine Items");
        System.out.println("(2) Purchase");
        System.out.println("(3) Exit\n");

        do {
            try {
                userInput = new Scanner(System.in);
                choice = userInput.nextInt();
            } catch (InputMismatchException ignored) {}

            if (choice < 1 || choice > 4) System.out.println("Please enter a choice between 1 and 3!");
        } while (choice < 1 || choice > 4);

        switch (choice) {
            case 1:
                i.printStock();
                mainMenu();
                break;
            case 2:
                p = new Purchase();
                purchaseMenu();
                break;
            case 3:
                System.out.println("Thanks for using me!");
                exit(0); // donezo
                break;
            case 4: writeSalesReport(); // FINISH THIS
        }
    }

    private void purchaseMenu() {
        Scanner userInput;
        int choice = 0;

        System.out.println("\n------- Purchase Menu ------- \n");
        System.out.println("(1) Feed Moneys");
        System.out.println("(2) Select Product");
        System.out.println("(3) Finish Transaction\n");

        do {
            try {
                userInput = new Scanner(System.in);
                choice = userInput.nextInt();
            } catch (InputMismatchException ignored) {}

            if (choice < 1 || choice > 3) System.out.println("Please enter a choice between 1 and 3!");
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1:
                p.feedMoney();
                break;
            case 2:
                p.selectProduct(i);
                break;
            case 3:
                p.dispenseChange(p.getBalance());
                mainMenu();
        }

        System.out.println("\nCurrent balance:" + VendingMachine.format(p.getBalance()));

        purchaseMenu();
    }

    private void writeSalesReport() {
        // fill this in
    }

    public static String format(int value) {
        return " $" + String.format("%.2f", (double) value / 100);
    }

    public static void main(String[] args) {
        VendingMachine vm = new VendingMachine();
        vm.mainMenu();
    }
}
