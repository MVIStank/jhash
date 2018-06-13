/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jhash;

import javafx.scene.control.Alert;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class FXMLDocumentController implements Initializable {
   
//Inizialize param
    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private TextField SUBNET;
     @FXML
    private TextField MASK;
 ////   
 
     @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
        check_mask();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    private void check_mask(){
        String mask=MASK.getText();
         if(mask== null || mask.trim().length() == 0)
         {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Ошибка");
              alert.setHeaderText(null);
              alert.setContentText(" Введите маску! ");
              alert.showAndWait();   
         }
         int mask_int=Integer.parseInt(mask);
           if (mask_int<0 || mask_int>32 )
           { 
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Ошибка");
              alert.setHeaderText(null);
              alert.setContentText(" Маска должна быть в диапозоне от 0 до 32 ");
              alert.showAndWait();   
           }     
          }// end function
    
     private void check_subnet(){
         
         
     }
            
    }

    

