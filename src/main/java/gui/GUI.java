package gui;/**
 * Created by SurfinBirb on 17.05.2017.
 */

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Interface;

import java.io.File;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Interface.turnOn();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Paths.get("src","main","java","gui","forms","AuthorizationForm.fxml").toUri().toURL());
            AnchorPane loginForm = (AnchorPane) fxmlLoader.load();
            Scene scene = new Scene(loginForm);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Authorization");
            primaryStage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void runGui(String[] args){
        main(args);
    }
}
