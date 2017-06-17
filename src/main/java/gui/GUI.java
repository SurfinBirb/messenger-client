package gui;/**
 * Created by SurfinBirb on 17.05.2017.
 */

import gui.forms.ClientFormController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Interface;
import model.Message;

import java.nio.file.Paths;

import static java.lang.Thread.sleep;

public class GUI extends Application {

    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Interface.turnOn();
            Interface.loggedProperty().addListener((observable, oldValue, newValue) -> onLogin());
            drawAuthorizationForm(primaryStage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void drawAuthorizationForm(Stage stage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Paths.get("src","main","java","gui","forms","AuthorizationForm.fxml").toUri().toURL());
        AnchorPane loginForm = (AnchorPane) fxmlLoader.load();
        Scene scene = new Scene(loginForm);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Authorization");
        this.stage = stage;
        stage.show();
    }

    private void clientForm() throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Paths.get("src","main","java","gui","forms","ClientForm.fxml").toUri().toURL());
        AnchorPane clientForm = (AnchorPane) fxmlLoader.load();
        Scene scene = new Scene(clientForm);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.setTitle("Client");
        stage.show();
    }

    public void onLogin(){
        Platform.runLater(() -> {
            stage.close();
            try {
                clientForm();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

}
