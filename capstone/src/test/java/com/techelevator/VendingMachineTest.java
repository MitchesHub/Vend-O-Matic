package com.techelevator;


import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class VendingMachineTest {

    @Test
    public void testCreateReport() {
        VendingMachine vm = new VendingMachine();
        vm.createReport();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yy_HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();

        // check if file created with current dateTime exists
        File f = new File("src/main/resources/Report_" + dtf.format(now) + ".txt");
        Assert.assertTrue(f.exists());
    }

}
