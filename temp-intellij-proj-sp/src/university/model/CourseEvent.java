package university.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CourseEvent {
    Integer id;
    String roomName;
    Date registrationDeadline;
    List<CourseUnit> courseUnits = new ArrayList<>();
    List<Person> professors = new ArrayList<>();
    List<Person> students = new ArrayList<>();

    public CourseEvent(Integer id, String roomName, Date registrationDeadline) {
        this.id = id;
        this.roomName = roomName;
        this.registrationDeadline = registrationDeadline;
    }

    public Integer getId() { return id; }
    public String getRoomName() { return roomName; }
    public Date getRegistrationDeadline() { return registrationDeadline; }
    public List<CourseUnit> getCourseUnits() { return courseUnits; }
    public List<Person> getProfessors() { return professors; }
    public List<Person> getStudents() { return students; }
}
