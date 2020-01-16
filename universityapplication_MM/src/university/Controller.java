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
import java.sql.*;

import java.io.IOException;
public class Controller {
    ResultSet searchForUser = new CreateConnection().resultSet("SELECT * FROM teachers");
    @FXML
    private TextField userNameTextField;

    @FXML
    private Button logInButton;

    public Controller() throws Exception {
    }

    @FXML
    void onClickAction(ActionEvent event){
        logInButton.setOnAction(actionEvent-> {
            String getID = userNameTextField.getText().trim();

            System.out.println(getID);
            try {
                while (searchForUser.next()) {
                    int a = searchForUser.getInt(1);
/*                    String name = searchForUser.getString(3);*/
                if (getID.substring(getID.length() - 1).equals(String.valueOf(a))) {
                    Parent secondWindow = null;
                    try {
                        secondWindow = FXMLLoader.load(getClass().getResource("menuSecondScene.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene secondScene = new Scene(secondWindow);
                    Stage secondStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    secondStage.setScene(secondScene);
                    secondStage.show();
                }
            }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    private TreeView<?> exampleTreeView;
}