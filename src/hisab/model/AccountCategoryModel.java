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
public class AccountCategoryModel {
    private final SimpleIntegerProperty accountCategoryId;
    private final SimpleStringProperty accountCategoryName;

   

    public AccountCategoryModel(int accountCategoryId,String accountCategoryName) {
          this.accountCategoryId = new SimpleIntegerProperty(accountCategoryId);
        this.accountCategoryName = new SimpleStringProperty(accountCategoryName);
    }

   
    
     public String getAccountCategoryName() {
        return accountCategoryName.get();
    }

    public int getAccountCategoryId() {
        return accountCategoryId.get();
    }
    
     
    public void setAccountCategoryId(int accountCategoryId) {
        this.accountCategoryId.set(accountCategoryId);
    }
      public void setAccountCategoryName(String accountCategoryName) {
        this.accountCategoryName.set(accountCategoryName);
    }
      
      
}
   
