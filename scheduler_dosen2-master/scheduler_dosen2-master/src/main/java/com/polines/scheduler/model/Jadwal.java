package com.polines.scheduler.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@NoArgsConstructor
public class Jadwal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hari;
    private String jamMulai;
    private String jamSelesai;

    @ManyToOne
    @JoinColumn(name = "mata_kuliah_kode")
    @JsonIgnoreProperties({"jadwalList", "hibernateLazyInitializer"})
    private MataKuliah mataKuliah;

    @ManyToOne
    @JoinColumn(name = "dosen_id")
    @JsonIgnoreProperties({"jadwalList", "hibernateLazyInitializer"})
    private Dosen dosen;

    @ManyToOne
    @JoinColumn(name = "ruangan_id")
    @JsonIgnoreProperties({"jadwalList", "hibernateLazyInitializer"})
    private Ruangan ruangan;
}