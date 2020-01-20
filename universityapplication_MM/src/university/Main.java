package university;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("logInFirstScene.fxml"));
        Image icon = new Image(getClass().getResourceAsStream("/images/mickey_gryffindor.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("University of Magic and Higher Sorcery");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
