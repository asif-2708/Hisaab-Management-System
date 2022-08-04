/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hisab.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Administrator
 */
public class UserRegistrationModel {
        
        private final SimpleStringProperty fistName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty mobile;
        private final SimpleStringProperty userName;
        private final SimpleStringProperty password;
        private final SimpleStringProperty userType;

    public UserRegistrationModel(String fistName, String lastName, String mobile, String userName, String password, String userType) {
        this.fistName = new SimpleStringProperty(fistName);
        this.lastName = new SimpleStringProperty(lastName);
        this.mobile = new SimpleStringProperty(mobile);
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.userType = new SimpleStringProperty(userType);
    }

    public String getFistName() {
        return fistName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getMobile() {
        return mobile.get();
    }

    public String getUserName() {
        return userName.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getUserType() {
        return userType.get();
    }
        
        
    public void getFistName(String firstName) {
        this.fistName.set(firstName);
    }

    public void getLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void getMobile(String mobile) {
        this.mobile.set(mobile);
    }

    public void getUserName(String userName) {
        this.userName.set(userName);
    }

    public void getPassword(String password) {
        this.password.set(password);
    }

    public void getUserType(String userType) {
        this.userType.set(userType);
    }
    
}
