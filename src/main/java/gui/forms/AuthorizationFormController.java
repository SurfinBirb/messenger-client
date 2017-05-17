package gui.forms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import model.Interface;


/**
 * Created by SurfinBirb on 17.05.2017.
 */
public class AuthorizationFormController {

    @FXML
    TextField loginField;

    @FXML
    PasswordField passwordField;

    @FXML
    Button loginButton;

    public AuthorizationFormController() {
    }

    @FXML
    public void onButtonAction(ActionEvent actionEvent) {
        Interface.sendAuthData(loginField.getText(), passwordField.getText());
    }
}
