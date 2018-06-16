//Добавить проверку подсети и маски на наличие букв
package jhash;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
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
import javafx.scene.control.CheckBox;
import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;


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
     @FXML 
    private CheckBox CheckBox;
     @FXML
    private ProgressIndicator ProgressIndicator;
 ////
    work_ip tmp=new work_ip ();   
    public void appendText(String str) {
    Platform.runLater(() -> IpOutputField.appendText(str));
}
 
     @FXML
    private void handleButtonAction(ActionEvent event) {
      //  System.out.println("You clicked me!");
       // label.setText("Hello World!"); 
        IpOutputField.clear();
        check_mask();
        check_subnet();
        NetField.setText(Arrays.toString(tmp.build_network()));
        MaskField.setText(Arrays.toString(tmp.mask));
        BroadcastField.setText(Arrays.toString(tmp.build_broadcast()));
        if(CheckBox.isSelected())     
        {  
           //  Platform.runLater(() -> got());
           tmp.print();
        }
            
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        OutputStream out = new OutputStream() {
        @Override
        public void write(int b) throws IOException {
            appendText(String.valueOf((char) b));
        }
    };
    System.setOut(new PrintStream(out, true));
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
          char a =',';
          char b= '.';
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
           {   subnet=subnet.replace(a, b);
               
               SUBNET.setText(subnet);
               if(subnet.contains("."))
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

