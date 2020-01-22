package university;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import university.db.DataService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        var db = new DataService("university?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "admin");
        primaryStage.setOnCloseRequest(e -> db.close());
        Parent root = FXMLLoader.load(getClass().getResource("controller/LogInView.fxml"));
        Image icon = new Image(getClass().getResourceAsStream("/images/mickey_gryffindor.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("University of Magic and Higher Sorcery");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    public static ResultSet resultSet(String query) throws SQLException {
        DataService ds  =  new DataService("university?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "admin");
        Connection connection = ds.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet;
    }
}

