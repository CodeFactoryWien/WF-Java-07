package university.db;

public class Queries {
    static String getAll(String tableName) {
        return "select * from " + tableName;
    }

    static String getCoursesData() {
        return "select course_id, course_title, course_description, course_type_name, course_module_code, course_credit_points " +
                "from courses join course_types using (course_type_id)";
    }

    static String getCourseEventsOfCourse(Integer courseId) {
        return "select course_event_id, room_name, registration_deadline" + // TODO: verify that `room_name` is correct
                "from course_events join rooms using (room_id) where course_id = " + courseId;
    }

    static String getCourseUnitsOfCourseEvent(Integer eventId) {
        return "select course_unit_id, course_unit_date, course_unit_time from course_units where course_event_id = " + eventId;
    }

    private Queries() {};
}
