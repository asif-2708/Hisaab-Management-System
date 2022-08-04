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
public class ItemCategoryModel {
    private final SimpleIntegerProperty itemCategoryId;
    private final SimpleStringProperty itemCategoryName;

   

    public ItemCategoryModel(int itemCategoryId,String itemCategoryName) {
          this.itemCategoryId = new SimpleIntegerProperty(itemCategoryId);
        this.itemCategoryName = new SimpleStringProperty(itemCategoryName);
    }

   
    
     public String getItemCategoryName() {
        return itemCategoryName.get();
    }

    public int getItemCategoryId() {
        return itemCategoryId.get();
    }
    
     
    public void setItemCategoryId(int itemCategoryId) {
        this.itemCategoryId.set(itemCategoryId);
    }
      public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName.set(itemCategoryName);
    }
      
      
}
   
