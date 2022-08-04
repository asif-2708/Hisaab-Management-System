/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hisab.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Admin
 */
public class ItemSaleModel {
    
    //private final SimpleIntegerProperty itemId;
    private final SimpleStringProperty itemName;
    private final SimpleDoubleProperty itemPrice;
    private final SimpleStringProperty itemPer;
    private final SimpleIntegerProperty itemQty;
    private final SimpleDoubleProperty itemAmt;
    private final SimpleDoubleProperty itemTaxAmt;
    private final SimpleDoubleProperty itemNetAmt;

    public ItemSaleModel(String itemName, Double itemPrice, String itemPer, int itemQty, Double itemAmt, Double itemTaxAmt, Double itemNetAmt) {
        
        this.itemName = new SimpleStringProperty(itemName);
        this.itemPrice =new SimpleDoubleProperty(itemPrice);
        this.itemPer = new SimpleStringProperty(itemPer);
        this.itemQty = new SimpleIntegerProperty(itemQty);
        this.itemAmt = new SimpleDoubleProperty(itemAmt);
        this.itemTaxAmt = new SimpleDoubleProperty(itemTaxAmt);
        this.itemNetAmt = new SimpleDoubleProperty(itemNetAmt);
    }

    
    public String getItemName() {
        return itemName.get();
    }

    public Double getItemPrice() {
        return itemPrice.get();
    }

    public String getItemPer() {
        return itemPer.get();
    }

    public int getItemQty() {
        return itemQty.get();
    }

    public Double getItemAmt() {
        return itemAmt.get();
    }

    public Double getItemTaxAmt() {
        return itemTaxAmt.get();
    }

    public Double getItemNetAmt() {
        return itemNetAmt.get();
    }
    
   
    
    

    public void getItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public void getItemPrice(double itemPrice) {
        this.itemPrice.set(itemPrice);
    }

    public void getItemPer(String itemPer) {
        this.itemPer.set(itemPer);
    }

    public void getItemQty(int itemQty) {
        this.itemQty.set(itemQty);
    }

    public void getItemAmt(double itemAmt) {
        this.itemAmt.set(itemAmt);
    }

    public void getItemTaxAmt(double itemTaxAmt) {
        this.itemTaxAmt.set(itemTaxAmt);
    }

    public void getItemNetAmt(double itemNetAmt) {
        this.itemNetAmt.set(itemNetAmt);
    }
    
    
}
