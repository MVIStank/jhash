package jhash;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.net.URL;


public class Jhash extends Application {
    private final static Logger log = LogManager.getLogger();

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource( "FXMLDocument.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        stage.setTitle("IP calculator");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        log.info("======Start program======");
        launch(args);


       //work_ip tmp=new work_ip ();
       //tmp.go();
    }
}

