package university.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import university.MainB;
import university.db.DataService;
import university.model.StudentGrade;
import university.model.Person;

import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
    @FXML
    private ImageView studentsImageView;

    Image bambi = new Image("images/bambi.png");
    Image donald = new Image("images/donald_duck.png");
    Image white = new Image("images/white.png");

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
            if (studentsListView.getSelectionModel().getSelectedItem().getId() == 1){
                studentsImageView.setImage(donald);
            }else if (studentsListView.getSelectionModel().getSelectedItem().getId() == 6){
                studentsImageView.setImage(bambi);
            }else{
                studentsImageView.setImage(white);
            }

            int studentId = studentsListView.getSelectionModel().getSelectedItem().getId();
            try {
                ResultSet res = db.resultSet("SELECT c.course_title, g.grade FROM gradings g JOIN courses c USING(course_id)\n" +
                        "JOIN students s USING(student_id) WHERE s.student_id = " + studentId);
                ObservableList<StudentGrade> gradings = FXCollections.observableArrayList();
                while (res.next()) {
                    String courseName = res.getString(1);
                    Integer studentGrade = res.getInt(2);
                    gradings.add(new StudentGrade(courseName, studentGrade));
                }
                studentGradeList.getItems().addAll(gradings);
            }catch (SQLException e) {
                e.printStackTrace();
            }
/*            if (studentId == 1){
                Image studentProfile = new Image("images/donald_duck.png");
                studentsImageView = new ImageView(studentProfile);
            }else if(studentId == 6){
                Image studentProfile1 = new Image("images/bambi.png");
                studentsImageView.setImage(studentProfile1);
            }else{
                Image studentProfile2 = new Image("images/white.png");
                studentsImageView.setImage(studentProfile2);
            }*/
        });
    }
}
