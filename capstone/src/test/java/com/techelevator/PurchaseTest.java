package com.techelevator;

import com.techelevator.util.Purchase;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class PurchaseTest {

    @Test
    public void purchasesWritesToCorrectFile(){
        File vm = new File("src/main/resources/vendingmachine.csv");
        String line = " ";
        try(FileReader fr = new FileReader(vm)){
            BufferedReader br = new BufferedReader(fr);
            while((line = br.readLine()) != null){
                break;
            }
        }catch(Exception e){

        }
        String result = "A1|Potato Crisps|3.05|Chip";
        Assert.assertEquals(result, line);
    }

    @Test
    public void balanceIsUpdatedWhenCustomerInputsMoney(){
        String input = "5";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Purchase p = new Purchase();
        p.feedMoney();
        Assert.assertEquals(5, p.getBalance() / 100);
    }

}
