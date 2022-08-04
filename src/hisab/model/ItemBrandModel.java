
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
public class ItemBrandModel {
    private final SimpleIntegerProperty itemBrandId;
    private final SimpleStringProperty itemBrandName;

   

    public ItemBrandModel(int itemBrandId,String itemBrandName) {
          this.itemBrandId = new SimpleIntegerProperty(itemBrandId);
        this.itemBrandName = new SimpleStringProperty(itemBrandName);
    }

   
    
     public String getItemBrandName() {
        return itemBrandName.get();
    }

    public int getItemBrandId() {
        return itemBrandId.get();
    }
    
     
    public void setItemBrandId(int itemBrandId) {
        this.itemBrandId.set(itemBrandId);
    }
      public void setItemBrandName(String itemBrandName) {
        this.itemBrandName.set(itemBrandName);
    }
      
      
}
   
