package scheduler;

import scheduler.model.*;
import scheduler.engine.ScheduleGenerator;
import scheduler.util.DataSeeder;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("🚀 Menjalankan Generator Jadwal GA + CSP (Java) - Multiple Runs\n");

        List<Course> courses = DataSeeder.createCourses();
        List<Lecturer> lecturers = DataSeeder.createLecturers();
        List<Room> rooms = DataSeeder.createRooms();

        int totalCourses = courses.size();
        int bestCount = 0;
        List<Assignment> bestSchedule = null;
        int totalRuns = 50; // Jalankan 50 kali, ambil terbaik

        long startOverall = System.currentTimeMillis();

        for (int run = 1; run <= totalRuns; run++) {
            Random rand = new Random(run); // seed berbeda setiap run
            long start = System.currentTimeMillis();
            List<Assignment> schedule = ScheduleGenerator.generate(courses, lecturers, rooms, rand);
            long end = System.currentTimeMillis();

            int count = schedule.size();
            if (count > bestCount) {
                bestCount = count;
                bestSchedule = schedule;
                System.out.printf("✅ Run %2d: %3d/%d course ditempatkan (%.1f%%) dalam %d ms [TERBAIK]%n",
                        run, count, totalCourses, (count * 100.0 / totalCourses), (end - start));
            } else {
                System.out.printf("   Run %2d: %3d/%d course ditempatkan (%.1f%%) dalam %d ms%n",
                        run, count, totalCourses, (count * 100.0 / totalCourses), (end - start));
            }
        }

        long endOverall = System.currentTimeMillis();
        System.out.println("\n⏱️ Total waktu eksekusi: " + (endOverall - startOverall) + " ms");

        // ===== TAMPILKAN HASIL TERBAIK =====
        System.out.println("\n📊 ============ HASIL TERBAIK ============");
        System.out.println("✅ Total course: " + totalCourses);
        System.out.println("✅ Total jadwal tergenerate: " + bestCount);
        System.out.println("✅ Persentase: " + String.format("%.2f%%", (bestCount * 100.0 / totalCourses)));

        if (bestCount == totalCourses) {
            System.out.println("🎉 ZERO CONFLICT TERCAPAI! Semua course berhasil dijadwalkan.");
        } else {
            System.out.println("❌ Gagal mencapai Zero Conflict. Course yang gagal: " + (totalCourses - bestCount));
        }

        // ===== STATISTIK PER PRODI =====
        Map<String, Integer> prodiCount = new HashMap<>();
        Map<String, Integer> typeCount = new HashMap<>();
        for (Assignment a : bestSchedule) {
            String prodi = a.getCourse().getProdi();
            prodiCount.put(prodi, prodiCount.getOrDefault(prodi, 0) + 1);
            String type = a.getCourse().getType();
            typeCount.put(type, typeCount.getOrDefault(type, 0) + 1);
        }

        System.out.println("\n📚 Distribusi per Program Studi:");
        for (Map.Entry<String, Integer> entry : prodiCount.entrySet()) {
            System.out.printf("   %-25s: %d course%n", entry.getKey(), entry.getValue());
        }

        System.out.println("\n🏫 Distribusi per Tipe Ruang:");
        for (Map.Entry<String, Integer> entry : typeCount.entrySet()) {
            System.out.printf("   %-15s: %d course%n", entry.getKey(), entry.getValue());
        }

        // ===== TAMPILKAN 10 JADWAL PERTAMA =====
        System.out.println("\n📅 ============ 10 JADWAL PERTAMA (TERBAIK) ============");
        System.out.println("Hari      | Jam        | Kode  | Dosen     | Ruang");
        System.out.println("----------|------------|-------|-----------|-----------------");
        for (int i = 0; i < Math.min(10, bestSchedule.size()); i++) {
            Assignment a = bestSchedule.get(i);
            System.out.printf("%-9s | %-10s | %-5s | %-9s | %s%n",
                    a.getDay(), a.getTimeSlot(),
                    a.getCourse().getCode(),
                    a.getLecturer().getName(),
                    a.getRoom().getName()
            );
        }

        if (bestSchedule.size() > 10) {
            System.out.println("\n... dan " + (bestSchedule.size() - 10) + " jadwal lainnya.");
        }

        System.out.println("\n✅ Selesai!");
    }
}