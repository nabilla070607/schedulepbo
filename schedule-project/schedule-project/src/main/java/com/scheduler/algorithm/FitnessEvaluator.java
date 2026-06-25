package com.scheduler.algorithm; 

import java.util.ArrayList;   // tambahkan ini
import java.util.HashMap; 
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.scheduler.model.Dosen;
import com.scheduler.model.MataKuliah;
import com.scheduler.model.Ruangan;
import com.scheduler.util.Chromosome;
import com.scheduler.util.Constants;

public class FitnessEvaluator {

    // Method utama untuk menghitung fitness
    public double evaluate(Chromosome chromosome, List<MataKuliah> allMk, List<Dosen> allDosen) {
        List<Chromosome.Gen> gens = chromosome.getGens();

        // === DATA STRUCTURE UNTUK MENDETEKSI KONFLIK ===
        // Key: "Hari_Jam_RuanganId" -> Set MataKuliahId (untuk deteksi double booking ruang)
        Map<String, Set<Integer>> roomUsage = new HashMap<>();
        // Key: "Hari_Jam_DosenId" -> Set MataKuliahId (untuk deteksi double booking dosen)
        Map<String, Set<Integer>> dosenUsage = new HashMap<>();
        // Key: "Hari_Jam_ProdiId" -> Set MataKuliahId (untuk deteksi double booking prodi)
        Map<String, Set<Integer>> prodiUsage = new HashMap<>();

        // Map untuk cari prodi dari id mata kuliah (cache)
        Map<Integer, Integer> mkToProdi = new HashMap<>();
        for (MataKuliah mk : allMk) {
            mkToProdi.put(mk.getId(), mk.getProgramStudiId());
        }

        // === HITUNG HARD CONFLICT ===
        for (Chromosome.Gen g : gens) {
            String timeKey = g.hari + "_" + g.jamMulai;

            // a. Deteksi Ruangan Double Booking
            String roomKey = timeKey + "_ROOM_" + g.ruanganId;
            roomUsage.computeIfAbsent(roomKey, k -> new HashSet<>()).add(g.mataKuliahId);

            // b. Deteksi Dosen Double Booking
            String dosenKey = timeKey + "_DOSEN_" + g.dosenId;
            dosenUsage.computeIfAbsent(dosenKey, k -> new HashSet<>()).add(g.mataKuliahId);

            // c. Deteksi Prodi Double Booking
            int prodiId = mkToProdi.getOrDefault(g.mataKuliahId, -1);
            if (prodiId != -1) {
                String prodiKey = timeKey + "_PRODI_" + prodiId;
                prodiUsage.computeIfAbsent(prodiKey, k -> new HashSet<>()).add(g.mataKuliahId);
            }
        }

        // Hitung total hard conflict (setiap set yang ukurannya > 1 berarti conflict)
        int hardConflicts = 0;
        for (Set<Integer> set : roomUsage.values()) {
            if (set.size() > 1) hardConflicts += (set.size() - 1);
        }
        for (Set<Integer> set : dosenUsage.values()) {
            if (set.size() > 1) hardConflicts += (set.size() - 1);
        }
        for (Set<Integer> set : prodiUsage.values()) {
            if (set.size() > 1) hardConflicts += (set.size() - 1);
        }

        // === HITUNG SOFT CONFLICT (Preferensi, contoh: dosen tidak suka Sabtu) ===
        int softConflicts = 0;
        for (Chromosome.Gen g : gens) {
            // Soft: Dosen lebih suka tidak mengajar Sabtu
            if ("Sabtu".equals(g.hari)) {
                softConflicts++;
            }
            // Soft: Beban dosen merata? ini nanti dihitung di luar, untuk sederhana skip dulu.
        }

        // === HITUNG SKOR AKHIR ===
        int rawScore = (hardConflicts * Constants.HARD_CONFLICT_PENALTY) +
                       (softConflicts * Constants.SOFT_CONFLICT_PENALTY);

        // Fitness = 1 / (1 + rawScore)
        // Jika rawScore = 0, fitness = 1.0 (Sempurna!)
        double fitness = 1.0 / (1.0 + rawScore);
        return fitness;
    }

    // ============================================================
    // METHOD BANTUAN UNTUK TESTING (Main)
    // ============================================================
    public static void main(String[] args) {
        // Buat dummy data untuk testing
        List<MataKuliah> dummyMk = new ArrayList<>();
        dummyMk.add(new MataKuliah(1, "IF101", "Algoritma", 3, "teori", 1));
        dummyMk.add(new MataKuliah(2, "IF102", "Struktur Data", 3, "teori", 1));

        List<Dosen> dummyDosen = new ArrayList<>();
        dummyDosen.add(new Dosen(1, "Dr. Andi", 24, 1));
        dummyDosen.add(new Dosen(2, "Dr. Siti", 24, 1));

        List<Ruangan> dummyRuang = new ArrayList<>();
        dummyRuang.add(new Ruangan(1, "Ruang A", "teori", 40));
        dummyRuang.add(new Ruangan(2, "Lab 1", "lab", 20));

        // Buat 1 kromosom acak
        Chromosome c = Chromosome.random(dummyMk, dummyDosen, dummyRuang);
        System.out.println("Kromosom random:");
        for (Chromosome.Gen g : c.getGens()) {
            System.out.println(" - " + g);
        }

        // Evaluasi
        FitnessEvaluator evaluator = new FitnessEvaluator();
        double fitness = evaluator.evaluate(c, dummyMk, dummyDosen);
        System.out.println("Fitness: " + fitness);
    }
}