package com.scheduler.model;

public class MataKuliah {
    private int id;
    private String kode;
    private String nama;
    private int sks;
    private String jenisRuang; // "lab" atau "teori"
    private int programStudiId;

    // Constructor kosong
    public MataKuliah() {}

    // Constructor dengan parameter
    public MataKuliah(int id, String kode, String nama, int sks, String jenisRuang, int programStudiId) {
        this.id = id;
        this.kode = kode;
        this.nama = nama;
        this.sks = sks;
        this.jenisRuang = jenisRuang;
        this.programStudiId = programStudiId;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public int getSks() { return sks; }
    public void setSks(int sks) { this.sks = sks; }

    public String getJenisRuang() { return jenisRuang; }
    public void setJenisRuang(String jenisRuang) { this.jenisRuang = jenisRuang; }

    public int getProgramStudiId() { return programStudiId; }
    public void setProgramStudiId(int programStudiId) { this.programStudiId = programStudiId; }

    @Override
    public String toString() {
        return kode + " - " + nama;
    }
}