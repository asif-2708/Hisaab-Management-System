/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hisab.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author USER
 */
public class ItemUnitModel {
    private final SimpleIntegerProperty itemUnitId;
    private final SimpleStringProperty itemUnitName;
    private final SimpleIntegerProperty itemUnitQty;
   

    public ItemUnitModel(int itemUnitId,String itemUnitName,int itemUnitQty) {
         this.itemUnitId = new SimpleIntegerProperty(itemUnitId);
         this.itemUnitName = new SimpleStringProperty(itemUnitName);
         this.itemUnitQty = new SimpleIntegerProperty(itemUnitQty);
    }

   
    
     public String getItemUnitName() {
        return itemUnitName.get();
    }

    public int getItemUnitId() {
        return itemUnitId.get();
    }
    
    public int getItemUnitQty() {
        return itemUnitQty.get();
    }
    
     
    public void setItemUnitId(int itemUnitId) {
        this.itemUnitId.set(itemUnitId);
    }
      public void setItemUnitName(String itemUnitName) {
        this.itemUnitName.set(itemUnitName);
    }
    
    public void setItemUnitQty(int itemUnitQty) {
        this.itemUnitQty.set(itemUnitQty);
    }
      
}
   
