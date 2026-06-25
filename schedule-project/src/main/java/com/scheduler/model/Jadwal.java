package com.scheduler.model;

public class Jadwal {
    private int id;
    private int mataKuliahId;
    private int dosenId;
    private int ruanganId;
    private String hari;     // Senin, Selasa, ...
    private String jamMulai; // HH:mm
    private String jamSelesai; // HH:mm

    public Jadwal() {}

    public Jadwal(int id, int mataKuliahId, int dosenId, int ruanganId, String hari, String jamMulai, String jamSelesai) {
        this.id = id;
        this.mataKuliahId = mataKuliahId;
        this.dosenId = dosenId;
        this.ruanganId = ruanganId;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getMataKuliahId() { return mataKuliahId; }
    public void setMataKuliahId(int mataKuliahId) { this.mataKuliahId = mataKuliahId; }
    public int getDosenId() { return dosenId; }
    public void setDosenId(int dosenId) { this.dosenId = dosenId; }
    public int getRuanganId() { return ruanganId; }
    public void setRuanganId(int ruanganId) { this.ruanganId = ruanganId; }
    public String getHari() { return hari; }
    public void setHari(String hari) { this.hari = hari; }
    public String getJamMulai() { return jamMulai; }
    public void setJamMulai(String jamMulai) { this.jamMulai = jamMulai; }
    public String getJamSelesai() { return jamSelesai; }
    public void setJamSelesai(String jamSelesai) { this.jamSelesai = jamSelesai; }
}