package university.db;

public class ReadQueries {
    static String getAll(String tableName) {
        return "select * from " + tableName;
    }

    static String getCoursesData() {
        return "select course_id, course_title, course_description, course_type_name, course_module_code, course_credit_points " +
                "from courses join course_types using (course_type_id)";
    }

    static String getCourseEventsOfCourse(Integer courseId) {
        return "select course_event_id, room_name, registration_deadline " + // TODO: verify that `room_name` is correct
                "from course_events join rooms using (room_id) where course_id = " + courseId;
    }

    static String getProfsOfCourseEvent(Integer eventId) {
        return "select professor_id, professor_name, professor_surname, professor_email, professor_phone_number " +
                "from professors_doing_course_events pe join professors using (professor_id) " +
                "where pe.course_event_id = " + eventId;
    }

    static String getStudentsOfCourseEvent(Integer eventId) {
        return "select student_id, student_name, student_surname, student_email, student_phone_number " +
                "from students_attending_course_events se join students using (student_id) " +
                "where se.course_event_id = " + eventId;
    }

    static String getCourseUnitsOfCourseEvent(Integer eventId) {
        return "select course_unit_id, course_unit_date, course_unit_time from course_units where course_event_id = " + eventId;
    }


    private ReadQueries() {};
}
