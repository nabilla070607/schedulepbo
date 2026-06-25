package com.scheduler.algorithm;

import com.scheduler.model.Dosen;
import com.scheduler.model.MataKuliah;
import com.scheduler.model.Ruangan;
import com.scheduler.util.Constants;

import java.util.*;
import java.util.stream.Collectors;

public class Chromosome {

    private List<Gen> gens;          // Daftar gen (assignment)
    private double fitness;          // Nilai fitness (0.0 - 1.0)

    // Inner class: Satu Gen = Satu Mata Kuliah + Alokasi
    public static class Gen {
        public int mataKuliahId;
        public int dosenId;
        public int ruanganId;
        public String hari;      // "Senin", "Selasa", ...
        public String jamMulai;  // "08:00", "10:00", ...

        public Gen() {}

        public Gen(int mataKuliahId, int dosenId, int ruanganId, String hari, String jamMulai) {
            this.mataKuliahId = mataKuliahId;
            this.dosenId = dosenId;
            this.ruanganId = ruanganId;
            this.hari = hari;
            this.jamMulai = jamMulai;
        }

        @Override
        public String toString() {
            return "MK=" + mataKuliahId + ", Dosen=" + dosenId + ", Ruang=" + ruanganId + ", " + hari + " " + jamMulai;
        }
    }

    // Constructor
    public Chromosome() {
        this.gens = new ArrayList<>();
        this.fitness = 0.0;
    }

    public Chromosome(List<Gen> gens) {
        this.gens = gens;
        this.fitness = 0.0;
    }

    // --- GETTER & SETTER ---
    public List<Gen> getGens() { return gens; }
    public void setGens(List<Gen> gens) { this.gens = gens; }
    public double getFitness() { return fitness; }
    public void setFitness(double fitness) { this.fitness = fitness; }

    // ============================================================
    // 1. INISIALISASI ACAK (Random Initialization)
    // ============================================================
    public static Chromosome random(List<MataKuliah> allMk, List<Dosen> allDosen, List<Ruangan> allRuangan) {
        Chromosome chromosome = new Chromosome();
        Random rand = new Random();

        // Filter ruangan berdasarkan jenis (lab/teori) agar sesuai
        Map<String, List<Ruangan>> ruanganByJenis = allRuangan.stream()
                .collect(Collectors.groupingBy(Ruangan::getJenis));

        for (MataKuliah mk : allMk) {
            Gen gen = new Gen();

            // 1. Pilih Mata Kuliah
            gen.mataKuliahId = mk.getId();

            // 2. Pilih Dosen secara acak (idealnya nanti disesuaikan dengan prodi, tapi untuk inisialisasi awal acak dulu)
            Dosen randomDosen = allDosen.get(rand.nextInt(allDosen.size()));
            gen.dosenId = randomDosen.getId();

            // 3. Pilih Ruangan sesuai jenis (Lab / Teori)
            String jenisRuang = mk.getJenisRuang(); // "lab" atau "teori"
            List<Ruangan> ruanganCocok = ruanganByJenis.getOrDefault(jenisRuang, allRuangan);
            if (ruanganCocok.isEmpty()) {
                ruanganCocok = allRuangan; // fallback jika tidak ada
            }
            Ruangan randomRuang = ruanganCocok.get(rand.nextInt(ruanganCocok.size()));
            gen.ruanganId = randomRuang.getId();

            // 4. Pilih Hari & Jam secara acak
            gen.hari = Constants.DAYS[rand.nextInt(Constants.DAYS.length)];
            gen.jamMulai = Constants.TIMES[rand.nextInt(Constants.TIMES.length)];

            chromosome.gens.add(gen);
        }

        return chromosome;
    }

    // ============================================================
    // 2. COPY / KLONING (untuk crossover dan elitisme)
    // ============================================================
    public Chromosome copy() {
        Chromosome clone = new Chromosome();
        for (Gen g : this.gens) {
            Gen newGen = new Gen(
                    g.mataKuliahId,
                    g.dosenId,
                    g.ruanganId,
                    g.hari,
                    g.jamMulai
            );
            clone.gens.add(newGen);
        }
        clone.fitness = this.fitness;
        return clone;
    }

    @Override
    public String toString() {
        return "Chromosome{gens=" + gens.size() + ", fitness=" + fitness + "}";
    }
}