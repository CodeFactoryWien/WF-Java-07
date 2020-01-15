package university;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private TextField userNameTextField;

    @FXML
    private Button logInButton;

    @FXML
    void onClickAction(ActionEvent event) throws IOException {
        Parent secondStage = FXMLLoader.load(getClass().getResource("menuSecondScene.fxml"));
        Scene secondScene = new Scene(secondStage);
        Stage secondWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
        secondWindow.setScene(secondScene);
        secondWindow.show();
    }

    @FXML
    private TreeView<?> exampleTreeView;

}

