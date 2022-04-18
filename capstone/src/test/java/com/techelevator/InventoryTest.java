package com.techelevator;

import com.techelevator.util.Inventory;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class InventoryTest {

     @Test
    public void inventoryPrintsToUser(){
        File f = new File("src/main/resources/vendingmachine.csv");
        Inventory i = new Inventory();
        i.printStock();
    }
}
