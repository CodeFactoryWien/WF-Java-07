package university;

import com.sun.javafx.image.impl.General;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import university.controller.GeneralCourseView;
import university.db.DataService;
import university.model.Course;

import javax.naming.ldap.Control;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        var db = new DataService("university");
        primaryStage.setOnCloseRequest(e -> db.close());

        FXMLLoader gcViewLoader = new FXMLLoader(getClass().getResource("controller/generalCourseView.fxml"));
        Parent     gcViewRoot = gcViewLoader.load();
        GeneralCourseView gcViewController = gcViewLoader.getController();

        gcViewController.setDb(db);
        gcViewController.loadData();
        gcViewController.wireElements();




        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(gcViewRoot));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
