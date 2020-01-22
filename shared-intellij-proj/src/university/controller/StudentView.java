package university.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import university.db.DataService;
import university.model.StudentGrade;
import university.model.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentView {

    public StudentView() throws SQLException {}

    //Students scene - nodes

    @FXML
    private JFXButton studentUpdateButton;
    @FXML
    private Label studentInfoButton;
    @FXML
    private JFXTextField studentIdField;
    @FXML
    private JFXTextField studentNameField;
    @FXML
    private JFXTextField studentSurnameField;
    @FXML
    private JFXTextField studentEmailField;
    @FXML
    private JFXTextField studentPhoneField;
    @FXML
    private JFXButton studentRestoreButton;

    //Listviews

    @FXML
    private JFXListView<StudentGrade> studentGradeList;
    @FXML
    private JFXListView<Person> studentsListView;

    //Methods to connect db with ListViews

    DataService db;
    public void setDb(DataService db){this.db = db;}

    public void loadData() {
        var students = db.getStudents();
        studentsListView.getItems().setAll(students);
    }
    public void wireElements() {
        connectListToDatabase();
    }

    public void connectListToDatabase(){
        studentsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                studentGradeList.getItems().clear();
                studentIdField.setText(Integer.toString(newValue.getId()));
                studentNameField.setText(newValue.getName());
                studentSurnameField.setText(newValue.getSurname());
                studentEmailField.setText(newValue.getEmail());
                studentPhoneField.setText(newValue.getPhone());

                int studentId = studentsListView.getSelectionModel().getSelectedItem().getId();
                System.out.println(studentId);
                DataService ds = new DataService("university?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "admin");
                Connection connection = ds.getConnection();
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT c.course_title, g.grade FROM gradings g JOIN courses c USING(course_id)" +
                        "JOIN students s USING(student_id) WHERE s.student_id = " + studentId);
                ObservableList<StudentGrade> gradings = FXCollections.observableArrayList();
                while (resultSet.next()){
                    String courseName = resultSet.getString(1);
                    Integer studentGrade = resultSet.getInt(2);
                    gradings.add(new StudentGrade(courseName, studentGrade));
                }studentGradeList.getItems().addAll(gradings);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
    }

}
