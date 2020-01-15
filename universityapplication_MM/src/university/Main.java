package university;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane logInPane = (AnchorPane) FXMLLoader.load(getClass().getResource("logInFirstScene.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(logInPane, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
