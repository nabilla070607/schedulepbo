package scheduler.engine;

import scheduler.model.Assignment;
import java.util.List;

public class CSPEngine {
    public static boolean isValid(Assignment assignment, List<Assignment> existing) {
        for (Assignment e : existing) {
            // 1. Bentrok Ruang
            if (e.getRoom().getId() == assignment.getRoom().getId() &&
                e.getDay().equals(assignment.getDay()) &&
                e.getTimeSlot().equals(assignment.getTimeSlot())) {
                return false;
            }
            // 2. Bentrok Dosen
            if (e.getLecturer().getId() == assignment.getLecturer().getId() &&
                e.getDay().equals(assignment.getDay()) &&
                e.getTimeSlot().equals(assignment.getTimeSlot())) {
                return false;
            }
            // 3. Bentrok Prodi
            if (e.getCourse().getProdi().equals(assignment.getCourse().getProdi()) &&
                e.getDay().equals(assignment.getDay()) &&
                e.getTimeSlot().equals(assignment.getTimeSlot())) {
                return false;
            }
            // 4. Kesesuaian tipe ruang
            if (!assignment.getCourse().getType().equals(assignment.getRoom().getType())) {
                return false;
            }
        }
        return true;
    }
}