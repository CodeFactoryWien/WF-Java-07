package university.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import university.db.DataService;
import university.model.Course;
import university.model.CourseEvent;
import university.model.Person;

import java.util.List;

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

    @FXML Label profsAtEventListLabel;
    @FXML Button rmProfBtn;
    @FXML Button addProfBtn;
    @FXML ListView<Person> profsTeachingEventListView;

    @FXML Label studsAtEventListLabel;
    @FXML Button rmStudBtn;
    @FXML Button addStudBtn;
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
        activateEventDetailButtons();
    }

    private void connectCourseListToCourseDetails() {
        courseListView.getSelectionModel().selectedItemProperty().addListener((obs, oval, nval) -> {
            clearAll();
            if (nval != null) {
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

    private void activateEventDetailButtons() {
        rmProfBtnActivateRemoveMode();
        addProfBtn.setOnAction(e -> enterAddProfMode());

        rmStudBtnActivateRemoveMode();
        addStudBtn.setOnAction(e -> enterAddStudMode());
    }

    private void enterAddProfMode() {
        CourseEvent selectedCourseEvent = courseEventsListView.getSelectionModel().getSelectedItem();
        if(selectedCourseEvent == null) return;
        List<Person> profsNotDoingEvent = db.getProfsNotDoingEvent(selectedCourseEvent.getId());

        profsAtEventListLabel.setText("Professors available for selection: ");
        addProfBtn.setText("Add Selected");
        rmProfBtn.setText("Abort");

        profsTeachingEventListView.getItems().setAll(profsNotDoingEvent);

        addProfBtn.setOnAction(e -> {
            Person selectedProf = profsTeachingEventListView.getSelectionModel().getSelectedItem();
            if (selectedProf != null) {
                db.addProfToCourseEvent(selectedProf.getId(), selectedCourseEvent.getId());
                selectedCourseEvent.getProfessors().add(selectedProf);
                exitAddProfMode();
            }
        });
        rmProfBtn.setOnAction(e -> exitAddProfMode());
    }

    private void exitAddProfMode() {
        profsAtEventListLabel.setText("Professors registered as teaching: ");
        addProfBtn.setText("Add New");
        rmProfBtn.setText("Remove Selected");

        profsTeachingEventListView.getItems().setAll(
                courseEventsListView.getSelectionModel().getSelectedItem().getProfessors());

        rmProfBtnActivateRemoveMode();
        addProfBtn.setOnAction(e -> enterAddProfMode());
    }

    private void rmProfBtnActivateRemoveMode() {
        rmProfBtn.setOnAction(e -> {
            Person selectedProf = profsTeachingEventListView.getSelectionModel().getSelectedItem();
            if (selectedProf != null) {
                CourseEvent selectedCourseEvent = courseEventsListView.getSelectionModel().getSelectedItem();
                db.deleteProfFromCourseEvent(selectedProf.getId(), selectedCourseEvent.getId());
                selectedCourseEvent.getProfessors().remove(selectedProf);
                profsTeachingEventListView.getItems().remove(selectedProf);
            }
        });
    }

    private void enterAddStudMode() {
        CourseEvent selectedCourseEvent = courseEventsListView.getSelectionModel().getSelectedItem();
        if(selectedCourseEvent == null) return;
        List<Person> studsNotDoingEvent = db.getStudsNotDoingEvent(selectedCourseEvent.getId());

        studsAtEventListLabel.setText("Students available for selection: ");
        addStudBtn.setText("Add Selected");
        rmStudBtn.setText("Abort");

        studsAttendingEventListView.getItems().setAll(studsNotDoingEvent);

        addStudBtn.setOnAction(e -> {
            Person selectedStud = studsAttendingEventListView.getSelectionModel().getSelectedItem();
            if (selectedStud != null) {
                db.addStudToCourseEvent(selectedStud.getId(), selectedCourseEvent.getId());
                selectedCourseEvent.getStudents().add(selectedStud);
                exitAddStudMode();
            }
        });
        rmStudBtn.setOnAction(e -> exitAddStudMode());
    }

    private void exitAddStudMode() {
        studsAtEventListLabel.setText("Students registered as attending: ");
        addStudBtn.setText("Add New");
        rmStudBtn.setText("Remove Selected");

        studsAttendingEventListView.getItems().setAll(
                courseEventsListView.getSelectionModel().getSelectedItem().getStudents());

        rmStudBtnActivateRemoveMode();
        addStudBtn.setOnAction(e -> enterAddStudMode());
    }

    private void rmStudBtnActivateRemoveMode() {
        rmStudBtn.setOnAction(e -> {
            Person selectedStud = studsAttendingEventListView.getSelectionModel().getSelectedItem();
            if (selectedStud != null) {
                CourseEvent selectedCourseEvent = courseEventsListView.getSelectionModel().getSelectedItem();
                db.deleteStudFromCourseEvent(selectedStud.getId(), selectedCourseEvent.getId());
                selectedCourseEvent.getStudents().remove(selectedStud);
                studsAttendingEventListView.getItems().remove(selectedStud);
            }
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
