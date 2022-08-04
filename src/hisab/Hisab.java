
package hisab;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Hisab extends Application {
    
   
    @Override
    public void start(Stage primaryStage) {    
        try {
            Parent root=FXMLLoader.load(getClass().getResource("/hisab/view/Login.fxml"));           
            Scene scene = new Scene(root);     
            primaryStage.setTitle("Hisab");
            primaryStage.setScene(scene);
            //primaryStage.initStyle(StageStyle.UNDECORATED);
            
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(Hisab.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
   
}
