
package hisab.model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ItemTaxModel {
    
    private final SimpleIntegerProperty itemTaxId;
    private final SimpleStringProperty itemTaxName;
    private final SimpleStringProperty itemTaxSystemName;
    private final SimpleStringProperty itemTaxCategoryName;
    private final SimpleStringProperty itemTaxOnName;
    private final SimpleFloatProperty itemTaxSgst;
    private final SimpleFloatProperty itemTaxCgst;
    private final SimpleFloatProperty itemTaxIgst;
    private final SimpleFloatProperty itemTaxAddi;

    public ItemTaxModel(Integer itemTaxId,String itemTaxName,String itemTaxSystemName,String itemTaxCategoryName,String itemTaxOnName,Float itemTaxSgst,Float itemTaxCgst,Float itemTaxIgst,Float itemTaxAddi) {
        this.itemTaxId=new SimpleIntegerProperty(itemTaxId);
        this.itemTaxName=new SimpleStringProperty(itemTaxName);
        this.itemTaxSystemName=new SimpleStringProperty(itemTaxSystemName);
        this.itemTaxCategoryName=new SimpleStringProperty(itemTaxCategoryName);
        this.itemTaxOnName=new SimpleStringProperty(itemTaxOnName);
        this.itemTaxSgst=new SimpleFloatProperty(itemTaxSgst);
        this.itemTaxCgst=new SimpleFloatProperty(itemTaxCgst);
        this.itemTaxIgst=new SimpleFloatProperty(itemTaxIgst);
        this.itemTaxAddi=new SimpleFloatProperty(itemTaxAddi);
    }

    public Integer getItemTaxId() {
        return itemTaxId.get();
    }

    public String getItemTaxName() {
        return itemTaxName.get();
    }

    public String getItemTaxSystemName() {
        return itemTaxSystemName.get();
    }

    public String getItemTaxCategoryName() {
        return itemTaxCategoryName.get();
    }

    public String getItemTaxOnName() {
        return itemTaxOnName.get();
    }

    public Float getItemTaxSgst() {
        return itemTaxSgst.get();
    }

    public Float getItemTaxCgst() {
        return itemTaxCgst.get();
    }

    public Float getItemTaxIgst() {
        return itemTaxIgst.get();
    }

    public Float getItemTaxAddi() {
        return itemTaxAddi.get();
    }
    
    
     public void setItemTaxId(int itemTaxId) {
        this.itemTaxId.set(itemTaxId);
    }

    public void setItemTaxName(String itemTaxName) {
        this.itemTaxName.set(itemTaxName);
    }

    public void setItemTaxSystemName(String itemTaxSystemName) {
        this.itemTaxSystemName.set(itemTaxSystemName);
    }

    public void setItemTaxCategoryName(String itemTaxCategoryName) {
        this.itemTaxCategoryName.set(itemTaxCategoryName);
    }

    public void setItemTaxOnName(String itemTaxOnName) {
        this.itemTaxOnName.set(itemTaxOnName);
    }

    public void setItemTaxSgst(Float itemTaxSgst) {
        this.itemTaxSgst.set(itemTaxSgst);
    }

    public void setItemTaxCgst(Float itemTaxCgst) {
        this.itemTaxCgst.set(itemTaxCgst);
    }

    public void setItemTaxIgst(Float itemTaxIgst) {
        this.itemTaxIgst.set(itemTaxIgst);
    }

    public void setItemTaxAddi(Float itemTaxAddi) {
        this.itemTaxAddi.set(itemTaxAddi);
    }
    
    
    
}
