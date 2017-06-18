package gui.forms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Interface;

/**
 * Created by SurfinBirb on 18.06.2017.
 */
public class NewRoomForm {

    @FXML
    public TextField roomNameField;

    @FXML
    public Button roomNameButton;

    public void initialize(){
        roomNameField.setPromptText("Room name");
    }

    public void roomCreationAttempt(ActionEvent actionEvent) {
        System.out.println("kek");
        if (roomNameField.getText() != null) Interface.tryToCreateNewRoom(roomNameField.getText());
    }


}
