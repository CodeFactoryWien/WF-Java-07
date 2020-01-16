package university;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        /*Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cr6_barnabas_doebroessy_school?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "admin");

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * from teachers");

        while (rs.next()) {
            System.out.println("Name : " + rs.getString(2));
        }

        con.close();
        st.close();*/

        String str = "hello-123";

        String newStr = str.split("-")[0];
        System.out.println(newStr);

        AnchorPane logInPane = (AnchorPane) FXMLLoader.load(getClass().getResource("logInFirstScene.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(logInPane, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
