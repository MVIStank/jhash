/*
TODO:
Добавить проверку подсети и маски на наличие букв +
кнопка отмена, при расчете +
исключение при закрытии восстановления +
rewrite by task some hardly operation ( by future task)
add show time +
add socket
разделить функицю вывода маски на расчет и на печать
переделать фукнцию сохранения ( только печатать , не расчитывать)
логировние
переделать вывод ошибок ( один обект)
блокировка кнопок при расчете ( save & restore)
Исключения
*/
package jhash;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

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
    private ListView<String> listView;
    @FXML
    private  Hyperlink Stop_Handler;

    Task copyWorker;

    private final static Logger log = LogManager.getLogger();
    work_ip tmp=new work_ip ();
    ObservableList <String> list =FXCollections.observableArrayList();
    Set<Long> keys;

    Task TimeShow = new Task() {
        @Override
        protected Object call() throws Exception {
            TimeShow Tm = new TimeShow();
            while(Jhash.close_app) {
                try {
                  TimeField.setText(Tm.time());
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                Thread.sleep(400);
            }
           return null;
        }
    };

    Task createWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                updateProgress(0.5, 1);
                updateMessage("Working...");
                Thread.sleep(7000);
                tmp.print();
                keys = tmp.getMap().keySet();
                for(Long key: keys) {
                    Platform.runLater(() -> list.addAll(tmp.getMap().get(key)));

                    Platform.runLater(() -> listView.setItems(list));
                }
                return true;
            }
        };
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Thread(TimeShow).start();
    }

 // public void  SetTimeField ( TextField TimeField){ this.TimeField = TimeField; }

    @FXML
   private void  HandlerMouseCancelclick(ActionEvent event){
        if (copyWorker.isRunning()) {
           log.info("Остановка потока вывода");
            button.setDisable(false);
            Stop_Handler.setVisible(false);
            progressbar.progressProperty().unbind();
            progressbar.setVisible(false);
            buttonSave.setDisable(false);
            label.setVisible(false);
            copyWorker.cancel(true);
         }
    }
    @FXML
   private void handleButtonAction(ActionEvent event) {
         if (check_mask() == 0 && check_subnet() == 0) {
             NetField.setText(Arrays.toString(tmp.build_network()));
             MaskField.setText(Arrays.toString(tmp.getMask()));
             BroadcastField.setText(Arrays.toString(tmp.build_broadcast()));
             if (CheckBox.isSelected()) {
                 if (keys != null) {
                     keys.clear();
                 }
                 if (list != null) {
                     list.clear();
                 }
                 label.setVisible(true);
                 button.setDisable(true);
                 Stop_Handler.setVisible(true);
                 buttonSave.setDisable(true);
                 progressbar.setVisible(true);
                 progressbar.setProgress(0);
                 copyWorker = createWorker();
                 copyWorker.messageProperty().addListener(new ChangeListener<String>() {
                     public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                         label.setText(newValue);
                     }
                 });
                 copyWorker.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                     @Override
                     public void handle(WorkerStateEvent t) {
                         button.setDisable(false);
                       //  button.setText("Рассчитать");
                         Stop_Handler.setVisible(false);
                         progressbar.progressProperty().unbind();
                         progressbar.setProgress(1);
                         progressbar.setVisible(false);
                         buttonSave.setDisable(false);
                         label.setVisible(false);
                     }
                 });
                 progressbar.progressProperty().unbind();
                 progressbar.progressProperty().bind(copyWorker.progressProperty());
                 new Thread(copyWorker).start();
             }
         }
     }
 
  @FXML
  private void handleButtonActionSave (ActionEvent event) {
    FileSave save = new FileSave();
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Сохранить");
    fileChooser.setInitialFileName("result.txt");
    File file = fileChooser.showSaveDialog(null);
    if (file != null) {
        try {
            log.info("Попытка сохранить в файл");
            save.export_file(file, tmp.getMap());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText(null);
            alert.setContentText(" Файл сохранен ");
            alert.showAndWait();
            log.info("Файл сохранен");
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText(" Ошибка при сохранении файла! ");
            alert.showAndWait();
            log.error("Ошибка при сохранении файла!", ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
   }
    @FXML
    private void handleButtonActionWriteObj (ActionEvent event)  {
       //FileSave save =new FileSave();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить");
        fileChooser.setInitialFileName("result.txt");
        File file = fileChooser.showSaveDialog(null);
        if (file !=null) {
            WriteObj WriteObj = new WriteObj(tmp, file);
            try {
                WriteObj.run();
                log.info ("Объект записан в файл");
            }
            catch(IOException ex) {
                log.error("Ошибка при сохранении объекта!", ex);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    @FXML
    private void handleButtonActionRestoreObj (ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть");
        fileChooser.setInitialFileName("result.txt");
        File file = fileChooser.showOpenDialog(null);
        RestoreObj RestObj = new RestoreObj(file);
        StringBuilder tmp_new = new StringBuilder();
        if(file != null) {
            try {
                tmp = RestObj.run();
                for (int i = 0; i < tmp.getIp().length; i++) {
                    if (i == tmp.getIp().length - 1) {
                        tmp_new.append(tmp.getIp()[i]);
                        break;
                    }
                    tmp_new.append(tmp.getIp()[i]).append(".");
                }
                MASK.setText(Integer.toString(tmp.getMaskShort()));
                SUBNET.setText(tmp_new.toString());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Успех");
                alert.setHeaderText(null);
                alert.setContentText(" Объект восстановлен");
                alert.showAndWait();
            } catch (FileNotFoundException ex) {
                log.error("Ошибка при открытии файла!", ex);
                ex.getStackTrace();
            } catch (ClassNotFoundException ex) {
                log.error("Ошибка при открытии файла: Класс не найден ", ex);
                ex.getStackTrace();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText(" Ошибка при попытки восстановления: объект был изменен ");
                alert.showAndWait();
            }
        }
    }
    private int check_mask() {
        String mask=MASK.getText();
         if(mask== null || mask.length() == 0) {
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Ошибка");
              alert.setHeaderText(null);
              alert.setContentText(" Введите маску! ");
              alert.showAndWait();
          }
         else { int mask_int=0;
               try{
                  mask_int=Integer.parseInt(mask);
                  }
               catch(NumberFormatException e)
                  {
                log.error("check_mask(): Parse_mask_error");
                  }
               //int mask_int=Integer.parseInt(mask);
              if (mask_int<=0 || mask_int>32 ) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText(" Маска должна быть в диапозоне от 1 до 32 ");
                alert.showAndWait();
                }
              else {
                  tmp.set_mask(mask_int);
                  return 0;
              }
            //  tmp.set_mask(mask_int);
           }
        return -1;
    }// end function
    
 private int check_subnet(){
         String subnet=SUBNET.getText();
         String [] str;
          char a =',';
          char b= '.';
         int [] subnet_network=new int[4];
          if( subnet.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Ошибка");
              alert.setHeaderText(null);
              alert.setContentText(" Введите подсеть! ");
              alert.showAndWait();
              return -1;
           }
           else {
               subnet=subnet.replace(a, b);
               SUBNET.setText(subnet);
               if(subnet.contains(".")) {
                str = subnet.split("[.]"); 
                if(str.length<=3) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText(null);
                    alert.setContentText("  октеты заполнены неверно!");
                    alert.showAndWait();
                    return -1;
                }
                    for(int i=0; i<=3;i++) {
                        try{
                        subnet_network[i]=Integer.parseInt(str[i]);
                           }
                        catch(NumberFormatException ed) {
                         log.error("check_subnet(): Parse_mask_error");
                               Alert alert = new Alert(Alert.AlertType.INFORMATION);
                               alert.setTitle("Ошибка");
                               alert.setHeaderText(null);
                               alert.setContentText("  Октеты содержат недопустимые значения!");
                               alert.showAndWait();
                               return -1;
                           }
                    }
                    for (int i=0; i<=3;i++) {
                      if (subnet_network[i] < 0 || subnet_network[i] > 255) {
                       Alert alert = new Alert(Alert.AlertType.INFORMATION);
                       alert.setTitle("Ошибка");
                       alert.setHeaderText(null);
                       alert.setContentText(" Октет должен быть в диапозоне от 0 до 255 ");
                       alert.showAndWait();
                       return -1;
                      }
                     }
               }
                 else {
                       Alert alert = new Alert(Alert.AlertType.INFORMATION);
                       alert.setTitle("Ошибка");
                       alert.setHeaderText(null);
                       alert.setContentText(" Подсеть задана не верно! ");
                       alert.showAndWait();
                     return -1;
                  }
           } //end else
          tmp.set_ip(subnet_network);
           return 0;
     }//end function
}

