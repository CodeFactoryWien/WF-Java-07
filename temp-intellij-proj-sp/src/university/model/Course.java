package university.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    Integer id;
    String title;
    String description;
    CourseType type;
    String moduleCode;
    Integer creditPoints;

    List<CourseEvent> courseEvents = new ArrayList<>();

    public Course(Integer id, String title, String description, CourseType type, String moduleCode, Integer creditPoints) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.moduleCode = moduleCode;
        this.creditPoints = creditPoints;
    }

    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public CourseType getType() { return type; }
    public String getModuleCode() { return moduleCode; }
    public Integer getCreditPoints() { return creditPoints; }

    public void setCourseEvents(List<CourseEvent> courseEvents) {
        this.courseEvents = courseEvents;
    }

    public void addCourseEvent(CourseEvent courseEvent) {
        courseEvents.add(courseEvent);
    }

    @Override
    public String toString() {
        return String.format("%d/%s %s %s", id, moduleCode, type.getAbbrev(), title);
    }
}
