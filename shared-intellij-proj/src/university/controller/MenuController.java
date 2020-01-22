package university.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import university.db.DataService;
import java.net.URL;
import java.sql.*;
import java.io.IOException;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    DataService db;
    public void setDb(DataService db){this.db = db;}
    //First scene - Nodes

    @FXML
    private ImageView logInScreenImage;
    @FXML
    private TextField logInTextField;
    @FXML
    private Button logInButton;

    //Log in button - Action event

    @FXML
    void logInAction(ActionEvent event) throws Exception {
        String[] split = logInTextField.getText().split("_");
        if (!( split[0].equals(""))) {
            String firstName = split[0];
            String lastName = split[1];
            ResultSet searchForUser = db.resultSet("SELECT * FROM professors WHERE professor_name = '" + firstName + "' AND professor_surname = '" + lastName + "'");
            try {
                if (searchForUser.next()) {
                    FXMLLoader menuViewLoader = new FXMLLoader(getClass().getResource("controller/MenuView.fxml"));
                    Parent menuViewRoot = menuViewLoader.load();
                    MenuController menuViewController = menuViewLoader.getController();

                    menuViewController.setDb(db);
                } else {
                    System.out.println("not connected!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("no user name found!");
        }
    }

    //Second scene - Nodes

    @FXML
    private JFXButton overviewButton;
    @FXML
    private JFXButton coursesButton;
    @FXML
    private JFXButton studentsButton;
    @FXML
    private JFXButton gradesButton;

    //Pane to field Views

    @FXML
    private AnchorPane rootPane;

    //Second scene - Action events

    @FXML
    void coursesActionButton(ActionEvent event) throws IOException {
        FXMLLoader gcViewLoader = new FXMLLoader(getClass().getResource("CourseView.fxml"));
        SplitPane coursesPane = gcViewLoader.load();
        CourseView gcViewController = gcViewLoader.getController();

        gcViewController.setDb(db);
        gcViewController.loadData();
        gcViewController.wireElements();
        rootPane.getChildren().setAll(coursesPane);

    }
    @FXML
    void gradesActionButton(ActionEvent event) throws IOException {
        FXMLLoader gViewLoader = new FXMLLoader(getClass().getResource("GradingForm.fxml"));
        SplitPane gradingPane = gViewLoader.load();
        GradingForm gViewController = gViewLoader.getController();

        gViewController.setDb(db);
        gViewController.loadData();
        gViewController.wireElements();
        rootPane.getChildren().setAll(gradingPane);
    }
    @FXML
    void overviewActionButton(ActionEvent event) {

    }
    @FXML
    void studentsActionButton(ActionEvent event) throws IOException {
        FXMLLoader sViewLoader = new FXMLLoader(getClass().getResource("StudentView.fxml"));
        StackPane studentsPane = sViewLoader.load();
        StudentView sViewController = sViewLoader.getController();

        sViewController.setDb(db);
        sViewController.loadData();
        sViewController.wireElements();
        rootPane.getChildren().setAll(studentsPane);
    }

}