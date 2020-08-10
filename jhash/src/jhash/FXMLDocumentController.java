//Добавить проверку подсети и маски на наличие букв
package jhash;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javafx.scene.control.Alert;
import java.net.URL;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;




public class FXMLDocumentController implements Initializable {
  
//Inizialize param
    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private Button buttonSave;
    @FXML
    private TextField SUBNET;
     @FXML
    private TextField MASK;
     @FXML
    private TextField NetField;
    @FXML
    private TextField TimeField;
     @FXML
    private TextField BroadcastField;
     @FXML
    private TextField MaskField;
     @FXML 
    private CheckBox CheckBox;
     @FXML 
    private ProgressBar progressbar;
     @FXML
    private ListView listView;

    Task copyWorker;


 private final static Logger log = LogManager.getLogger();
  work_ip tmp=new work_ip ();
  ListView<String> myListView = new ListView<>();
  ObservableList <String> list =FXCollections.observableArrayList();
  Set<Integer> keys;

  //  public void appendText(String str) {
  //  Platform.runLater(() -> IpOutputField.appendText(str));
//}



    public Task createWorker()
  {
    return new Task() 
     {
        @Override
        protected Object call() throws Exception 
        {
           updateProgress(0.5, 1); 
            updateMessage("Working...");
           Thread.sleep(7000);
             tmp.print();
             keys = tmp.treemap.keySet();
             for(Integer key: keys)
              {
             Platform.runLater(() -> list.addAll(tmp.treemap.get(key)));
              
             Platform.runLater(() -> listView.setItems(list));
              }
             return true;
        }
    };
  }
     @FXML
private void handleButtonAction(ActionEvent event) {
  check_mask();
  check_subnet();
  TimeField.setText(String.valueOf(new GregorianCalendar().getTime()));
  NetField.setText(Arrays.toString(tmp.build_network()));
  MaskField.setText(Arrays.toString(tmp.mask));
  BroadcastField.setText(Arrays.toString(tmp.build_broadcast()));
    if(CheckBox.isSelected())     
      {   
        if(keys !=null )
          {
            keys.clear();
          }
        if(list !=null)
           {
             list.clear();
           }
      label.setVisible(true);
      button.setDisable(true);
      progressbar.setVisible(true);
      progressbar.setProgress(0);
      copyWorker = createWorker();
      copyWorker.messageProperty().addListener(new ChangeListener<String>()
        {
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
            {
              label.setText(newValue);
            }
        });     
      copyWorker.setOnSucceeded(new EventHandler<WorkerStateEvent>()
        {
          @Override
           public void handle(WorkerStateEvent t)
           {
              button.setDisable(false);
              progressbar.progressProperty().unbind();
              progressbar.setProgress(1);
              progressbar.setVisible(false);
              // IpOutputField.appendText("Количество IP адресов:  "+(tmp.treemap.size()-2));
              // label.setText("Completed!");
              label.setVisible(false);
            }
        });
       progressbar.progressProperty().unbind();
       progressbar.progressProperty().bind(copyWorker.progressProperty());
        new Thread(copyWorker).start();
    }
    }
 
@FXML
private void handleButtonActionSave (ActionEvent event) {
    FileSave save =new FileSave();
    FileChooser fileChooser = new FileChooser();
   fileChooser.setTitle("Сохранить");
   fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
   fileChooser.setInitialFileName("result.txt");
   File file = fileChooser.showSaveDialog(null);
        try {
           log.info("Попытка сохранить в файл");
            save.export_file(file, tmp.treemap);
          //  log.info("Файл сохранен!");
        } catch (IOException ex) {
          log.info("Ошибка при сохранении файла!",ex);
        }
   
   
}

    @Override
public void initialize(URL url, ResourceBundle rb){  }
private void check_mask()
    {
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
             
           { int mask_int=0;
               try{
                  mask_int=Integer.parseInt(mask);
                  }
               catch(NumberFormatException e)
                  {
                log.error("check_mask(): Parse_mask_error");
                  }
               //int mask_int=Integer.parseInt(mask);
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
                        try{
                        subnet_network[i]=Integer.parseInt(str[i]);
                           }
                        catch(NumberFormatException ed)
                           {
                         log.error("check_subnet(): Parse_mask_error");
                           }
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

