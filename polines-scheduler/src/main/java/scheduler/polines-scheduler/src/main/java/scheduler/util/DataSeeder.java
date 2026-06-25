package scheduler.util;

import scheduler.model.*;
import java.util.*;

public class DataSeeder {

    private static final String[] PRODIS = {"Information Systems", "Civil Engineering", "Mechanical Engineering", "Electrical Engineering"};

    public static List<Course> createCourses() {
        List<Course> list = new ArrayList<>();
        for (int i = 0; i < 265; i++) {
            String prodi = PRODIS[i % 4];
            String type = (i % 5 == 0) ? "Laboratory" : "Theory";
            list.add(new Course(i + 1, "MK" + String.format("%03d", i + 1),
                                "Subject " + (i + 1), prodi, 3, type));
        }
        return list;
    }

    public static List<Lecturer> createLecturers() {
        List<Lecturer> list = new ArrayList<>();
        for (int i = 1; i <= 41; i++) {
            String prodi = PRODIS[i % 4];
            list.add(new Lecturer(i, "Dosen " + i, 24, prodi));
        }
        return list;
    }

    public static List<Room> createRooms() {
        List<Room> list = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            list.add(new Room(i, "Theory Room " + i, "Theory"));
        }
        for (int i = 10; i <= 17; i++) {
            list.add(new Room(i, "Lab " + (i - 9), "Laboratory"));
        }
        return list;
    }
}