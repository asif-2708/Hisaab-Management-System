/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hisab.controller;

import com.jfoenix.controls.JFXTextField;
import hisab.DatabaseHandler;
import hisab.UserHandler;
import hisab.model.ItemEntryModel;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class ItemEntryController implements Initializable {

    private int itemEntryId=0,itemBrandId=0,itemCategoryId=0,itemUnitId=0,itemTaxId=0; //indecate Id Of Selected Value from table
    private String itemBrand,itemCategory,itemUnit,itemTax;
    private String query=null;
    private ResultSet rs=null;
    
    //observable list for retrive list from database and feed this value into this list to fill comboBox 
    ObservableList<String> listCmbItemEntryBrandName=FXCollections.observableArrayList();
    ObservableList<String> listCmbItemEntryCategoryName=FXCollections.observableArrayList();
    ObservableList<String> listCmbItemEntryUnitName=FXCollections.observableArrayList();
    ObservableList<String> listCmbItemEntryTaxName=FXCollections.observableArrayList();
    //observable list which contain all the data which selected from database and fill the tableView
    ObservableList<ItemEntryModel> list=FXCollections.observableArrayList();
    @FXML
    private JFXTextField txtItemEntryName;
    @FXML
    private ComboBox<String> cmbItemEntryBrandName;
    @FXML
    private ComboBox<String> cmbItemEntryCategoryName;
    @FXML
    private ComboBox<String> cmbItemEntryUnitName;
    @FXML
    private ComboBox<String> cmbItemEntryTaxName;
    @FXML
    private JFXTextField txtItemEntryPurPrice;
    @FXML
    private JFXTextField txtItemEntrySalePrice;
    @FXML
    private JFXTextField txtItemEntryShortName;
    
    @FXML
    private Button btnSaveItemEntry;
    @FXML
    private Button btnRefreshItemEntry;
    @FXML
    private Button btnUpdateItemEntry;
    
    
    @FXML
    private MenuItem editContextMenu;
    @FXML
    private MenuItem deleteContextMenu;
    
    
    @FXML
    private TableView<ItemEntryModel> tableItemEntry;
    @FXML
    private TableColumn<ItemEntryModel, Integer> itemEntryIdColum;
    @FXML
    private TableColumn<ItemEntryModel, String> itemEntryNameColum;
    @FXML
    private TableColumn<ItemEntryModel, String> itemEntryShortNameColum;
    @FXML
    private TableColumn<ItemEntryModel, String> itemEntryBrandColum;
    @FXML
    private TableColumn<ItemEntryModel, String> itemEntryCategoryColum;
    @FXML
    private TableColumn<ItemEntryModel, String> itemEntryUnitColum;
    @FXML
    private TableColumn<ItemEntryModel, String> itemEntryTaxColum;
    @FXML
    private TableColumn<ItemEntryModel, Double> itemEntryPurPriceColum;
    @FXML
    private TableColumn<ItemEntryModel, Double> itemEntrySalePriceColum;
   
   
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            try {
            fillComboBox();   
            btnUpdateItemEntry.setVisible(false);
            tableItemEntry.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            tableItemEntry.setEditable(true);
            //tableItemEntry.getSelectionModel().cellSelectionEnabledProperty().set(true);
            updateItemEntryTable();
            
        }   catch (Exception ex) {
                Logger.getLogger(ItemEntryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    



   
    @FXML
    private void saveItemTax(ActionEvent event) throws SQLException {
        
        // code for retrive itemBrandId,itemCategory,itemUnit,itemTax from currusponding table according to which value selected by user using  combo box
        itemBrand=cmbItemEntryBrandName.getSelectionModel().getSelectedItem();
        itemCategory=cmbItemEntryCategoryName.getSelectionModel().getSelectedItem();
        itemUnit=cmbItemEntryUnitName.getSelectionModel().getSelectedItem();
        itemTax=cmbItemEntryTaxName.getSelectionModel().getSelectedItem();
        
        //select itemBrandId from database using which value user has selected from combobox 
        query="select item_brand_id from item_brand where item_brand_name='"+itemBrand+"'";
        rs=DatabaseHandler.selectData(query);
       
        while(rs.next()){
            try {
                itemBrandId=rs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(ItemEntryController.class.getName()).log(Level.SEVERE, null, ex);
            }             
        } 
        
        //select itemCategoryId from database using which value user has selected from combobox 
        query="select item_category_id from item_category where item_category_name='"+itemCategory+"'";
        rs=DatabaseHandler.selectData(query);
       
        while(rs.next()){
            try {
                itemCategoryId=rs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(ItemEntryController.class.getName()).log(Level.SEVERE, null, ex);
            }             
        } 
        
        //select itemUnitId from database using which value user has selected from combobox 
        query="select item_unit_id from item_unit where item_unit_name='"+itemUnit+"'";
        rs=DatabaseHandler.selectData(query);
       
        while(rs.next()){
            try {
                itemUnitId=rs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(ItemEntryController.class.getName()).log(Level.SEVERE, null, ex);
            }             
        } 
        
        
        //select itemTaxId from database using which value user has selected from combobox 
        query="select item_tax_id from item_tax where item_tax_name='"+itemTax+"'";
        rs=DatabaseHandler.selectData(query);
       
        while(rs.next()){
            try {
                itemTaxId=rs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(ItemEntryController.class.getName()).log(Level.SEVERE, null, ex);
            }             
        }
        
        
        //Insert Data into item_master which user has entered into fields
        query="insert into item_master values(null,'"+txtItemEntryName.getText()+"','"+txtItemEntryShortName.getText()+"',"+itemBrandId+","+itemCategoryId+","+itemUnitId+","+itemTaxId+","+Double.parseDouble(txtItemEntryPurPrice.getText())+","+Double.parseDouble(txtItemEntrySalePrice.getText())+")";
       

        if(DatabaseHandler.insertData(query)){
            updateItemEntryTable();
            UserHandler.alertInformation("saveAlert");

            //***********Insert Item into  the Stock_master ************
            //select item_master_id from item_master
            query="select item_master_id from item_master where item_name='"+txtItemEntryName.getText()+"'";
            rs=DatabaseHandler.selectData(query);
            if(rs.next()){
                query="insert into stock_master(item_master_id) values("+rs.getInt("item_master_id")+")";
                DatabaseHandler.insertData(query);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Data Not Saved! Item is Already Exist");
        }
        clearAll();
        
        
    }
    
    @FXML
    private void removeItemEntry(ActionEvent event) throws SQLException {
        ObservableList<ItemEntryModel> selectedItemList=FXCollections.observableArrayList();
        selectedItemList.addAll(tableItemEntry.getSelectionModel().getSelectedItems());
       
        Optional<ButtonType> buttonChoice=UserHandler.alertConfirmation(selectedItemList.size(),"deleteAlert");
        if(buttonChoice.get()==ButtonType.OK){
            
            for(int i=0;i<selectedItemList.size();i++){
                
                query="delete  from item_master where item_master_id="+selectedItemList.get(i).getItemEntryId()+"";
                DatabaseHandler.removeData(query);
                
            }
        }       
        updateItemEntryTable();
    }
    
    
    @FXML
    private void editItemEntry(ActionEvent event) {
        btnSaveItemEntry.setVisible(false);
        btnUpdateItemEntry.setVisible(true);
        ItemEntryModel itemEntryModel=(ItemEntryModel) tableItemEntry.getSelectionModel().getSelectedItem();
        itemEntryId=itemEntryModel.getItemEntryId();
        txtItemEntryName.setText(itemEntryModel.getItemEntryName());
        txtItemEntryShortName.setText(itemEntryModel.getItemEntryShortName());
        cmbItemEntryBrandName.getSelectionModel().select(itemEntryModel.getItemBrandName());
        cmbItemEntryCategoryName.getSelectionModel().select(itemEntryModel.getItemCategoryName());
        cmbItemEntryUnitName.getSelectionModel().select(itemEntryModel.getItemUnitName());
        cmbItemEntryTaxName.getSelectionModel().select(itemEntryModel.getItemTaxName());
        txtItemEntryPurPrice.setText(itemEntryModel.getItemPurPrice().toString());
        txtItemEntrySalePrice.setText(itemEntryModel.getItemSalePrice().toString());

    }
    
    @FXML
    private void updateItemEntry(ActionEvent event) throws SQLException {
        // code for retrive id from perticular table according to which tax system selected by user by combo box
        itemBrand=cmbItemEntryBrandName.getSelectionModel().getSelectedItem();
        itemCategory=cmbItemEntryCategoryName.getSelectionModel().getSelectedItem();
        itemUnit=cmbItemEntryUnitName.getSelectionModel().getSelectedItem();
        itemTax=cmbItemEntryTaxName.getSelectionModel().getSelectedItem();
        
        //select item_brand_Id from item_brand using which value user has selected from combobox 
        query="select item_brand_id from item_brand where item_brand_name='"+itemBrand+"'";
        rs=DatabaseHandler.selectData(query);
       
        while(rs.next()){
            try {
                itemBrandId=rs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(ItemEntryController.class.getName()).log(Level.SEVERE, null, ex);
            }             
        } 
        
        
        //select item_category_Id from item_category using which value user has selected from combobox 
        query="select item_category_id from item_category where item_category_name='"+itemCategory+"'";
        rs=DatabaseHandler.selectData(query);
       
        while(rs.next()){
            try {
                itemCategoryId=rs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(ItemEntryController.class.getName()).log(Level.SEVERE, null, ex);
            }             
        } 

        //select item_Unit_Id from item_unit using which value user has selected from combobox 
        query="select item_unit_id from item_unit where item_unit_name='"+itemUnit+"'";
        rs=DatabaseHandler.selectData(query);
       
        while(rs.next()){
            try {
                itemUnitId=rs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(ItemEntryController.class.getName()).log(Level.SEVERE, null, ex);
            }             
        } 
        
        //select item_brand_Id from item_brand using which value user has selected from combobox 
        query="select item_tax_id from item_tax where item_tax_name='"+itemTax+"'";
        rs=DatabaseHandler.selectData(query);
       
        while(rs.next()){
            try {
                itemTaxId=rs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(ItemEntryController.class.getName()).log(Level.SEVERE, null, ex);
            }             
        } 
        
        
        query="update item_master set item_name='"+txtItemEntryName.getText()+"',item_short_name='"+txtItemEntryShortName.getText()+"',item_brand_id="+itemBrandId+",item_category_id="+itemCategoryId+",item_unit_id="+itemUnitId+",item_tax_id="+itemTaxId+",pur_price="+Double.parseDouble(txtItemEntryPurPrice.getText())+",sale_price="+Double.parseDouble(txtItemEntrySalePrice.getText())+" where item_master_id="+itemEntryId+"";
        Optional<ButtonType> buttonChoice=UserHandler.alertConfirmation("updateAlert");
        if(buttonChoice.get()==ButtonType.OK){
            if(DatabaseHandler.updateData(query)){
                UserHandler.alertInformation("updateSuccessInfo");
                updateItemEntryTable();
                clearAll();
            }
            
        }
          
        
        
    }
    
    
     @FXML
    private void refreshItemEntry(ActionEvent event) {
        clearAll();
    }

    
    
    
    private void fillComboBox() throws SQLException {
        
        // Fill Observable List For cmbItemBrandName in listItemEntryBrandName
        query="select item_brand_name from item_brand";
        rs=DatabaseHandler.selectData(query);
        
        while(rs.next()){
            try {
                listCmbItemEntryBrandName.add(rs.getString(1));
                
            } catch (SQLException ex) {
                Logger.getLogger(ItemEntryController.class.getName()).log(Level.SEVERE, null, ex);
            }         
        } 
            cmbItemEntryBrandName.setItems(listCmbItemEntryBrandName);
            
        // Fill Observable List For cmbItemCategoryName in listItemEntryCategoryName
        query="select item_category_name from item_category";
        rs=DatabaseHandler.selectData(query);
        
        while(rs.next()){
            try {
                listCmbItemEntryCategoryName.add(rs.getString(1));
                
            } catch (SQLException ex) {
                Logger.getLogger(ItemEntryController.class.getName()).log(Level.SEVERE, null, ex);
            }         
        } 
            cmbItemEntryCategoryName.setItems(listCmbItemEntryCategoryName);
        
            
            
        // Fill Observable List For cmbItemUnitName in listItemEntryUnitName
        query="select item_unit_name from item_unit";
        rs=DatabaseHandler.selectData(query);
        
        while(rs.next()){
            try {
                listCmbItemEntryUnitName.add(rs.getString(1));
                
            } catch (SQLException ex) {
                Logger.getLogger(ItemEntryController.class.getName()).log(Level.SEVERE, null, ex);
            }         
        } 
            cmbItemEntryUnitName.setItems(listCmbItemEntryUnitName);
            
            
            
            
        // Fill Observable List For cmbItemTaxName in listItemEntryTaxName
        query="select item_tax_name from item_tax";
        rs=DatabaseHandler.selectData(query);
        
        while(rs.next()){
            try {
                listCmbItemEntryTaxName.add(rs.getString(1));
                
            } catch (SQLException ex) {
                Logger.getLogger(ItemEntryController.class.getName()).log(Level.SEVERE, null, ex);
            }         
        } 
            cmbItemEntryTaxName.setItems(listCmbItemEntryTaxName);    
                      
         
    }

    
    
    
    //method is used to fillUp table With fetching data from single/multiple database and fill observable list and fill table view
    private void updateItemEntryTable() throws SQLException {
        
        tableItemEntry.getItems().clear();      
        itemEntryIdColum.setCellValueFactory(new PropertyValueFactory<ItemEntryModel,Integer>("itemEntryId"));
        itemEntryNameColum.setCellValueFactory(new PropertyValueFactory<ItemEntryModel,String>("itemEntryName"));
        itemEntryShortNameColum.setCellValueFactory(new PropertyValueFactory<ItemEntryModel,String>("itemEntryShortName"));
        itemEntryBrandColum.setCellValueFactory(new PropertyValueFactory<ItemEntryModel,String>("itemBrandName"));
        itemEntryCategoryColum.setCellValueFactory(new PropertyValueFactory<ItemEntryModel,String>("itemCategoryName"));
        itemEntryUnitColum.setCellValueFactory(new PropertyValueFactory<ItemEntryModel,String>("itemUnitName"));
        itemEntryTaxColum.setCellValueFactory(new PropertyValueFactory<ItemEntryModel,String>("itemTaxName"));
        itemEntryPurPriceColum.setCellValueFactory(new PropertyValueFactory<ItemEntryModel,Double>("itemPurPrice"));
        itemEntrySalePriceColum.setCellValueFactory(new PropertyValueFactory<ItemEntryModel,Double>("itemSalePrice"));

        query="select ie.item_master_id,ie.item_name,ie.item_short_name,ib.item_brand_name,ic.item_category_name,iu.item_unit_name,it.item_tax_name,ie.pur_price,ie.sale_price from item_master ie\n" +
                "join item_brand ib on ie.item_brand_id=ib.item_brand_id\n" +
                "join item_category ic on ie.item_category_id=ic.item_category_id\n" +
                "join item_unit iu on ie.item_unit_id=iu.item_unit_id\n" +
                "join item_tax it on ie.item_tax_id=it.item_tax_id";

        rs=DatabaseHandler.selectData(query);
        while(rs.next()){
            try {
                list.add(
                        new ItemEntryModel(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getDouble(8),rs.getDouble(9))
                );
            } catch (SQLException ex) {
                Logger.getLogger(ItemEntryController.class.getName()).log(Level.SEVERE, null, ex);
            }               
        }
        
        tableItemEntry.setItems(list);
        
    }

    private void clearAll() {
        btnSaveItemEntry.setVisible(true);
        btnUpdateItemEntry.setVisible(false);
        txtItemEntryName.clear();
        txtItemEntryShortName.clear();
        cmbItemEntryBrandName.setItems(null);
        cmbItemEntryCategoryName.setItems(null);
        cmbItemEntryUnitName.setItems(null);
        cmbItemEntryTaxName.setItems(null);
        cmbItemEntryBrandName.setItems(listCmbItemEntryBrandName);
        cmbItemEntryCategoryName.setItems(listCmbItemEntryCategoryName);
        cmbItemEntryUnitName.setItems(listCmbItemEntryUnitName);
        cmbItemEntryTaxName.setItems(listCmbItemEntryTaxName);
        txtItemEntryPurPrice.clear();
        txtItemEntrySalePrice.clear();
    }

   

   
    
}
