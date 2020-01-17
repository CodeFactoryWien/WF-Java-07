package university.model;

public enum CourseType {
    LECTURE, SEMINAR;

    public String getAbbrev() {
        return name().substring(0, 2);
    }
}
