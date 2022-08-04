
package hisab.controller;


import com.jfoenix.controls.JFXButton;
import hisab.model.HomeModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;


public class HomeController implements Initializable {
    
    HomeModel homeModel=new HomeModel();
    @FXML  StackPane stackPane;
   
    @FXML BorderPane mainContent;
   
    @FXML
    private MenuItem insertItem;
    @FXML
    private MenuItem mnuItemBrand;
    @FXML
    private MenuItem mnuItemCatagory;
    @FXML
    private MenuItem mnuItemUnit;
    @FXML
    private MenuItem mnuItemTax;
    @FXML
    private MenuItem mnuAccount;
    @FXML
    private MenuItem mnuAccountCatagory;
    @FXML
    private MenuItem mnuPurchase;
    @FXML
    private MenuItem mnuSales;
    @FXML
    private JFXButton btnStockMaster;
    @FXML
    private MenuItem mnuUser;
    @FXML
    private MenuItem mnuSalesList;
    @FXML
    private MenuItem mnuPurchaseList;
    @FXML
    private MenuItem mnuStockList;
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void ItemEntry(ActionEvent event){
 
        homeModel.showPanel("/hisab/view/ItemEntry.fxml",stackPane);
    }
   

    @FXML
    private void showItemBrand(ActionEvent event) {
        homeModel.showScene("/hisab/view/ItemBrand.fxml");
    }

    @FXML
    private void showItemCatagory(ActionEvent event) {
        homeModel.showScene("/hisab/view/ItemCategory.fxml");
    }

    @FXML
    private void showItemUnit(ActionEvent event) {
        homeModel.showScene("/hisab/view/ItemUnit.fxml");
    }

    @FXML
    private void showItemTax(ActionEvent event) {
        homeModel.showScene("/hisab/view/ItemTax.fxml");
    }

    @FXML
    private void showAccount(ActionEvent event) {
        homeModel.showScene("/hisab/view/AccountEntry.fxml");
    }

    @FXML
    private void showAccountCatagory(ActionEvent event) {
        homeModel.showScene("/hisab/view/AccountCategory.fxml");
    }

    @FXML
     void showItemPurchase(ActionEvent event) {
        homeModel.showPanel("/hisab/view/ItemPurchase.fxml",stackPane);
    }

   

    @FXML
    private void showStockMaster(ActionEvent event) {
                homeModel.showPanel("/hisab/view/StockMaster.fxml",stackPane);
    }

    @FXML
    public void showItemSale(ActionEvent event) {
         homeModel.showPanel("/hisab/view/ItemSale.fxml",stackPane);
    }

    @FXML
    private void showUser(ActionEvent event) {
        homeModel.showPanel("/hisab/view/userRegistration.fxml",stackPane);
    }

    @FXML
    private void showSalesList(ActionEvent event) {
        homeModel.showPanel("/hisab/view/ItemSalesList.fxml",stackPane);
    }

    @FXML
    private void showPurchaseList(ActionEvent event) {
        homeModel.showPanel("/hisab/view/ItemPurchaseList.fxml",stackPane);
    }

    @FXML
    private void showStockList(ActionEvent event) {
        homeModel.showPanel("/hisab/view/StockMaster.fxml",stackPane);
    }
    
    //call methods from outside class
     public void showItemSaleFromOutside() {
         homeModel.showPanel("/hisab/view/ItemSale.fxml",stackPane);
    }
    
}
