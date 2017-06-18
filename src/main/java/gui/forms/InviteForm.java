package gui.forms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Interface;

/**
 * Created by SurfinBirb on 18.06.2017.
 */
public class InviteForm {

    @FXML
    public Button inviteButton;

    @FXML
    public TextField nameField;

    private Long roomId;

    public void tryToInvite(ActionEvent actionEvent) {
        if (nameField.getText().matches("^[0-9]+$") && nameField.getText().length() < 64) Interface.sendRoomInvitation(roomId, Long.decode(nameField.getText()));
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
