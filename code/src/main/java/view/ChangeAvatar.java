package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.AA;

import java.io.File;

public class ChangeAvatar extends Application {
    private static Stage stage;
    @FXML
    private Circle avatar1;
    @FXML
    private Circle avatar2;
    @FXML
    private Circle avatar3;
    @FXML
    private Circle avatar4;
    @FXML
    private Circle avatar5;
    @FXML
    private Circle avatar6;
    @FXML
    private Circle avatar7;
    @FXML
    private Circle avatar8;


    @Override
    public void start(Stage stage) throws Exception {
        ChangeAvatar.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(ChangeAvatar.class.getResource("changeAvatar.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("change avatar");
        stage.show();
    }

    @FXML
    public void initialize(){
        avatar1.setFill(new ImagePattern(new Image(ChangeAvatar.class.getResource("/images/avatar1.jpeg").toExternalForm())));
        avatar2.setFill(new ImagePattern(new Image(ChangeAvatar.class.getResource("/images/avatar2.jpeg").toExternalForm())));
        avatar3.setFill(new ImagePattern(new Image(ChangeAvatar.class.getResource("/images/avatar3.jpeg").toExternalForm())));
        avatar4.setFill(new ImagePattern(new Image(ChangeAvatar.class.getResource("/images/avatar4.jpeg").toExternalForm())));
        avatar5.setFill(new ImagePattern(new Image(ChangeAvatar.class.getResource("/images/avatar5.jpeg").toExternalForm())));
        avatar6.setFill(new ImagePattern(new Image(ChangeAvatar.class.getResource("/images/avatar6.jpeg").toExternalForm())));
        avatar7.setFill(new ImagePattern(new Image(ChangeAvatar.class.getResource("/images/avatar7.jpeg").toExternalForm())));
        avatar8.setFill(new ImagePattern(new Image(ChangeAvatar.class.getResource("/images/avatar8.jpeg").toExternalForm())));
    }

    public void choose1() throws Exception{
        AA.getCurrentUser().setAvatar("/images/avatar1.jpeg");
        end();
    }
    public void choose2() throws Exception{
        AA.getCurrentUser().setAvatar("/images/avatar2.jpeg");
        end();
    }
    public void choose3() throws Exception{
        AA.getCurrentUser().setAvatar("/images/avatar3.jpeg");
        end();
    }
    public void choose4() throws Exception{
        AA.getCurrentUser().setAvatar("/images/avatar4.jpeg");
        end();
    }
    public void choose5() throws Exception{
        AA.getCurrentUser().setAvatar("/images/avatar5.jpeg");
        end();
    }
    public void choose6() throws Exception{
        AA.getCurrentUser().setAvatar("/images/avatar6.jpeg");
        end();
    }
    public void choose7() throws Exception{
        AA.getCurrentUser().setAvatar("/images/avatar7.jpeg");
        end();
    }
    public void choose8() throws Exception {
        AA.getCurrentUser().setAvatar("/images/avatar8.jpeg");
        end();
    }

    public void end() throws Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("profile changed");
        alert.setTitle("Your profile has changed successfully!");
        alert.showAndWait();
        new ProfileMenu().start(ChangeAvatar.stage);
    }

    public void browse() throws Exception{
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Image File (*.jpeg), (*.jpg), (*.png)",
                "*.jpeg", "*.jpg", "*.png");
        fileChooser.setTitle("select profile photo");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            AA.getCurrentUser().setAvatar(file.toURI().toString());
            end();
        }
    }

    public void back() throws Exception{
        new ProfileMenu().start(stage);
    }
}
