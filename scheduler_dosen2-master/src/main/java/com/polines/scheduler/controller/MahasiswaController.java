package com.polines.scheduler.controller;

import com.polines.scheduler.model.Jadwal;
import com.polines.scheduler.repository.JadwalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mahasiswa")
public class MahasiswaController {

    @Autowired
    private JadwalRepository jadwalRepository;

    @GetMapping("/schedule")
    public List<Jadwal> getSchedule() {
        return jadwalRepository.findAll();
    }
}