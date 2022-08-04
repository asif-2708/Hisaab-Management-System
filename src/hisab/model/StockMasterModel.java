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
public class StockMasterModel {
    private final SimpleIntegerProperty stockMasterId,opQty,purQty,saleQty,clQty,totalQty;
    private final SimpleStringProperty  itemName;
    private final SimpleDoubleProperty opValue,purValue,saleValue,clValue;

    public StockMasterModel(int stockMasterId,String itemName, int opQty,double opValue, int purQty, double purValue,int totalQty, int saleQty, double saleValue,int clQty, double clValue) {
        this.stockMasterId = new SimpleIntegerProperty(stockMasterId);
        this.opQty = new SimpleIntegerProperty(opQty);
        this.purQty = new SimpleIntegerProperty(purQty);
        this.saleQty = new SimpleIntegerProperty(saleQty);
        this.clQty = new SimpleIntegerProperty(clQty);
        this.totalQty = new SimpleIntegerProperty(totalQty);
        this.itemName = new SimpleStringProperty(itemName);
        this.opValue = new SimpleDoubleProperty(opValue);
        this.purValue = new SimpleDoubleProperty(purValue);
        this.saleValue = new SimpleDoubleProperty(saleValue);
        this.clValue = new SimpleDoubleProperty(clValue);
    }

    public int getStockMasterId() {
        return stockMasterId.get();
    }

    public int getOpQty() {
        return opQty.get();
    }

    public int getPurQty() {
        return purQty.get();
    }

    public int getSaleQty() {
        return saleQty.get();
    }

    public int getClQty() {
        return clQty.get();
    }

    public int getTotalQty() {
        return totalQty.get();
    }

    public String getItemName() {
        return itemName.get();
    }

    public double getOpValue() {
        return opValue.get();
    }

    public double getPurValue() {
        return purValue.get();
    }

    public double getSaleValue() {
        return saleValue.get();
    }

    public double getClValue() {
        return clValue.get();
    }

    public void setStockMasterId(int stockMasterId) {
        this.stockMasterId.set( stockMasterId);
    }

    public void setOpQty(int opQty) {
        this.opQty.set(opQty);
    }

    public void setPurQty(int purQty) {
        this.purQty.set(purQty);
    }

    public void setSaleQty(int saleQty) {
        this.saleQty.set(saleQty);
    }

    public void setClQty(int clQty) {
        this.clQty.set(clQty);
    }

    public void setTotalQty(int totalQty) {
        this.totalQty.set(totalQty);
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public void setOpValue(double opValue) {
        this.opValue.set(opValue);
    }

    public void setPurValue(double purValue) {
        this.purValue.set(purValue);
    }

    public void setSaleValue(double saleValue) {
        this.saleValue.set(saleValue);
    }

    public void setClValue(double clValue) {
        this.clValue.set(clValue);
    }
    
    
    
}
