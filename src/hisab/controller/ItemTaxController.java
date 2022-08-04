/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hisab.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import hisab.DatabaseHandler;
import hisab.UserHandler;
import hisab.model.ItemTaxModel;
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
 * @author Administrator
 */
public class ItemTaxController implements Initializable {
    
    private int taxId=0,taxSystemId=0,taxCategoryId=0,taxOnId=0; //indecate Id Of Selected Value from table
    private String taxSystem,taxCategory,taxOn;
    private String query=null;
    private ResultSet rs=null;
    private int selectedRowId;  // for Check The Id of selected Id Colum of the table
    //observable list for retrive list from database and feed this value into this list to fill comboBox 
    ObservableList<String> listCmbItemTaxSystem=FXCollections.observableArrayList();
    ObservableList<String> listCmbItemTaxCategory=FXCollections.observableArrayList();
    ObservableList<String> listCmbItemTaxOn=FXCollections.observableArrayList();
    
    //Observable list for tableView
    ObservableList<ItemTaxModel> list=FXCollections.observableArrayList();
    
    @FXML
    private JFXTextField txtItemTaxName;
    @FXML
    private JFXTextField txtItemTaxSgst;
    @FXML
    private JFXTextField txtItemTaxCgst;
    @FXML
    private JFXTextField txtItemTaxIgst;
    @FXML
    private JFXTextField txtItemTaxAddi;
    @FXML
    private ComboBox<String> cmbItemTaxSystem;
    @FXML
    private ComboBox<String> cmbItemTaxCategory;
    @FXML
    private ComboBox<String> cmbItemTaxOn;
    @FXML
    private JFXButton btnSaveItemTax;
    @FXML
    private JFXButton btnRefreshIemTax;
    @FXML
    private JFXButton btnUpdateItemTax;
    @FXML
    private TableView<ItemTaxModel> tableItemTax;
    @FXML
    private TableColumn<ItemTaxModel, Integer> itemTaxIdColum;
    @FXML
    private TableColumn<ItemTaxModel, String> itemTaxNameColum;
    @FXML
    private TableColumn<ItemTaxModel, String> itemTaxSystemColum;
    @FXML
    private TableColumn<ItemTaxModel, String> itemTaxCategoryColum;
    @FXML
    private TableColumn<ItemTaxModel, String> itemTaxOnColum;
    @FXML
    private TableColumn<ItemTaxModel, Float> itemTaxSgstColum;
    @FXML
    private TableColumn<ItemTaxModel, Float> itemTaxCgstColum;
    @FXML
    private TableColumn<ItemTaxModel, Float> itemTaxIgstColum;
    @FXML
    private TableColumn<ItemTaxModel, Float> itemTaxAddiColum;
    @FXML
    private MenuItem editContextMenu;
    @FXML
    private MenuItem deleteContextMenu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fillComboBox();   
            btnUpdateItemTax.setVisible(false);
            tableItemTax.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            tableItemTax.setEditable(true);
            //tableItemTax.getSelectionModel().cellSelectionEnabledProperty().set(true);
            updateItemTaxTable();
            
        }   catch (Exception ex) {
                Logger.getLogger(ItemTaxController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        private void updateItemTaxTable() throws SQLException {
        
        //clear previous all data from tableItemTax and Load Again
        tableItemTax.getItems().clear();
        
       itemTaxIdColum.setCellValueFactory(new PropertyValueFactory<ItemTaxModel,Integer>("itemTaxId"));
       itemTaxNameColum.setCellValueFactory(new PropertyValueFactory<ItemTaxModel,String>("itemTaxName"));
       itemTaxSystemColum.setCellValueFactory(new PropertyValueFactory<ItemTaxModel,String>("itemTaxSystemName"));
       itemTaxCategoryColum.setCellValueFactory(new PropertyValueFactory<ItemTaxModel,String>("itemTaxCategoryName"));
       itemTaxOnColum.setCellValueFactory(new PropertyValueFactory<ItemTaxModel,String>("itemTaxOnName"));
       itemTaxSgstColum.setCellValueFactory(new PropertyValueFactory<ItemTaxModel,Float>("itemTaxSgst"));
       itemTaxCgstColum.setCellValueFactory(new PropertyValueFactory<ItemTaxModel,Float>("itemTaxCgst"));
       itemTaxIgstColum.setCellValueFactory(new PropertyValueFactory<ItemTaxModel,Float>("itemTaxIgst"));
       itemTaxAddiColum.setCellValueFactory(new PropertyValueFactory<ItemTaxModel,Float>("itemTaxAddi"));
        
       //inner Join query for select data from multiple table and fill it into Observable List 
        query= "SELECT it.item_tax_id,it.item_tax_name,its.item_tax_system_name,itc.item_tax_category_name ,ito.item_tax_on_name,it.item_tax_sgst,it.item_tax_cgst,it.item_tax_igst,it.item_tax_Addi from item_tax it join item_tax_system its ON it.item_tax_system_id=its.item_tax_system_id join item_tax_category itc ON it.item_tax_category_id=itc.item_tax_category_id join item_tax_on ito ON it.item_tax_on_id=ito.item_tax_on_id" ;
        rs=DatabaseHandler.selectData(query);
        while(rs.next()){
            try {
                list.add(
                        new ItemTaxModel(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),Float.parseFloat(rs.getString(6)),Float.parseFloat(rs.getString(7)),Float.parseFloat(rs.getString(8)),Float.parseFloat(rs.getString(9)))
                 );
                
                
            } catch (SQLException ex) {
                Logger.getLogger(ItemTaxController.class.getName()).log(Level.SEVERE, null, ex);
            }
            tableItemTax.setItems(list);
            
        }
        
    }

    @FXML
    private void saveItemTax(ActionEvent event) throws SQLException {
        
        // code for retrive Tax_System_id from tax_system table according to which tax system selected by user by combo box
        taxSystem=cmbItemTaxSystem.getSelectionModel().getSelectedItem().toString();
        taxCategory=cmbItemTaxCategory.getSelectionModel().getSelectedItem().toString();
        taxOn=cmbItemTaxOn.getSelectionModel().getSelectedItem().toString();
        
        
        //select item_tax_system_Id from database using which value user has selected from combobox 
        query="select item_tax_system_id from item_tax_system where item_tax_system_name='"+taxSystem+"'";
        rs=DatabaseHandler.selectData(query);
       
        while(rs.next()){
            try {
                taxSystemId=rs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(ItemTaxController.class.getName()).log(Level.SEVERE, null, ex);
            }             
        } 
        
      //select item_tax_category_Id from database using which value user has selected from combobox 
        query="select item_tax_category_id from item_tax_category where item_tax_category_name='"+taxCategory+"'";
        rs=DatabaseHandler.selectData(query);
       
        while(rs.next()){
            try {
                taxCategoryId=rs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(ItemTaxController.class.getName()).log(Level.SEVERE, null, ex);
            }             
        } 
        
        //select item_tax_on_Id from database using which value user has selected from combobox 
        query="select item_tax_on_id from item_tax_on where item_tax_on_name='"+taxOn+"'";
        rs=DatabaseHandler.selectData(query);
       
        while(rs.next()){
            try {
                taxOnId=rs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(ItemTaxController.class.getName()).log(Level.SEVERE, null, ex);
            }             
        } 
        
        
        //Insert Data into item_tax which user has entered into fields
        query="insert into item_tax values(null,'"+txtItemTaxName.getText()+"',"+taxSystemId+","+taxCategoryId+","+taxOnId+","+Float.parseFloat(txtItemTaxSgst.getText())+","+Float.parseFloat(txtItemTaxCgst.getText())+","+Float.parseFloat(txtItemTaxIgst.getText())+","+Float.parseFloat(txtItemTaxAddi.getText())+")";
        
        if(DatabaseHandler.insertData(query)){
        updateItemTaxTable();
        UserHandler.alertInformation("saveAlert");
        }
        else{
            JOptionPane.showMessageDialog(null, "Data Not Saved! Please follow Description");
        }
        clearAll();
    }

    @FXML
    private void refreshItemTax(ActionEvent event) {
        clearAll();
    }

    @FXML
    private void editItemTax(ActionEvent event) {
        ItemTaxModel itemTaxModel=(ItemTaxModel) tableItemTax.getSelectionModel().getSelectedItem();
        
        taxId=tableItemTax.getSelectionModel().getSelectedItem().getItemTaxId();
        txtItemTaxName.setText(itemTaxModel.getItemTaxName());
        txtItemTaxSgst.setText(itemTaxModel.getItemTaxSgst().toString());
        txtItemTaxCgst.setText(itemTaxModel.getItemTaxCgst().toString());
        txtItemTaxIgst.setText(itemTaxModel.getItemTaxIgst().toString());
        txtItemTaxAddi.setText(itemTaxModel.getItemTaxAddi().toString());
        cmbItemTaxSystem.getSelectionModel().select(itemTaxModel.getItemTaxSystemName());
        cmbItemTaxCategory.getSelectionModel().select(itemTaxModel.getItemTaxCategoryName());
        cmbItemTaxOn.getSelectionModel().select(itemTaxModel.getItemTaxOnName());
        btnSaveItemTax.setVisible(false);
        btnUpdateItemTax.setVisible(true);
        selectedRowId=itemTaxModel.getItemTaxId(); //This value is used into UpdateItemTax Function to dwnote which row user want to update
        
    }
    
    @FXML
    private void removeItemTax(ActionEvent event) throws SQLException {
        ObservableList<ItemTaxModel> listId= FXCollections.observableArrayList();    
        listId.addAll(tableItemTax.getSelectionModel().getSelectedItems());
        //call Custom Mathod For Confirmation Dialog With passing Total Number Of Selected record 
        Optional<ButtonType> buttonChoice=UserHandler.alertConfirmation(listId.size(),"deleteAlert");
        if(buttonChoice.get()== ButtonType.OK){
            for(int i=0;i<listId.size();i++){
                query="delete from item_tax where item_tax_id="+listId.get(i).getItemTaxId()+"";
                DatabaseHandler.removeData(query);                         
            }
        }
        updateItemTaxTable();
    }
    
    //For Updatem item_Tax table row by which user want to update
    @FXML
    private void updateItemTax(ActionEvent event) throws SQLException {
        
        // code for retrive Tax_System_id from tax_system table according to which tax system selected by user by combo box
        taxSystem=cmbItemTaxSystem.getSelectionModel().getSelectedItem().toString();
        taxCategory=cmbItemTaxCategory.getSelectionModel().getSelectedItem().toString();
        taxOn=cmbItemTaxOn.getSelectionModel().getSelectedItem().toString();
        
        
        //select item_tax_system_Id from database using which value user has selected from combobox 
        query="select item_tax_system_id from item_tax_system where item_tax_system_name='"+taxSystem+"'";
        rs=DatabaseHandler.selectData(query);
       
        while(rs.next()){
            try {
                taxSystemId=rs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(ItemTaxController.class.getName()).log(Level.SEVERE, null, ex);
            }             
        } 
        
      //select item_tax_category_Id from database using which value user has selected from combobox 
        query="select item_tax_category_id from item_tax_category where item_tax_category_name='"+taxCategory+"'";
        rs=DatabaseHandler.selectData(query);
       
        while(rs.next()){
            try {
                taxCategoryId=rs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(ItemTaxController.class.getName()).log(Level.SEVERE, null, ex);
            }             
        } 
        
        //select item_tax_on_Id from database using which value user has selected from combobox 
        query="select item_tax_on_id from item_tax_on where item_tax_on_name='"+taxOn+"'";
        rs=DatabaseHandler.selectData(query);
       
        while(rs.next()){
            try {
                taxOnId=rs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(ItemTaxController.class.getName()).log(Level.SEVERE, null, ex);
            }             
        } 
        
        
        //Update Data from  item_tax which user has entered into fields
        query="update item_tax set item_tax_name='"+txtItemTaxName.getText()+"',item_tax_system_id="+taxSystemId+",item_tax_category_id="+taxCategoryId+",item_tax_on_id="+taxOnId+",item_tax_sgst="+Float.parseFloat(txtItemTaxSgst.getText())+",item_tax_cgst="+Float.parseFloat(txtItemTaxCgst.getText())+",item_tax_igst="+Float.parseFloat(txtItemTaxIgst.getText())+",item_tax_addi="+Float.parseFloat(txtItemTaxAddi.getText())+" where item_tax_id="+taxId+"";
        
        Optional<ButtonType> buttonChoice=UserHandler.alertConfirmation("updateAlert");
        if(buttonChoice.get()==ButtonType.OK){
            if(DatabaseHandler.updateData(query)){
                UserHandler.alertInformation("updateSuccessInfo");
                updateItemTaxTable();
            }     
        }
        clearAll();
        
        
    }

    private void clearAll() {
        btnSaveItemTax.setVisible(true);
        btnUpdateItemTax.setVisible(false);
        txtItemTaxName.clear();
        txtItemTaxSgst.clear();
        txtItemTaxCgst.clear();
        txtItemTaxIgst.clear();
        txtItemTaxAddi.clear();
         cmbItemTaxSystem.setItems(null);
         cmbItemTaxCategory.setItems(null);
         cmbItemTaxOn.setItems(null);
         cmbItemTaxSystem.setItems(listCmbItemTaxSystem);
         cmbItemTaxCategory.setItems(listCmbItemTaxCategory);
         cmbItemTaxOn.setItems(listCmbItemTaxOn);
    }

    
    
    //This method is for Fill ComboBox at initial level and Fill Observable List for fillUp ComboBox Again
    private void fillComboBox() throws SQLException {
        
        // Fill Observable List For itemTaxSystem in listItemTaxSystem
        query="select item_tax_system_name from item_tax_system";
        rs=DatabaseHandler.selectData(query);
        
        while(rs.next()){
            try {
                listCmbItemTaxSystem.add(rs.getString(1));
                
            } catch (SQLException ex) {
                Logger.getLogger(ItemTaxController.class.getName()).log(Level.SEVERE, null, ex);
            }         
        } 
            cmbItemTaxSystem.setItems(listCmbItemTaxSystem);
    
    
    
        // Fill Observable List For itemTaxCategory in listItemTaxCategory
        query="select item_tax_category_name from item_tax_category";
        rs=DatabaseHandler.selectData(query);
        
        while(rs.next()){
            try {
                listCmbItemTaxCategory.add(rs.getString(1));
                
            } catch (SQLException ex) {
                Logger.getLogger(ItemTaxController.class.getName()).log(Level.SEVERE, null, ex);
            }         
        } 
        cmbItemTaxCategory.setItems(listCmbItemTaxCategory);
        
        
        // Fill Observable List For itemTaxOn in listItemTaxOn
        query="select item_tax_on_name from item_tax_on";
        rs=DatabaseHandler.selectData(query);
        
        while(rs.next()){
            try {
                listCmbItemTaxOn.add(rs.getString(1));
                
            } catch (SQLException ex) {
                Logger.getLogger(ItemTaxController.class.getName()).log(Level.SEVERE, null, ex);
            }         
        } 
        cmbItemTaxOn.setItems(listCmbItemTaxOn);
     
    }//End Of fillComboBox

    
    
}
