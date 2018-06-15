//Добавить проверку подсети и маски на наличие букв
package jhash;

import java.util.Arrays;
import javafx.scene.control.Alert;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;


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
     @FXML
    private TextField NetField;
     @FXML
    private TextField BroadcastField;
     @FXML
    private TextField MaskField;
     @FXML
    private TextArea IpOutputField;
 ////
    work_ip tmp=new work_ip ();   
 
     @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");   
        check_mask();
        check_subnet();
        NetField.setText(Arrays.toString(tmp.build_network()));
        BroadcastField.setText(Arrays.toString(tmp.build_broadcast()));
        //MaskField.setText(System.out.toString()
                
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
      
    private void check_mask(){
        String mask=MASK.getText();
         if(mask== null || mask.length() == 0)
          {
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Ошибка");
              alert.setHeaderText(null);
              alert.setContentText(" Введите маску! ");
              alert.showAndWait();
          }
         else
           { int mask_int=Integer.parseInt(mask);
              if (mask_int<0 || mask_int>32 )
                { 
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText(" Маска должна быть в диапозоне от 0 до 32 ");
                alert.showAndWait();              
                }  
              tmp.set_mask(mask_int);
           }
    }// end function
    
     private void check_subnet(){
         String subnet=SUBNET.getText();
         String [] str;
         int [] subnet_network=new int[4];
          if( subnet.length() == 0)
           {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Ошибка");
              alert.setHeaderText(null);
              alert.setContentText(" Введите подсеть! ");
              alert.showAndWait();   
           }
           else
           {    if(subnet.contains("."))
               {
                str = subnet.split("[.]"); 
                if(str.length<=3)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText(null);
                    alert.setContentText("  октеты заполнены неверно!");
                    alert.showAndWait();
                }
                    for(int i=0; i<=3;i++) 
                    {    
                        subnet_network[i]=Integer.parseInt(str[i]);
                    }
                    for (int i=0; i<=3;i++) 
                     {
                      if (subnet_network[i] < 0 || subnet_network[i] > 255)
                       {
                       Alert alert = new Alert(Alert.AlertType.INFORMATION);
                       alert.setTitle("Ошибка");
                       alert.setHeaderText(null);
                       alert.setContentText(" Октет должен быть в диапозоне от 0 до 255 ");
                       alert.showAndWait();
                       }     
                     }
               }
                 else
                 {
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
                       alert.setTitle("Ошибка");
                       alert.setHeaderText(null);
                       alert.setContentText(" Подсеть задана не верно! ");
                       alert.showAndWait();
                  }
           } //end else
          tmp.set_ip(subnet_network);
     }//end function
      
   


}



    

