package university.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import university.db.DataService;
import university.model.Course;
import university.model.CourseEvent;
import university.model.Person;

public class CourseView {
    @FXML ListView<Course> courseListView;

    @FXML TextField courseIdField;
    @FXML TextField courseTypeField;
    @FXML TextField courseModuleField;
    @FXML TextField coursePointsField;
    @FXML TextArea courseDescrField;
    @FXML Button restoreDescrBtn;
    @FXML Button updateDescrBtn;
    @FXML ListView<CourseEvent> courseEventsListView;

    @FXML TextField courseEventIdField;
    @FXML TextField courseEventRoomField;
    @FXML TextField courseEventRegDateField;
    @FXML TextField courseEventStartDateField;
    @FXML TextField courseEventEndDateField;
    @FXML ListView<Person> profsTeachingEventListView;
    @FXML ListView<Person> studsAttendingEventListView;

    DataService db;
    public void setDb(DataService db) { this.db = db; }

    public void loadData() {
        var courses = db.getCourses();
        courseListView.getItems().setAll(courses);
    }

    public void wireElements() {
        connectCourseListToCourseDetails();
        connectEventListToEventDetails();
        activateCourseDetailButtons();
    }

    private void connectCourseListToCourseDetails() {
        courseListView.getSelectionModel().selectedItemProperty().addListener((obs, oval, nval) -> {
            if (nval == null) {
                clearAll();
            } else {
                db.addCourseEventDataToCourse(nval);

                courseIdField.setText(Integer.toString(nval.getId()));
                courseTypeField.setText(nval.getType().name());
                courseModuleField.setText(nval.getModuleCode());
                coursePointsField.setText(Integer.toString(nval.getCreditPoints()));
                courseDescrField.setText(nval.getDescription());

                courseEventsListView.getItems().setAll(nval.getCourseEvents());
            }
        });
    }

    private void clearAll() {
        clearCourseDetails();
        clearEventDetails();
    }

    private void clearCourseDetails() {
        courseIdField.setText("");
        courseTypeField.setText("");
        courseModuleField.setText("");
        coursePointsField.setText("");
        courseDescrField.setText("");
        courseEventsListView.getItems().clear();
    }

    private void clearEventDetails() {
        courseEventIdField.setText("");
        courseEventRoomField.setText("");
        courseEventRegDateField.setText("");
        courseEventStartDateField.setText("");
        courseEventEndDateField.setText("");
        profsTeachingEventListView.getItems().clear();
        studsAttendingEventListView.getItems().clear();
    }

    private void activateCourseDetailButtons() {
        restoreDescrBtn.setOnAction(e ->
                courseDescrField.setText(
                        courseListView.getSelectionModel().getSelectedItem().getDescription()));
        updateDescrBtn.setOnAction(e -> {
            String newDescr = courseDescrField.getText();
            db.updateCourseDescription(
                    courseListView.getSelectionModel().getSelectedItem().getId(),
                    newDescr);
            courseListView.getSelectionModel().getSelectedItem().setDescription(newDescr);
            courseListView.refresh();
        });
    }

    private void connectEventListToEventDetails() {
        courseEventsListView.getSelectionModel().selectedItemProperty().addListener((obs, oval, nval) -> {
            if (nval == null) return;

            courseEventIdField.setText(Integer.toString(nval.getId()));
            courseEventRoomField.setText(nval.getRoomName());
            courseEventRegDateField.setText(nval.getRegistrationDeadline().toString());
            courseEventStartDateField.setText(nval.getStartDate().toString());
            courseEventEndDateField.setText(nval.getEndDate().toString());

            profsTeachingEventListView.getItems().setAll(nval.getProfessors());
            studsAttendingEventListView.getItems().setAll(nval.getStudents());
        });
    }
}
