package university;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import university.controller.MenuController;
import university.db.DataService;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainB extends Application {/*
    public static DataService db = new DataService("university?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "admin");
    public static DataService getDb() { return db;}*/

    @Override
    public void start(Stage primaryStage) throws Exception{
        var db = new DataService("university?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "admin");
        primaryStage.setOnCloseRequest(e -> db.close());

        FXMLLoader logInViewLoader = new FXMLLoader(getClass().getResource("controller/LogInView.fxml"));
        Parent root = logInViewLoader.load();
        MenuController logInViewController = logInViewLoader.getController();

        logInViewController.setDb(db);
        /*Parent root = FXMLLoader.load(getClass().getResource("controller/LogInView.fxml"));*/
        Image icon = new Image(getClass().getResourceAsStream("/images/mickey_gryffindor.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("University of Magic and Higher Sorcery");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
