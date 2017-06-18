package gui.forms;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Interface;
import model.Message;
import model.Room;

import java.nio.file.Paths;
import java.util.LinkedList;

/**
 * Created by SurfinBirb on 20.05.2017.
 */
public class ClientFormController {

    @FXML
    public Button sendButton;

    @FXML
    public TextArea textField;

    @FXML
    public ListView<Message> messageListView;

    @FXML
    public ListView<Room> roomListView;

    @FXML
    public Button newRoom;

    private Long currentRoomId;

    private ObservableList<Room> roomObservableList = FXCollections.observableList(new LinkedList<Room>());

    private Stage newRoomStage;

    public void initialize() {

        MapChangeListener<Long, ListProperty<Message>> newMapChangeListener = change -> { // map listener
            if(change.wasAdded()){
                roomObservableList.add(Interface.getRoomMap().get(change.getKey()));
                Platform.runLater(() -> {
                    if(newRoomStage != null) newRoomStage.close();
                    newRoom.setDisable(false);
                });
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

        final ContextMenu contextMenu = new ContextMenu();
        MenuItem invite = new MenuItem("Invite");
        invite.setOnAction(actionEvent -> Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(Paths.get("src", "main", "java", "gui", "forms", "InviteForm.fxml").toUri().toURL());
                AnchorPane clientForm = (AnchorPane) fxmlLoader.load();
                Scene scene = new Scene(clientForm);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.setTitle(roomListView.getSelectionModel().getSelectedItem().getRoomName());
                InviteForm controller = fxmlLoader.getController();
                controller.setRoomId(roomListView.getSelectionModel().getSelectedItem().getRoomId());
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        contextMenu.getItems().addAll(invite);
        roomListView.setContextMenu(contextMenu);

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

    public void newRoom(ActionEvent actionEvent){
        newRoom.setDisable(true);
        try {
            Platform.runLater(() -> {
                try {
                    Stage stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(Paths.get("src", "main", "java", "gui", "forms", "NewRoomForm.fxml").toUri().toURL());
                    AnchorPane clientForm = (AnchorPane) fxmlLoader.load();
                    Scene scene = new Scene(clientForm);
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.setTitle("New Room");
                    stage.show();
                    stage.setOnCloseRequest(we -> newRoom.setDisable(false));
                    newRoomStage = stage;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
