package university;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import university.controller.MenuController;
import university.db.DataService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        var props = getAppProps();
        var db = new DataService(
                props.getProperty("dbname"),
                props.getProperty("dbuser"),
                props.getProperty("dbpass"));

        primaryStage.setOnCloseRequest(e -> db.close());

        FXMLLoader logInViewLoader = new FXMLLoader(getClass().getResource("controller/LogInView.fxml"));
        Parent root = logInViewLoader.load();
        MenuController logInViewController = logInViewLoader.getController();

        logInViewController.setDb(db);
        Image icon = new Image(getClass().getResourceAsStream("/images/mickey_gryffindor.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("University of Magic and Higher Sorcery");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Properties getAppProps() throws IOException {
        Properties props = new Properties();
        var in = getClass().getResourceAsStream("localdev.props");
        props.load(in);
        in.close();
        return props;
    }
}
