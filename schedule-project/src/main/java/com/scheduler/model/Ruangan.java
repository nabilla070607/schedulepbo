package com.scheduler.model;

public class Ruangan {
    private int id;
    private String nama;
    private String jenis; // "lab" atau "teori"
    private int kapasitas = 40;

    public Ruangan() {}

    public Ruangan(int id, String nama, String jenis, int kapasitas) {
        this.id = id;
        this.nama = nama;
        this.jenis = jenis;
        this.kapasitas = kapasitas;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }
    public int getKapasitas() { return kapasitas; }
    public void setKapasitas(int kapasitas) { this.kapasitas = kapasitas; }

    @Override
    public String toString() {
        return nama + " (" + jenis + ")";
    }
}