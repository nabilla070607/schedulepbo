package com.polines.scheduler.repository;

import com.polines.scheduler.model.Ruangan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuanganRepository extends JpaRepository<Ruangan, Long> {
}