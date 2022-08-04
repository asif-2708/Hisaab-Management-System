
package hisab.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ItemEntryModel {
    
    private final SimpleIntegerProperty itemEntryId;
    private final SimpleStringProperty itemEntryName;
    private final SimpleStringProperty itemEntryShortName;
    private final SimpleStringProperty itemBrandName;
    private final SimpleStringProperty itemCategoryName;
    private final SimpleStringProperty itemUnitName;
    private final SimpleStringProperty itemTaxName;
    private final SimpleDoubleProperty itemPurPrice;
    private final SimpleDoubleProperty itemSalePrice;

    public ItemEntryModel(Integer itemEntryId, String itemEntryName, String itemEntryShortName, String itemBrandName, String itemCategoryName, String itemUnitName, String itemTaxName, Double itemPurPrice, Double itemSalePrice) {
        this.itemEntryId =new SimpleIntegerProperty(itemEntryId);
        this.itemEntryName =new SimpleStringProperty(itemEntryName);
        this.itemEntryShortName =new SimpleStringProperty(itemEntryShortName);
        this.itemBrandName =new SimpleStringProperty(itemBrandName);
        this.itemCategoryName =new SimpleStringProperty(itemCategoryName);
        this.itemUnitName =new SimpleStringProperty(itemUnitName);
        this.itemTaxName =new SimpleStringProperty(itemTaxName);
        this.itemPurPrice =new SimpleDoubleProperty(itemPurPrice);
        this.itemSalePrice =new SimpleDoubleProperty(itemSalePrice);
    }

    public Integer getItemEntryId() {
        return itemEntryId.get();
    }

    public String getItemEntryName() {
        return itemEntryName.get();
    }

    public String getItemEntryShortName() {
        return itemEntryShortName.get();
    }

    public String getItemBrandName() {
        return itemBrandName.get();
    }

    public String getItemCategoryName() {
        return itemCategoryName.get();
    }

    public String getItemUnitName() {
        return itemUnitName.get();
    }

    public String getItemTaxName() {
        return itemTaxName.get();
    }

    public Double getItemPurPrice() {
        return itemPurPrice.get();
    }

    public Double getItemSalePrice() {
        return itemSalePrice.get();
    }
   

    public void setItemEntryId(int itemEntryId) {
        this.itemEntryId.set(itemEntryId);
    }
    
    public void setItemEntryName(String itemEntryName) {
        this.itemEntryName.set(itemEntryName);
    }
    
    public void setItemEntryShortName(String itemEntryShortName) {
        this.itemEntryShortName.set(itemEntryShortName);
    }
    
    public void setItemBrandName(String itemBrandName) {
        this.itemBrandName.set(itemBrandName);
    }
     
    public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName.set(itemCategoryName);
    }
    
    public void setItemUnitName(String itemUnitName) {
        this.itemUnitName.set(itemUnitName);
    }
     
    public void setItemTaxName(String itemTaxName) {
        this.itemTaxName.set(itemTaxName);
    }
    
    public void setItemPurPrice(Double itemPurPrice) {
        this.itemPurPrice.set(itemPurPrice);
    }
    
    public void setItemSalePrice(Double itemSalePrice) {
        this.itemSalePrice.set(itemSalePrice);
    }
}
