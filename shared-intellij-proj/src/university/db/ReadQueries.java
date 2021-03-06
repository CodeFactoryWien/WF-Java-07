package university.db;

public class ReadQueries {
    static String getAll(String tableName) { return "select * from " + tableName; }
    static String getAll(String tableName, String sortBy) {
        return "select * from " + tableName + " order by " + sortBy;
    }

    static String getCoursesData() {
        return "select course_id, course_title, course_description, course_type_name, course_module_code, course_credit_points " +
                "from courses join course_types using (course_type_id) order by course_title";
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

    static String getProfessorsNotTeachingEvent(Integer eventId) {
        return "select professor_id, professor_name, professor_surname from professors p " +
               "where not exists (select 1 from professors_doing_course_events x " +
                                 "where course_event_id = " + Integer.toString(eventId) + " " +
                                 "and x.professor_id = p.professor_id) " +
                "order by professor_surname, professor_name";
    }

    static String getStudentsNotAttendingEvent(Integer eventId) {
        return "select student_id, student_name, student_surname from students s " +
                "where not exists (select 1 from students_attending_course_events x " +
                                  "where course_event_id = " + Integer.toString(eventId) + " " +
                                  "and x.student_id = s.student_id) " +
                "order by student_surname, student_name";
    }

    static String getGrade(Integer studentId, Integer courseId) {
        return String.format("select grade, grading_comment from gradings where student_id = %s and course_id = %s",
                studentId, courseId);
    }

    private ReadQueries() {};
}
