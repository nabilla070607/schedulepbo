package com.polines.scheduler.controller;

import com.polines.scheduler.model.Dosen;
import com.polines.scheduler.model.Jadwal;
import com.polines.scheduler.repository.DosenRepository;
import com.polines.scheduler.repository.JadwalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/dosen")
public class DosenController {

    @Autowired
    private DosenRepository dosenRepository;

    @Autowired
    private JadwalRepository jadwalRepository;

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboard() {
        Map<String, Object> result = new HashMap<>();
        result.put("totalMataKuliah", jadwalRepository.count());
        result.put("totalJadwal", jadwalRepository.count());
        return result;
    }

    @GetMapping("/schedule")
    public List<Jadwal> getSchedule() {
        return jadwalRepository.findAll();
    }
}