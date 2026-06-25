package scheduler.engine;

import scheduler.model.*;
import java.util.*;

public class ScheduleGenerator {

    private static final String[] DAYS = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
    private static final String[] TIME_SLOTS = {
        "07:00-08:40", "08:40-10:20", "10:20-12:00",
        "12:00-13:00", "13:00-14:40", "14:40-16:20", "16:20-18:00"
    };

    // METHOD YANG DIPANGGIL DARI MAIN (dengan Random)
    public static List<Assignment> generate(List<Course> courses,
                                            List<Lecturer> lecturers,
                                            List<Room> rooms,
                                            Random rand) {
        List<Assignment> result = new ArrayList<>();

        // 1. Prioritaskan course (Laboratory didahulukan)
        Map<String, Integer> prodiCount = new HashMap<>();
        for (Course c : courses) {
            prodiCount.put(c.getProdi(), prodiCount.getOrDefault(c.getProdi(), 0) + 1);
        }

        List<Course> prioritized = new ArrayList<>(courses);
        prioritized.sort((c1, c2) -> {
            if (c1.getType().equals("Laboratory") && !c2.getType().equals("Laboratory")) return -1;
            if (!c1.getType().equals("Laboratory") && c2.getType().equals("Laboratory")) return 1;
            int cmp = Integer.compare(prodiCount.get(c2.getProdi()), prodiCount.get(c1.getProdi()));
            if (cmp != 0) return cmp;
            return c1.getCode().compareTo(c2.getCode());
        });

        // 2. Acak urutan
        Collections.shuffle(prioritized, rand);

        // 3. Proses setiap course
        for (Course course : prioritized) {
            boolean placed = false;
            List<SlotCandidate> candidates = new ArrayList<>();

            for (Lecturer lecturer : lecturers) {
                for (Room room : rooms) {
                    if (!room.getType().equals(course.getType())) continue;
                    for (String day : DAYS) {
                        for (String timeSlot : TIME_SLOTS) {
                            Assignment assignment = new Assignment(course, lecturer, room, day, timeSlot);
                            int conflictCount = countConflicts(assignment, result);
                            candidates.add(new SlotCandidate(assignment, conflictCount));
                        }
                    }
                }
            }

            candidates.sort((a, b) -> Integer.compare(a.conflictCount, b.conflictCount));

            for (SlotCandidate sc : candidates) {
                if (sc.conflictCount == 0 && CSPEngine.isValid(sc.assignment, result)) {
                    result.add(sc.assignment);
                    placed = true;
                    break;
                }
            }

            if (!placed) {
                for (SlotCandidate sc : candidates) {
                    if (sc.conflictCount == 1 && CSPEngine.isValid(sc.assignment, result)) {
                        result.add(sc.assignment);
                        placed = true;
                        break;
                    }
                }
            }

            if (!placed) {
                for (SlotCandidate sc : candidates) {
                    if (CSPEngine.isValid(sc.assignment, result)) {
                        result.add(sc.assignment);
                        placed = true;
                        break;
                    }
                }
            }

            if (!placed) {
                System.out.println("⚠️ Gagal menempatkan course: " + course.getCode());
            }
        }

        return result;
    }

    private static int countConflicts(Assignment candidate, List<Assignment> existing) {
        int conflicts = 0;
        for (Assignment e : existing) {
            if (e.getRoom().getId() == candidate.getRoom().getId() &&
                e.getDay().equals(candidate.getDay()) &&
                e.getTimeSlot().equals(candidate.getTimeSlot())) {
                conflicts++;
            }
            if (e.getLecturer().getId() == candidate.getLecturer().getId() &&
                e.getDay().equals(candidate.getDay()) &&
                e.getTimeSlot().equals(candidate.getTimeSlot())) {
                conflicts++;
            }
            if (e.getCourse().getProdi().equals(candidate.getCourse().getProdi()) &&
                e.getDay().equals(candidate.getDay()) &&
                e.getTimeSlot().equals(candidate.getTimeSlot())) {
                conflicts++;
            }
        }
        return conflicts;
    }

    private static class SlotCandidate {
        Assignment assignment;
        int conflictCount;
        SlotCandidate(Assignment assignment, int conflictCount) {
            this.assignment = assignment;
            this.conflictCount = conflictCount;
        }
    }
}