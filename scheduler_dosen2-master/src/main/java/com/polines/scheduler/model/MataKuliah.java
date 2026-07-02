package com.polines.scheduler.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class MataKuliah {
    @Id
    private String kode;
    private String nama;
    private int sks;
    private String tipe; // "Teori" atau "Lab"
}