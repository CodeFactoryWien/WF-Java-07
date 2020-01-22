package university.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CourseEvent {
    Integer id;
    String roomName;
    Date registrationDeadline;
    List<Person> professors = new ArrayList<>();
    List<Person> students = new ArrayList<>();

    List<CourseUnit> courseUnits = new ArrayList<>();
    CourseUnit startUnit;
    CourseUnit endUnit;

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
    public Date getStartDate() { return getStartUnit().getDate(); }
    public Date getEndDate() { return getEndUnit().getDate(); }

    public CourseUnit getStartUnit() {
        if (startUnit == null) cacheStartAndEndUnits();
        return startUnit;
    }

    public CourseUnit getEndUnit() {
        if (endUnit == null) cacheStartAndEndUnits();
        return endUnit;
    }

    private void cacheStartAndEndUnits() {
        sortUnitsByDateAndTime();
        startUnit = courseUnits.get(0);
        endUnit = courseUnits.get(courseUnits.size() - 1);
    }

    private void sortUnitsByDateAndTime() {
        courseUnits.sort((u1, u2) -> {
            int dateComp = u1.getDate().compareTo(u2.getDate());
            if (dateComp == 0) {
                return u1.getTime().compareTo(u2.getTime());
            } else {
                return dateComp;
            }
        });
    }

    @Override
    public String toString() {
        return String.format("%s - %s", getStartDate(), getEndDate());
    }
}
