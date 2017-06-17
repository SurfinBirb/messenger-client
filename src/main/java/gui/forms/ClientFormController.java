package gui.forms;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import model.Interface;
import model.Message;
import model.Room;

import java.util.LinkedList;

/**
 * Created by SurfinBirb on 20.05.2017.
 */
public class ClientFormController {

    @FXML
    public Button sendButton;

    @FXML
    public TextArea textField;

    public ListView<Message> getMessageListView() {
        return messageListView;
    }

    @FXML
    public ListView<Message> messageListView;

    @FXML
    public ListView<Room> roomListView;

    private Long currentRoomId;

    private ObservableList<Room> roomObservableList = FXCollections.observableList(new LinkedList<Room>());

    public void initialize() {

        MapChangeListener<Long, ListProperty<Message>> newMapChangeListener = change -> { // map listener
            if(change.wasAdded()){
                roomObservableList.add(Interface.getRoomMap().get(change.getKey()));
            }
        };

        Interface.roomObservableMap.addListener(newMapChangeListener);

        messageListView.setCellFactory(param -> new ListCell<Message>(){
            @Override
            protected void updateItem(Message message, boolean empty){
                super.updateItem(message, empty);

                if((message != null) || !empty){
                    Platform.runLater(() ->{
                        if(message != null) {
                            setText(message.getText());
                            setStyle("");
                        }
                    });
                }

                if ((message == null) || empty){
                    setGraphic(null);
                    setText(null);
                    setStyle(null);
                }
            }
        });

        roomListView.setCellFactory(param -> new ListCell<Room>(){
            @Override
            protected void updateItem(Room room, boolean empty){
                super.updateItem(room, empty);
                if((room != null) || !empty){
                        Platform.runLater(() -> {
                            if(room != null) {
                                setText(room.getRoomName());
                                setStyle("");
                            }
                        }
                    );
                }
            }
        });

        currentRoomId = Interface.defaultRoomId;

        messageListView.setItems(Interface.roomObservableMap.getOrDefault(Interface.defaultRoomId, new SimpleListProperty<>()).get());

        roomObservableList.addAll(Interface.getRoomMap().values());

        roomListView.setItems(roomObservableList);

    }

    public void onButtonAction(ActionEvent actionEvent) {
        if (!textField.getText().equals("")){
            Interface.sendMessage(currentRoomId, textField.getText());
            textField.setText("");
        }
    }

    public void onRoomListClick(MouseEvent mouseEvent){
        if(roomListView.getSelectionModel().getSelectedItem() != null) {
            currentRoomId = roomListView.getSelectionModel().getSelectedItem().getRoomId();
            messageListView.setItems(Interface.roomObservableMap.get(currentRoomId).get());
        }
    }

}
