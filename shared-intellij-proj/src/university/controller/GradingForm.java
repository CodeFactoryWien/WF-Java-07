package university.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import university.db.DataService;
import university.model.Course;
import university.model.Grade;
import university.model.Person;

public class GradingForm {
    @FXML TextField courseListFilter;
    FilteredList<Course> filteredCourses;
    @FXML Button clearCourseFilterBtn;
    @FXML ListView<Course> courseListView;

    @FXML TextField studListFilter;
    FilteredList<Person> filteredStuds;
    @FXML Button clearStudFilterBtn;
    @FXML ListView<Person> studentListView;

    @FXML TextField studNameField;
    @FXML TextField courseNameField;
    @FXML TextField gradeValueField;
    @FXML TextArea gradeCommentField;
    @FXML Button clearBtn;
    @FXML Button restoreBtn;
    @FXML Button saveBtn;
    @FXML Label formErrorMsg;

    DataService db;
    public void setDb(DataService db) { this.db = db; }

    public void loadData() {
        ObservableList<Course> courses = FXCollections.observableArrayList(db.getCourses());
        filteredCourses = new FilteredList<>(courses, el -> true);
        courseListView.setItems(filteredCourses);

        ObservableList<Person> studs = FXCollections.observableArrayList(db.getStudents());
        filteredStuds = new FilteredList<>(studs, el -> true);
        studentListView.setItems(filteredStuds);
    }

    public void wireElements() {
        connectListViewsToForm();
        connectCourseListFilterToCourseList();
        connectStudListFilterToCourseList();
        activateFormBtns();
    }

    private void connectCourseListFilterToCourseList() {
        courseListFilter.textProperty().addListener(change -> {
            if(courseListFilter.getText().isEmpty()) {
                filteredCourses.setPredicate(el -> true);
            } else {
                filteredCourses.setPredicate(el ->
                        el.getTitle().toLowerCase().contains(courseListFilter.getText().toLowerCase()));
            }
        });
        clearCourseFilterBtn.setOnAction(e -> courseListFilter.setText(""));
    }

    private void connectStudListFilterToCourseList() {
        studListFilter.textProperty().addListener(change -> {
            if(studListFilter.getText().isEmpty()) {
                filteredStuds.setPredicate(el -> true);
            } else {
                filteredStuds.setPredicate(el ->
                        el.getName().toLowerCase().contains(studListFilter.getText().toLowerCase()) ||
                                el.getSurname().toLowerCase().contains(studListFilter.getText().toLowerCase()));
            }
        });
        clearStudFilterBtn.setOnAction(e -> studListFilter.setText(""));
    }


    private void connectListViewsToForm() {
        courseListView.getSelectionModel().selectedItemProperty().addListener((x, old, nu) -> {
            formErrorMsg.setVisible(false);
            clearFields();
            if (nu != null && studentListView.getSelectionModel().getSelectedItem() != null) {
                fillForm();
            }
        });
        studentListView.getSelectionModel().selectedItemProperty().addListener((x, old, nu) -> {
            formErrorMsg.setVisible(false);
            clearFields();
            if (nu != null && courseListView.getSelectionModel().getSelectedItem() != null) {
                fillForm();
            }
        });
    }

    private void activateFormBtns() {
        clearBtn.setOnAction(e -> clearForm());
        restoreBtn.setOnAction(e -> restoreForm());
        saveBtn.setOnAction(e -> sendForm());
    }

    private void fillForm() {
        Person selectedStudent = studentListView.getSelectionModel().getSelectedItem();
        Course selectedCourse  = courseListView.getSelectionModel().getSelectedItem();
        studNameField.setText(selectedStudent.toString());
        courseNameField.setText(selectedCourse.toString());

        Grade grade = db.getGrade(selectedStudent.getId(), selectedCourse.getId());
        if (grade != null) {
            gradeValueField.setText(Integer.toString(grade.getValue()));
            gradeCommentField.setText(grade.getComment());
        }
    }

    private void clearForm() {
        clearFields();
        studentListView.getSelectionModel().clearSelection();
        courseListView.getSelectionModel().clearSelection();
    }

    private void clearFields() {
        formErrorMsg.setVisible(false);
        studNameField.setText("");
        courseNameField.setText("");
        gradeValueField.setText("");
        gradeCommentField.setText("");
    }

    private void restoreForm() {
        clearFields();
        fillForm();
    }

    private void sendForm() {
        if(gradeValueField.getText().strip().matches("^[0-5]$")) {
            formErrorMsg.setVisible(false);
            db.addOrUpdateGrading(
                    studentListView.getSelectionModel().getSelectedItem().getId(),
                    courseListView.getSelectionModel().getSelectedItem().getId(),
                    Integer.parseInt(gradeValueField.getText()),
                    gradeCommentField.getText()
            );
            clearForm();
        } else {
            formErrorMsg.setVisible(true);
        }
    }
}
