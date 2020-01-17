package university.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import university.db.DataService;
import university.model.Course;

public class GeneralCourseView {
    @FXML ListView<Course> courseListView;

    @FXML TextField idField;
    @FXML TextField typeField;
    @FXML TextField moduleField;
    @FXML TextField pointsField;
    @FXML TextField descrField;

    DataService db;
    public void setDb(DataService db) { this.db = db; }

    public void loadData() {
        var courses = db.getCourses();
        courseListView.getItems().setAll(courses);
    }

    public void wireElements() {
        connectCLViewToTextFields();
    }

    private void connectCLViewToTextFields() {
        courseListView.getSelectionModel().selectedItemProperty().addListener((obs, oval, nval) -> {
            idField.setText(Integer.toString(nval.getId()));
            typeField.setText(nval.getType().name());
            moduleField.setText(nval.getModuleCode());
            pointsField.setText(Integer.toString(nval.getCreditPoints()));
            descrField.setText(nval.getDescription());
        });
    }
}
