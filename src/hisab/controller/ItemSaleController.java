/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hisab.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import hisab.DatabaseHandler;
import hisab.ReportGenerator;
import hisab.model.HomeModel;
import hisab.model.ItemSaleModel;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class ItemSaleController implements Initializable {

    private String query;
    private ResultSet resultSet;
    private int saleNo=1;
    double itemPrice=0,itemTaxPer=0,itemAmt=0,itemTaxAmt=0,itemNetAmt=0;
    int itemQty=1;
    private String itemUnit;//used to store value of item unit type and used when edit the item
    boolean flag=true; //the flag value is false when the table detail mode is in edited mode (using editContextMenu) to prevent chages with keyrealeased method
    
    @FXML
    private TextField txtSaleNo;

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
    private Button btnSaveItemSale;
    @FXML
    private FontAwesomeIconView btnAddItem1;
    @FXML
    private Button btnClearItemSale;
    @FXML
    private FontAwesomeIconView btnAddItem11;
    @FXML
    private TableView<ItemSaleModel> tableItemSaleDetail;
    @FXML
    private TableColumn<ItemSaleModel, String> itemSaleItemNameColum;
    @FXML
    private TableColumn<ItemSaleModel, Integer> itemSaleItemQtyColum;
    @FXML
    private TableColumn<ItemSaleModel, String> itemSaleItemUnitColum;
    @FXML
    private TableColumn<ItemSaleModel, Double> itemSaleItemPriceColum;
    @FXML
    private TableColumn<ItemSaleModel, Double> itemSaleItemAmtColum;
    @FXML
    private TableColumn<ItemSaleModel, Double> itemSaleItemTaxColum;
    @FXML
    private TableColumn<ItemSaleModel, Double> itemSaleItemNetAmt;
    @FXML
    private DatePicker txtSaleDate;
    @FXML
    private TextField txtItemUnit;
    @FXML
    private ComboBox<String> cmbSaleType;
    @FXML
    private Button btnSaveItemDetail;
    ObservableList<ItemSaleModel> listSaleDetail=FXCollections.observableArrayList();
    ObservableList<ItemSaleModel> listSaleDetaildelete=FXCollections.observableArrayList();
    ObservableList<ItemSaleModel> listSaleDetailUpdate=FXCollections.observableArrayList();
    ObservableList<ItemSaleModel> listSaleDetailFillAmtBox=FXCollections.observableArrayList();

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
            tableItemSaleDetail.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
             tableItemSaleDetail.setEditable(true);
            
            LocalDate localDate=LocalDate.now();
            txtSaleDate.setValue(localDate);
            generateInnNo();
            txtSaleNo.setText(String.valueOf(saleNo));
            cmbCashCreditFill();
            cmbAccNameFill();
            cmbItemSaleTypeFill();
            cmbItemNameFill();
        } catch (SQLException ex) {
            Logger.getLogger(ItemSaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

   
    
   
    
    
    //Generate Innvoice No to Display In sale No text box
    private void generateInnNo() throws SQLException {
        query="select * from sales_master ";
        resultSet=DatabaseHandler.selectData(query);
        while(resultSet.next()){
            saleNo=resultSet.getInt(1);
            saleNo++;
            //txtSaleNo.setText(String.valueOf(saleNo));
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
            Logger.getLogger(ItemSaleController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ItemSaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    
    //Fill the sale type  list from Database and fill the comboBox 
    private void cmbItemSaleTypeFill(){
       ObservableList<String> listSaleType=FXCollections.observableArrayList(); 
        try {           
            query="select sales_type_name from sales_type";
            resultSet=DatabaseHandler.selectData(query);
            while(resultSet.next()){
               listSaleType.add(resultSet.getString(1));
            }
            
            cmbSaleType.setItems(listSaleType);
            
        } catch (SQLException ex) {
            Logger.getLogger(ItemSaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    
    //FILL DETAIL OF ITEM WHEN ITEM IS SELECTED
    @FXML
    private void itemDetailFill(ActionEvent event) throws SQLException {
         if(flag==true){
        
            query="select im.sale_price,iu.item_unit_name,it.item_tax_igst from item_master im join item_unit iu on im.item_unit_id=iu.item_unit_id join item_tax it on im.item_tax_id=it.item_tax_id where im.item_name='"+cmbItemName.getSelectionModel().getSelectedItem()+"'";
            resultSet=DatabaseHandler.selectData(query);
            while(resultSet.next()){
                txtItemPrice.setText(resultSet.getString("sale_price"));
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
    private void saveSaleItemDetail(ActionEvent event) {

        listSaleDetail.add(
                new ItemSaleModel(cmbItemName.getSelectionModel().getSelectedItem(),Double.parseDouble(txtItemPrice.getText()),txtItemUnit.getText(),Integer.parseInt(txtItemQty.getText()),Double.parseDouble(txtItemAmt.getText()),Double.parseDouble(txtItemTaxAmt.getText()),Double.parseDouble(txtItemNetAmt.getText()))
        ); 
        
       updateSaleItemDetailTable();
    }

    private void updateSaleItemDetailTable() {
        
        clearItemSaleDetailFields();//call method which clear all the fields before updating the table with ObservableList
        
        itemSaleItemNameColum.setCellValueFactory(new PropertyValueFactory<ItemSaleModel,String>("itemName"));
        itemSaleItemQtyColum.setCellValueFactory(new PropertyValueFactory<ItemSaleModel,Integer>("itemQty"));
        itemSaleItemUnitColum.setCellValueFactory(new PropertyValueFactory<ItemSaleModel,String>("itemPer"));
        itemSaleItemPriceColum.setCellValueFactory(new PropertyValueFactory<ItemSaleModel,Double>("itemPrice"));
        itemSaleItemAmtColum.setCellValueFactory(new PropertyValueFactory<ItemSaleModel,Double>("itemAmt"));
        itemSaleItemTaxColum.setCellValueFactory(new PropertyValueFactory<ItemSaleModel,Double>("itemTaxAmt"));
        itemSaleItemNetAmt.setCellValueFactory(new PropertyValueFactory<ItemSaleModel,Double>("itemNetAmt"));
        
        tableItemSaleDetail.setItems(listSaleDetail);
        fillItemAmtBoxOnTableUpdated();
    }

    @FXML
    private void editItemSaleDetail(ActionEvent event) {
        flag=false;
        btnUpdateItemDetail.setVisible(true);
        btnSaveItemDetail.setVisible(false);
        ItemSaleModel itemSaleDetailEdit=tableItemSaleDetail.getSelectionModel().getSelectedItem();
        
        cmbItemName.getSelectionModel().select(itemSaleDetailEdit.getItemName());
        txtItemPrice.setText(itemSaleDetailEdit.getItemPrice().toString());
        txtItemQty.setText(String.valueOf(itemSaleDetailEdit.getItemQty()));
        txtItemUnit.setText(itemSaleDetailEdit.getItemPer());
        txtItemAmt.setText(itemSaleDetailEdit.getItemAmt().toString());
        txtItemTaxAmt.setText(itemSaleDetailEdit.getItemTaxAmt().toString());
        txtItemNetAmt.setText(itemSaleDetailEdit.getItemNetAmt().toString());
        
    }

    @FXML
    private void deleteItemSaleDetail(ActionEvent event) {
        listSaleDetaildelete=tableItemSaleDetail.getSelectionModel().getSelectedItems();
        listSaleDetail.removeAll(listSaleDetaildelete);      
        updateSaleItemDetailTable();
    }

    
    //clear all the fields when save/delete the itemDetail
    private void clearItemSaleDetailFields(){
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
    private void updateSaleItemDetail(ActionEvent event) {
        flag=true;
        listSaleDetailUpdate.add(
                new ItemSaleModel(cmbItemName.getSelectionModel().getSelectedItem(),Double.parseDouble(txtItemPrice.getText()),txtItemUnit.getText(),Integer.parseInt(txtItemQty.getText()),Double.parseDouble(txtItemAmt.getText()),Double.parseDouble(txtItemTaxAmt.getText()),Double.parseDouble(txtItemNetAmt.getText()))
        );
        
        listSaleDetail.removeAll(tableItemSaleDetail.getSelectionModel().getSelectedItem());
        listSaleDetail.addAll(listSaleDetailUpdate);
        listSaleDetailUpdate.clear();
        updateSaleItemDetailTable();
        
        btnUpdateItemDetail.setVisible(false);
        btnSaveItemDetail.setVisible(true);
    }

    
    private void fillItemAmtBoxOnTableUpdated() {
        ItemSaleModel itemSaleModel;
        double totalAmt=0,taxAmt=0,netAmt=0;
      
        for(int i=1;i<=listSaleDetail.size();i++){
            itemSaleModel=listSaleDetail.get(i-1);
            totalAmt=totalAmt+itemSaleModel.getItemAmt();
            taxAmt=taxAmt+itemSaleModel.getItemTaxAmt();
            netAmt=netAmt+itemSaleModel.getItemNetAmt();
        }
        
        txtTotalAmt.setText(String.valueOf(totalAmt));
        txtTotalTaxAmt.setText(String.valueOf(taxAmt));
        txtTotalNetAmt.setText(String.valueOf(netAmt));
        txtTotalPayableAmt.setText(String.valueOf(Double.parseDouble(txtTotalNetAmt.getText())- Double.parseDouble(txtTotalDisAmt.getText())));
    }

    @FXML
    private void saveItemSale(ActionEvent event) throws SQLException {
        ResultSet resultAccountId,resultSetDetail;
        int accountId=0,billNo=0,saleTermId=0,saleTypeId=0,itemId=0;
        int saleQty=0,clQty=0; //for retrive previous sale qty and add new qty in stock_master
        double saleValue=0,clValue=0;
        ObservableList<ItemSaleModel> tableData=FXCollections.observableArrayList();
        
        //Get sales_term_id from sales_term according to sale_trerm cash/credit
        query="select sales_term_id from sales_term where sales_term_name='"+cmbCashCredit.getSelectionModel().getSelectedItem()+"'";
        resultSetDetail=DatabaseHandler.selectData(query);
        if(resultSetDetail.next()){
            saleTermId=resultSetDetail.getInt("sales_term_id");        
        }
        
        
        //Get sales_type_id from sales_type according to sales_term cash/credit
        query="select sales_type_id from sales_type where sales_type_name='"+cmbSaleType.getSelectionModel().getSelectedItem()+"'";
        resultSetDetail=DatabaseHandler.selectData(query);
        if(resultSetDetail.next()){
            saleTypeId=resultSetDetail.getInt("sales_type_id");
        }
        
        
        // get account_master_id from account_detail according to account_name
        query="select account_master_id from account_detail where account_print_name='"+cmbAccName.getSelectionModel().getSelectedItem()+"'";
        resultAccountId=DatabaseHandler.selectData(query);
        if(resultAccountId.next()){
            accountId = resultAccountId.getInt("account_master_id");
        }
        
       
           
           
           
        query="insert into sales_master(sales_master_id,account_id,sales_term_id,sales_type_id,total_amt,igst_tax,total_tax,dis_amt,net_amt,sales_date,u_date)values("+txtSaleNo.getText()+","+accountId+","+saleTermId+","+ saleTypeId+","+Double.parseDouble(txtTotalAmt.getText())+","+Double.parseDouble(txtTotalTaxAmt.getText())+","+Double.parseDouble(txtTotalTaxAmt.getText())+","+Double.parseDouble(txtTotalDisAmt.getText())+","+Double.parseDouble(txtTotalPayableAmt.getText())+",'"+txtSaleDate.getValue()+"','"+txtSaleDate.getValue()+"')";
        if(DatabaseHandler.insertData(query)){
            
            //get Sale_master_id from sale_master after insert sale_master data to insert ite_detail according to this bill no
            query="select sales_master_id from sales_master where sales_master_id= "+Integer.parseInt(txtSaleNo.getText())+"";
            resultBillNo=DatabaseHandler.selectData(query);
            System.out.println("bill no="+Integer.parseInt(txtSaleNo.getText()));
            if(resultBillNo.next()){
                billNo = resultBillNo.getInt(1); 
            } 
           
           
           
            
            tableData.addAll(tableItemSaleDetail.getItems());
            for(int i=0;i<tableData.size();i++){
                
   
                query="select item_master_id from item_master where item_name='"+tableData.get(i).getItemName()+"'";
                resultSetDetail=DatabaseHandler.selectData(query);
               
                if(resultSetDetail.next()){
                   
                    itemId=resultSetDetail.getInt("item_master_id");
                    
                } 
           

              query="insert into sales_detail(sales_master_id,item_id,item_per,item_qty,sale_price,total_amt,IGST,payable_amt,sales_date)values("+billNo+","+itemId+",'"+tableData.get(i).getItemPer()+"','"+tableData.get(i).getItemQty()+"',"+Double.parseDouble(tableData.get(i).getItemPrice().toString())+","+Double.parseDouble(tableData.get(i).getItemAmt().toString())+","+Double.parseDouble(tableData.get(i).getItemTaxAmt().toString())+","+Double.parseDouble(tableData.get(i).getItemNetAmt().toString())+",'"+txtSaleDate.getValue()+"')";
                DatabaseHandler.insertData(query);
              
                    
                    
                    //update the stock detail
                    query="select sm.sale_qty,sm.sale_value,it.pur_price from stock_master sm join item_master it on it.item_master_id=sm.item_master_id where sm.item_master_id="+itemId+"";
                    resultSet=DatabaseHandler.selectData(query);
                    if(resultSet.next()){     
                        saleQty =resultSet.getInt("sale_qty")+tableData.get(i).getItemQty();  
                        saleValue=saleQty*resultSet.getDouble("pur_price");
                        //saleValue=saleQty*Double.parseDouble(tableData.get(i).getItemPrice().toString());
                        query="update stock_master set sale_qty="+saleQty+",sale_value="+saleValue+" where item_master_id="+itemId+"";
                        DatabaseHandler.updateData(query);
                    }
                    query="select op_qty+pur_qty-sale_qty,op_value+pur_value-sale_value from stock_master where item_master_id="+itemId+"";
                    resultSet=DatabaseHandler.selectData(query);
                    if(resultSet.next()){
                    query="update stock_master set cl_stock="+resultSet.getInt("op_qty+pur_qty-sale_qty")+", cl_value="+resultSet.getInt("op_value+pur_value-sale_value")+" where item_master_id="+itemId+" ";
                    DatabaseHandler.updateData(query);
                    }
                    
             }
                
                 
                    Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Print Bill");
                    alert.setContentText("Bill Inserted Successfully! Do You Want To print Bill ?");
                    Optional<ButtonType> btnChoice=alert.showAndWait();
                    if(btnChoice.get()==ButtonType.OK){
                        
                      
                       String query="select sm.sales_master_id,ac.account_print_name,st.sales_term_name,st2.sales_type_name,sm.total_amt,sm.total_tax,sm.net_amt,sm.sales_date from sales_master as sm join account_detail as ac on ac.account_master_id=sm.account_id join sales_term as st on st.sales_term_id=sm.sales_term_id join sales_type as st2 on st2.sales_type_id=sm.sales_type_id where sales_master_id="+billNo+"";
                       ReportGenerator  reportGenerator=new ReportGenerator();
                       reportGenerator.generateReport("salesReport.jrxml", query);
                       
                   
                       
                    }
               
        }
     
  }

    
}








