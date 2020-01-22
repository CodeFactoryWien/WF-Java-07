package university;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import university.controller.CourseView;
import university.controller.GradingForm;
import university.db.DataService;

public class MainS extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        var db = new DataService("university", "root", "");
        primaryStage.setOnCloseRequest(e -> db.close());

        FXMLLoader courseViewLoader = new FXMLLoader(getClass().getResource("controller/CourseView.fxml"));
        Parent     courseViewRoot = courseViewLoader.load();
        CourseView courseViewController = courseViewLoader.getController();

        courseViewController.setDb(db);
        courseViewController.loadData();
        courseViewController.wireElements();

        FXMLLoader  gradingFormLoader = new FXMLLoader(getClass().getResource("controller/GradingForm.fxml"));
        Parent      gradingFormRoot = gradingFormLoader.load();
        GradingForm gradingFormController = gradingFormLoader.getController();

        gradingFormController.setDb(db);
        gradingFormController.loadData();
        gradingFormController.wireElements();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(courseViewRoot));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
