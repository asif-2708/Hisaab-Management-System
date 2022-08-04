
package hisab.model;

import hisab.controller.HomeController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class HomeModel {
    
     public void showPanel(String location,StackPane stackPane){
        try {
            
            FXMLLoader loader=new FXMLLoader();
            Pane root=loader.load(getClass().getResource(location));
            root.setMinSize(stackPane.getWidth(),  stackPane.getHeight());
            stackPane.getChildren().clear();
            stackPane.getChildren().add(root);
         
       
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void removePanel(String location,StackPane stackPane){
        try {
            
            FXMLLoader loader=new FXMLLoader();
            Pane root=loader.load(getClass().getResource(location));      
            stackPane.getChildren().remove(root);
       
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showScene(String location){
        try {
            
            FXMLLoader loader=new FXMLLoader();
            Pane root=loader.load(getClass().getResource(location));
            //root.setMinSize(stackPane.getWidth(),  stackPane.getHeight());
            Stage stage=new Stage();
            Scene scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
         
       
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
