package com.scheduler.model;

public class Dosen {
    private int id;
    private String nama;
    private int maxSks = 24; // default
    private int programStudiId;

    public Dosen() {}

    public Dosen(int id, String nama, int maxSks, int programStudiId) {
        this.id = id;
        this.nama = nama;
        this.maxSks = maxSks;
        this.programStudiId = programStudiId;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public int getMaxSks() { return maxSks; }
    public void setMaxSks(int maxSks) { this.maxSks = maxSks; }
    public int getProgramStudiId() { return programStudiId; }
    public void setProgramStudiId(int programStudiId) { this.programStudiId = programStudiId; }

    @Override
    public String toString() {
        return nama;
    }
}