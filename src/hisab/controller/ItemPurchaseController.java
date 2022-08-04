/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hisab.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import hisab.DatabaseHandler;
import hisab.ReportGenerator;
import hisab.model.ItemPurchaseModel;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class ItemPurchaseController implements Initializable {

    private String query;
    private ResultSet resultSet;
    private int purchaseNo=1;
    double itemPrice=0,itemTaxPer=0,itemAmt=0,itemTaxAmt=0,itemNetAmt=0;
    int itemQty=1;
    private String itemUnit;//used to store value of item unit type and used when edit the item
    boolean flag=true; //the flag value is false when the table detail mode is in edited mode (using editContextMenu) to prevent chages with keyrealeased method
    
    @FXML
    private TextField txtPurNo;
    @FXML
    private TextField txtInvNo;
    @FXML
    private ComboBox<String> cmbAccName;
    @FXML
    private ComboBox<String> cmbCashCredit;
    @FXML
    private ComboBox<String> cmbItemName;
    @FXML
    private TextField txtItemPrice;
    @FXML
    private TextField txtItemQty;
   
    @FXML
    private TextField txtItemAmt;
    @FXML
    private TextField txtItemTaxPer;
    @FXML
    private TextField txtItemTaxAmt;
    @FXML
    private TextField txtItemNetAmt;
    @FXML
    private FontAwesomeIconView btnAddItem;
    @FXML
    private TextField txtTotalAmt;
    @FXML
    private TextField txtTotalTaxAmt;
    @FXML
    private TextField txtTotalDisAmt;
    @FXML
    private TextField txtTotalNetAmt;
    @FXML
    private Button btnSaveItemPur;
    @FXML
    private FontAwesomeIconView btnAddItem1;
    @FXML
    private Button btnClearItemPur;
    @FXML
    private FontAwesomeIconView btnAddItem11;
    @FXML
    private TableView<ItemPurchaseModel> tableItemPurDetail;
    @FXML
    private TableColumn<ItemPurchaseModel, String> itemPurItemNameColum;
    @FXML
    private TableColumn<ItemPurchaseModel, Integer> itemPurItemQtyColum;
    @FXML
    private TableColumn<ItemPurchaseModel, String> itemPurItemUnitColum;
    @FXML
    private TableColumn<ItemPurchaseModel, Double> itemPurItemPriceColum;
    @FXML
    private TableColumn<ItemPurchaseModel, Double> itemPurItemAmtColum;
    @FXML
    private TableColumn<ItemPurchaseModel, Double> itemPurItemTaxColum;
    @FXML
    private TableColumn<ItemPurchaseModel, Double> itemPurItemNetAmt;
    @FXML
    private DatePicker txtPurDate;
    @FXML
    private TextField txtItemUnit;
    @FXML
    private ComboBox<String> cmbPurchaseType;
    @FXML
    private Button btnSaveItemDetail;
    ObservableList<ItemPurchaseModel> listPurchaseDetail=FXCollections.observableArrayList();
    ObservableList<ItemPurchaseModel> listPurchaseDetaildelete=FXCollections.observableArrayList();
    ObservableList<ItemPurchaseModel> listPurchaseDetailUpdate=FXCollections.observableArrayList();
    ObservableList<ItemPurchaseModel> listPurchaseDetailFillAmtBox=FXCollections.observableArrayList();

    @FXML
    private MenuItem editContextMenu;
    @FXML
    private Button btnUpdateItemDetail;
    @FXML
    private TextField txtTotalPayableAmt;
    private ResultSet resultBillNo;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            txtTotalDisAmt.setText("0");
            btnUpdateItemDetail.setVisible(false);
            tableItemPurDetail.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
             tableItemPurDetail.setEditable(true);
            
            LocalDate localDate=LocalDate.now();
            txtPurDate.setValue(localDate);
            generateInnNo();
            txtPurNo.setText(String.valueOf(purchaseNo));
            cmbCashCreditFill();
            cmbAccNameFill();
            cmbItemPurchaseTypeFill();
            cmbItemNameFill();
        } catch (SQLException ex) {
            Logger.getLogger(ItemPurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

   
    
   
    
    
    //Generate Innvoice No to Display In pur No text box
    private void generateInnNo() throws SQLException {
        query="select * from purchase_master ";
        resultSet=DatabaseHandler.selectData(query);
        while(resultSet.next()){
            purchaseNo=resultSet.getInt(1);
            purchaseNo++;
            //txtPurNo.setText(String.valueOf(purchaseNo));
        }
    }
    
    //fill combo box cmbCashCreditFill
    private void cmbCashCreditFill() {
        cmbCashCredit.getItems().add("Cash");
        cmbCashCredit.getItems().add("Credit");       
    }
    
    
    //Fill the Customer/supplier list from Database and fill the comboBox 
    private void cmbAccNameFill(){
       ObservableList<String> listAccName=FXCollections.observableArrayList(); 
        try {           
            query="select account_print_name from account_detail";
            resultSet=DatabaseHandler.selectData(query);
            while(resultSet.next()){
               listAccName.add(resultSet.getString(1));
            }
            
            cmbAccName.setItems(listAccName);
            
        } catch (SQLException ex) {
            Logger.getLogger(ItemPurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    //Fill the Customer/supplier list from Database and fill the comboBox 
    private void cmbItemNameFill(){
       ObservableList<String> listItemName=FXCollections.observableArrayList(); 
        try {           
            query="select item_name from item_master";
            resultSet=DatabaseHandler.selectData(query);
            while(resultSet.next()){
               listItemName.add(resultSet.getString(1));
            }
            
            cmbItemName.setItems(listItemName);
            
        } catch (SQLException ex) {
            Logger.getLogger(ItemPurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    
    //Fill the purchase type  list from Database and fill the comboBox 
    private void cmbItemPurchaseTypeFill(){
       ObservableList<String> listPurchaseType=FXCollections.observableArrayList(); 
        try {           
            query="select purchase_type_name from purchase_type";
            resultSet=DatabaseHandler.selectData(query);
            while(resultSet.next()){
               listPurchaseType.add(resultSet.getString(1));
            }
            
            cmbPurchaseType.setItems(listPurchaseType);
            
        } catch (SQLException ex) {
            Logger.getLogger(ItemPurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    
    //FILL DETAIL OF ITEM WHEN ITEM IS SELECTED
    @FXML
    private void itemDetailFill(ActionEvent event) throws SQLException {
         if(flag==true){
        
            query="select im.pur_price,iu.item_unit_name,it.item_tax_igst from item_master im join item_unit iu on im.item_unit_id=iu.item_unit_id join item_tax it on im.item_tax_id=it.item_tax_id where im.item_name='"+cmbItemName.getSelectionModel().getSelectedItem()+"'";
            resultSet=DatabaseHandler.selectData(query);
            while(resultSet.next()){
                txtItemPrice.setText(resultSet.getString("pur_price"));
                txtItemQty.setText("1");
                itemUnit=resultSet.getString("item_unit_name");
                txtItemUnit.setText(itemUnit);
                itemTaxPer=Double.parseDouble(resultSet.getString("item_tax_igst"));
                txtItemTaxPer.setText(itemTaxPer+"%");  
                itemPrice=Double.parseDouble(txtItemPrice.getText());
                itemQty=Integer.parseInt(txtItemQty.getText());
                itemAmt=itemPrice*itemQty;
                txtItemAmt.setText(String.valueOf(itemAmt));
                itemTaxAmt=(itemAmt/100)*itemTaxPer;
                txtItemTaxAmt.setText(String.valueOf(itemTaxAmt));
                itemNetAmt=itemAmt+itemTaxAmt;
                txtItemNetAmt.setText(String.valueOf(itemNetAmt));
            }
         }
    }

    @FXML
    private void onItemPriceKeyRelesed(KeyEvent event) {
        updateItemDetailOnKeyRelesed();
    }
    
    @FXML
    private void onItemQtyKeyRelesed(KeyEvent event) {
        updateItemDetailOnKeyRelesed();
    }
    
    
     @FXML
    private void onTotalDisAmtKeyRelesed(KeyEvent event) {
            txtTotalPayableAmt.setText(String.valueOf(Double.parseDouble(txtTotalNetAmt.getText())- Double.parseDouble(txtTotalDisAmt.getText())));

    }
    
    
    private void updateItemDetailOnKeyRelesed(){
        itemPrice=Double.parseDouble(txtItemPrice.getText());
        itemQty=Integer.parseInt(txtItemQty.getText());
        itemAmt=itemPrice*itemQty;
        txtItemAmt.setText(String.valueOf(itemAmt));
        itemTaxAmt=(itemAmt/100)*itemTaxPer;
        txtItemTaxAmt.setText(String.valueOf(itemTaxAmt));
        itemNetAmt=itemAmt+itemTaxAmt;
        txtItemNetAmt.setText(String.valueOf(itemNetAmt));
    }

    @FXML
    private void savePurchaseItemDetail(ActionEvent event) {

        listPurchaseDetail.add(
                new ItemPurchaseModel(cmbItemName.getSelectionModel().getSelectedItem(),Double.parseDouble(txtItemPrice.getText()),txtItemUnit.getText(),Integer.parseInt(txtItemQty.getText()),Double.parseDouble(txtItemAmt.getText()),Double.parseDouble(txtItemTaxAmt.getText()),Double.parseDouble(txtItemNetAmt.getText()))
        ); 
        
       updatePurchaseItemDetailTable();
    }

    private void updatePurchaseItemDetailTable() {
        
        clearItemPurchaseDetailFields();//call method which clear all the fields before updating the table with ObservableList
        
        itemPurItemNameColum.setCellValueFactory(new PropertyValueFactory<ItemPurchaseModel,String>("itemName"));
        itemPurItemQtyColum.setCellValueFactory(new PropertyValueFactory<ItemPurchaseModel,Integer>("itemQty"));
        itemPurItemUnitColum.setCellValueFactory(new PropertyValueFactory<ItemPurchaseModel,String>("itemPer"));
        itemPurItemPriceColum.setCellValueFactory(new PropertyValueFactory<ItemPurchaseModel,Double>("itemPrice"));
        itemPurItemAmtColum.setCellValueFactory(new PropertyValueFactory<ItemPurchaseModel,Double>("itemAmt"));
        itemPurItemTaxColum.setCellValueFactory(new PropertyValueFactory<ItemPurchaseModel,Double>("itemTaxAmt"));
        itemPurItemNetAmt.setCellValueFactory(new PropertyValueFactory<ItemPurchaseModel,Double>("itemNetAmt"));
        
        tableItemPurDetail.setItems(listPurchaseDetail);
        fillItemAmtBoxOnTableUpdated();
    }

    @FXML
    private void editItemPurDetail(ActionEvent event) {
        flag=false;
        btnUpdateItemDetail.setVisible(true);
        btnSaveItemDetail.setVisible(false);
        ItemPurchaseModel itemPurchaseDetailEdit=tableItemPurDetail.getSelectionModel().getSelectedItem();
        
        cmbItemName.getSelectionModel().select(itemPurchaseDetailEdit.getItemName());
        txtItemPrice.setText(itemPurchaseDetailEdit.getItemPrice().toString());
        txtItemQty.setText(String.valueOf(itemPurchaseDetailEdit.getItemQty()));
        txtItemUnit.setText(itemPurchaseDetailEdit.getItemPer());
        txtItemAmt.setText(itemPurchaseDetailEdit.getItemAmt().toString());
        txtItemTaxAmt.setText(itemPurchaseDetailEdit.getItemTaxAmt().toString());
        txtItemNetAmt.setText(itemPurchaseDetailEdit.getItemNetAmt().toString());
        
    }

    @FXML
    private void deleteItemPurDetail(ActionEvent event) {
        listPurchaseDetaildelete=tableItemPurDetail.getSelectionModel().getSelectedItems();
        listPurchaseDetail.removeAll(listPurchaseDetaildelete);      
        updatePurchaseItemDetailTable();
    }

    
    //clear all the fields when save/delete the itemDetail
    private void clearItemPurchaseDetailFields(){
        cmbItemName.getSelectionModel().clearSelection();
        txtItemPrice.clear();
        txtItemQty.clear();
        txtItemUnit.clear();
        txtItemAmt.clear();
        txtItemTaxPer.clear();
        txtItemTaxAmt.clear();
        txtItemNetAmt.clear();
    }

    @FXML
    private void updatePurchaseItemDetail(ActionEvent event) {
        flag=true;
        listPurchaseDetailUpdate.add(
                new ItemPurchaseModel(cmbItemName.getSelectionModel().getSelectedItem(),Double.parseDouble(txtItemPrice.getText()),txtItemUnit.getText(),Integer.parseInt(txtItemQty.getText()),Double.parseDouble(txtItemAmt.getText()),Double.parseDouble(txtItemTaxAmt.getText()),Double.parseDouble(txtItemNetAmt.getText()))
        );
        
        listPurchaseDetail.removeAll(tableItemPurDetail.getSelectionModel().getSelectedItem());
        listPurchaseDetail.addAll(listPurchaseDetailUpdate);
        listPurchaseDetailUpdate.clear();
        updatePurchaseItemDetailTable();
        
        btnUpdateItemDetail.setVisible(false);
        btnSaveItemDetail.setVisible(true);
    }

    
    private void fillItemAmtBoxOnTableUpdated() {
        ItemPurchaseModel itemPurchaseModel;
        double totalAmt=0,taxAmt=0,netAmt=0;
      
        for(int i=1;i<=listPurchaseDetail.size();i++){
            itemPurchaseModel=listPurchaseDetail.get(i-1);
            totalAmt=totalAmt+itemPurchaseModel.getItemAmt();
            taxAmt=taxAmt+itemPurchaseModel.getItemTaxAmt();
            netAmt=netAmt+itemPurchaseModel.getItemNetAmt();
        }
        
        txtTotalAmt.setText(String.valueOf(totalAmt));
        txtTotalTaxAmt.setText(String.valueOf(taxAmt));
        txtTotalNetAmt.setText(String.valueOf(netAmt));
        txtTotalPayableAmt.setText(String.valueOf(Double.parseDouble(txtTotalNetAmt.getText())- Double.parseDouble(txtTotalDisAmt.getText())));
    }

    @FXML
    private void saveItemPurchase(ActionEvent event) throws SQLException {
        ResultSet resultAccountId,resultSetDetail;
        int accountId=0,billNo=0,purchaseTermId=0,purchaseTypeId=0,itemId=0;
        int purQty=0,clQty=0; //for retrive previous pur qty and add new qty in stock_master
        double purValue=0,clValue=0;
        ObservableList<ItemPurchaseModel> tableData=FXCollections.observableArrayList();
        
        //Get purchase_term_id from purchase_term according to purchase_trerm cash/credit
        query="select purchase_term_id from purchase_term where purchase_term_name='"+cmbCashCredit.getSelectionModel().getSelectedItem()+"'";
        resultSetDetail=DatabaseHandler.selectData(query);
        if(resultSetDetail.next()){
            purchaseTermId=resultSetDetail.getInt("purchase_term_id");        
        }
        
        
        //Get purchase_type_id from purchase_type according to purchase_term cash/credit
        query="select purchase_type_id from purchase_type where purchase_type_name='"+cmbPurchaseType.getSelectionModel().getSelectedItem()+"'";
        resultSetDetail=DatabaseHandler.selectData(query);
        if(resultSetDetail.next()){
            purchaseTypeId=resultSetDetail.getInt("purchase_type_id");
        }
        
        
        // get account_master_id from account_detail according to account_name
        query="select account_master_id from account_detail where account_print_name='"+cmbAccName.getSelectionModel().getSelectedItem()+"'";
        resultAccountId=DatabaseHandler.selectData(query);
        if(resultAccountId.next()){
            accountId = resultAccountId.getInt("account_master_id");
        }
        
       
           
           
           
        query="insert into purchase_master(purchase_master_id,bill_no,account_id,purchase_term_id,purchase_type_id,total_amt,igst_tax,total_tax,dis_amt,net_amt,pur_date,u_date)values("+txtPurNo.getText()+","+txtInvNo.getText()+","+accountId+","+purchaseTermId+","+ purchaseTypeId+","+Double.parseDouble(txtTotalAmt.getText())+","+Double.parseDouble(txtTotalTaxAmt.getText())+","+Double.parseDouble(txtTotalTaxAmt.getText())+","+Double.parseDouble(txtTotalDisAmt.getText())+","+Double.parseDouble(txtTotalPayableAmt.getText())+",'"+txtPurDate.getValue()+"','"+txtPurDate.getValue()+"')";
        if(DatabaseHandler.insertData(query)){
            
            //get Purchase_master_id from purchase_master after insert purchase_master data to insert ite_detail according to this bill no
            query="select purchase_master_id from purchase_master where purchase_master_id= "+Integer.parseInt(txtPurNo.getText())+"";
            resultBillNo=DatabaseHandler.selectData(query);
            System.out.println("bill no="+Integer.parseInt(txtPurNo.getText()));
            if(resultBillNo.next()){
                billNo = resultBillNo.getInt(1); 
            } 
           
           
           
            
            tableData.addAll(tableItemPurDetail.getItems());
            for(int i=0;i<tableData.size();i++){
                
   
                query="select item_master_id from item_master where item_name='"+tableData.get(i).getItemName()+"'";
                resultSetDetail=DatabaseHandler.selectData(query);
               
                if(resultSetDetail.next()){
                   
                    itemId=resultSetDetail.getInt("item_master_id");
                    
                } 
           

              query="insert into purchase_detail(purchase_master_id,item_id,item_per,item_qty,pur_price,total_amt,IGST,payable_amt,pur_date)values("+billNo+","+itemId+",'"+tableData.get(i).getItemPer()+"','"+tableData.get(i).getItemQty()+"',"+Double.parseDouble(tableData.get(i).getItemPrice().toString())+","+Double.parseDouble(tableData.get(i).getItemAmt().toString())+","+Double.parseDouble(tableData.get(i).getItemTaxAmt().toString())+","+Double.parseDouble(tableData.get(i).getItemNetAmt().toString())+",'"+txtPurDate.getValue()+"')";
                DatabaseHandler.insertData(query);
              
                    
                    
                    //update the stock detail
                    query="select sm.pur_qty,sm.pur_value,it.pur_price from stock_master sm join item_master it on it.item_master_id=sm.item_master_id where sm.item_master_id="+itemId+"";
                    resultSet=DatabaseHandler.selectData(query);
                    if(resultSet.next()){           
                        purQty =resultSet.getInt("pur_qty")+tableData.get(i).getItemQty();
                        purValue=purQty*resultSet.getDouble("pur_price");
                        //purValue=purQty*Double.parseDouble(tableData.get(i).getItemPrice().toString());
                        
                        query="update stock_master set pur_qty="+purQty+",pur_value="+purValue+" where item_master_id="+itemId+"";
                        DatabaseHandler.updateData(query);   
                    }
                   query="select op_qty+pur_qty,op_qty+pur_qty-sale_qty,op_value+pur_value-sale_value from stock_master where item_master_id="+itemId+"";
                    resultSet=DatabaseHandler.selectData(query);
                    if(resultSet.next()){
                    query="update stock_master set total_qty="+resultSet.getInt("op_qty+pur_qty")+", cl_stock="+resultSet.getInt("op_qty+pur_qty-sale_qty")+", cl_value="+resultSet.getInt("op_value+pur_value-sale_value")+" where item_master_id="+itemId+" ";
                    DatabaseHandler.updateData(query);
                    
                    }
                     
                    
             }
                
                 
                    Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Print Bill");
                    alert.setContentText("Bill Inserted Successfully! Do You Want To print Bill ?");
                    Optional<ButtonType> btnChoice=alert.showAndWait();
                    if(btnChoice.get()==ButtonType.OK){
                        
                        
                         String query="select pm.purchase_master_id,ac.account_print_name,pt.purchase_term_name,pt2.purchase_type_name,pm.total_amt,pm.total_tax,pm.net_amt,pm.pur_date from purchase_master as pm join account_detail as ac on ac.account_master_id=pm.account_id join purchase_term as pt on pt.purchase_term_id=pm.purchase_term_id join purchase_type as pt2 on pt2.purchase_type_id=pm.purchase_type_id where purchase_master_id="+billNo+"";
                       ReportGenerator  reportGenerator=new ReportGenerator();
                       reportGenerator.generateReport("purchaseReport.jrxml", query);
                       
                       
                    }
               
        }
     
  }

    
}








