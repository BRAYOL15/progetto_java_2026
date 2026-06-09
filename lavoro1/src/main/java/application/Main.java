package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            File fxmlFile = new File("src/main/java/application/PrenotazioneVoli.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
            Parent root = loader.load();
            Scene scene = new Scene(root, 1200, 800);
            primaryStage.setTitle("BRADAR - Sistema Prenotazioni Voli");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}