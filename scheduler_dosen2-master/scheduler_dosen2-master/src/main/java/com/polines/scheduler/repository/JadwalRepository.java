package com.polines.scheduler.repository;

import com.polines.scheduler.model.Jadwal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JadwalRepository extends JpaRepository<Jadwal, Long> {
    List<Jadwal> findByDosenId(Long dosenId);
    List<Jadwal> findByRuanganId(Long ruanganId);
    List<Jadwal> findByMataKuliahKode(String kode);
}