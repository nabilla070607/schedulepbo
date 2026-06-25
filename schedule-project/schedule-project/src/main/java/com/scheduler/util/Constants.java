package com.scheduler.util;

public class Constants {

    // ===== PARAMETER GENETIC ALGORITHM =====
    public static final int POPULATION_SIZE = 50;      // Ukuran populasi
    public static final int MAX_GENERATIONS = 500;     // Maksimal generasi
    public static final double CROSSOVER_RATE = 0.8;   // Probabilitas crossover (80%)
    public static final double MUTATION_RATE = 0.02;   // Probabilitas mutasi (2%)
    public static final int ELITE_COUNT = 2;           // Jumlah kromosom terbaik yang dipertahankan
    public static final int TOURNAMENT_SIZE = 3;       // Ukuran tournament selection

    // ===== SLOT WAKTU =====
    // Hari kuliah (Senin - Sabtu)
    public static final String[] DAYS = {
        "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"
    };

    // Jam mulai (4 slot utama, bisa ditambah jika perlu)
    public static final String[] TIMES = {
        "08:00", "10:00", "13:00", "15:00"
    };

    // Durasi per slot dalam menit (misal 100 menit = 1 jam 40 menit)
    public static final int SLOT_DURATION_MINUTES = 100;

    // ===== BOBOT KONFLIK UNTUK FITNESS =====
    public static final int HARD_CONFLICT_PENALTY = 100; // Denda besar untuk hard constraint
    public static final int SOFT_CONFLICT_PENALTY = 10;  // Denda kecil untuk soft constraint
}