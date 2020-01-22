package university.model;

public class StudentGrade {
    private String courseName;
    private Integer studentGrade;

    public StudentGrade(String courseName, Integer studentGrade) {
        this.courseName = courseName;
        this.studentGrade = studentGrade;
    }
    public String getCourseName() { return courseName; }
    public Integer getStudentGrade() { return studentGrade; }

    @Override
    public String toString(){
        return courseName + ":" + studentGrade;
    }
}
