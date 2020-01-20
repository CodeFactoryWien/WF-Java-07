package university;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

import java.io.IOException;

public class Controller {

    //First scene - Nodes

    @FXML
    private ImageView logInScreenImage; //TODO: Make ImageView work with linked image work.

    @FXML
    private TextField logInTextField;

    @FXML
    private Button logInButton;

    //Log in button - Action event

    @FXML
    void logInAction(ActionEvent event) throws Exception { //TODO: Make a functioning try / catch statement.
        String[] split = logInTextField.getText().split("_");
        String firstName = split[0];
        String lastName = split[1];
            ResultSet searchForUser = new CreateConnection().resultSet("SELECT * FROM teachers WHERE name = " +
                                                            "'" + firstName + "' AND surname = '" + lastName + "'");
            try {
                if (searchForUser.next()) {
                    Parent secondWindow = FXMLLoader.load(getClass().getResource("menuSecondScene.fxml"));
                    Scene secondScene = new Scene(secondWindow);
                    Stage secondStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    secondStage.setScene(secondScene);
                    secondStage.show();
                } else if (logInTextField.getText() == null || logInTextField.getText().trim().length() == 0){
                    System.out.println("textfield is empty");
                } else {
                    System.out.println("not connected!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    //Second scene - Nodes

    @FXML
    private JFXButton overviewButton;

    @FXML
    private JFXButton coursEventsButton;

    @FXML
    private JFXButton studentsButton;

    @FXML
    private JFXButton gradesButton;

    //Second scene - Action events

    @FXML  // TODO: Write functions + Make them connected to database
    void courseEventsActionButton(ActionEvent event) {

    }

    @FXML
    void gradesActionButton(ActionEvent event) {

    }

    @FXML
    void overviewActionButton(ActionEvent event) {

    }

    @FXML
    void studentsActionButton(ActionEvent event) {

    }
}