package university.model;

import java.sql.Date;
import java.sql.Time;

public class CourseUnit {
    Integer id;
    Date date;
    Time time;

    public CourseUnit(Integer id, Date date, Time time) {
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public Integer getId() { return id; }
    public Date getDate() { return date; }
    public Time getTime() { return time; }
}
