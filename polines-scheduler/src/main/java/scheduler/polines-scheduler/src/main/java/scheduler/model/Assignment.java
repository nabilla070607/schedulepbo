package scheduler.model;

public class Assignment {
    private Course course;
    private Lecturer lecturer;
    private Room room;
    private String day;
    private String timeSlot;

    public Assignment(Course course, Lecturer lecturer, Room room, String day, String timeSlot) {
        this.course = course;
        this.lecturer = lecturer;
        this.room = room;
        this.day = day;
        this.timeSlot = timeSlot;
    }

    public Course getCourse() { return course; }
    public Lecturer getLecturer() { return lecturer; }
    public Room getRoom() { return room; }
    public String getDay() { return day; }
    public String getTimeSlot() { return timeSlot; }

    public void setCourse(Course course) { this.course = course; }
    public void setLecturer(Lecturer lecturer) { this.lecturer = lecturer; }
    public void setRoom(Room room) { this.room = room; }
    public void setDay(String day) { this.day = day; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
}