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
import university.db.DataService;

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
        if (!( split[0].equals(""))) {
            String firstName = split[0];
            String lastName = split[1];

            DataService ds = new DataService("university_application?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "admin");
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            ResultSet searchForUser = statement.executeQuery("SELECT * FROM professors WHERE professor_name = " +
                    "'" + firstName + "' AND professor_surname = '" + lastName + "'");
            try {
                if (searchForUser.next()) {
                    Parent secondWindow = FXMLLoader.load(getClass().getResource("menuSecondScene.fxml"));
                    Scene secondScene = new Scene(secondWindow);
                    Stage secondStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    secondStage.setScene(secondScene);
                    secondStage.show();
                } else {
                    System.out.println("not connected!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("no user name found");
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