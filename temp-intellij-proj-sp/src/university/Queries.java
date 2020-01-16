package university;

public class Queries {
    static String getAll(String tableName) {
        return "select * from " + tableName;
    }

    static String getCoursesData() {
        return "select course_id, course_title, course_description, course_type_name, course_module_code, course_credit_points " +
                "from courses join course_types using (course_type_id)";
    }

    static String getCourseEventsOfCourse(Integer courseId) {
        return "select course_event_id,  from course_events join rooms using (room_id) where course_id = " +
                courseId;
    }

    private Queries() {};
}
