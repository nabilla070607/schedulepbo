package com.polines.scheduler.controller;

import com.polines.scheduler.model.Dosen;
import com.polines.scheduler.model.MataKuliah;
import com.polines.scheduler.model.Ruangan;
import com.polines.scheduler.repository.DosenRepository;
import com.polines.scheduler.repository.MataKuliahRepository;
import com.polines.scheduler.repository.RuanganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.polines.scheduler.model.Jadwal;
import com.polines.scheduler.repository.JadwalRepository;
import com.polines.scheduler.service.ScheduleGeneratorService;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private DosenRepository dosenRepository;

    @Autowired
    private MataKuliahRepository mataKuliahRepository;

    @Autowired
    private RuanganRepository ruanganRepository;

    @Autowired
    private JadwalRepository jadwalRepository;

    @Autowired
    private ScheduleGeneratorService generatorService;

    // ===== DOSEN =====
    @GetMapping("/lecturers")
    public List<Dosen> getLecturers() {
        return dosenRepository.findAll();
    }

    @PostMapping("/lecturers")   // <--- TAMBAHKAN INI!
    public Dosen addLecturer(@RequestBody Dosen dosen) {
        return dosenRepository.save(dosen);
    }

    // ===== MATA KULIAH =====
    @GetMapping("/courses")
    public List<MataKuliah> getCourses() {
        return mataKuliahRepository.findAll();
    }

    @PostMapping("/courses")
    public MataKuliah addCourse(@RequestBody MataKuliah mataKuliah) {
        return mataKuliahRepository.save(mataKuliah);
    }

    // ===== RUANGAN =====
    @GetMapping("/rooms")
    public List<Ruangan> getRooms() {
        return ruanganRepository.findAll();
    }

    @PostMapping("/rooms")
    public Ruangan addRoom(@RequestBody Ruangan ruangan) {
        return ruanganRepository.save(ruangan);
    }

    @GetMapping("/schedule")
    public List<Jadwal> getSchedule() {
        return jadwalRepository.findAll();
    }

    @PostMapping("/lecturers/batch")
    public List<Dosen> addLecturersBatch(@RequestBody List<Dosen> dosenList) {
        return dosenRepository.saveAll(dosenList);
    }

    @PostMapping("/courses/batch")
    public List<MataKuliah> addCoursesBatch(@RequestBody List<MataKuliah> mkList) {
        return mataKuliahRepository.saveAll(mkList);
    }

    @PostMapping("/rooms/batch")
    public List<Ruangan> addRoomsBatch(@RequestBody List<Ruangan> ruanganList) {
        return ruanganRepository.saveAll(ruanganList);
    }

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalMataKuliah", mataKuliahRepository.count());
        stats.put("totalDosen", dosenRepository.count());
        stats.put("totalRuangan", ruanganRepository.count());
        stats.put("totalKonflik", 0); // Nanti diisi logika CSP
        return stats;
    }

    @PostMapping("/generate")
    public Map<String, Object> generate() {
        Map<String, Object> result = new HashMap<>();
        result.put("logs", generatorService.generate());
        return result;
    }

    @DeleteMapping("/lecturers/{id}")
    public void deleteLecturer(@PathVariable Long id) {
        // Hapus jadwal yang pakai dosen ini dulu
        List<Jadwal> jadwalDosen = jadwalRepository.findByDosenId(id);
        jadwalRepository.deleteAll(jadwalDosen);
        // Baru hapus dosen
        dosenRepository.deleteById(id);
    }

    @DeleteMapping("/rooms/{id}")
    public void deleteRoom(@PathVariable Long id) {
        List<Jadwal> jadwalRuangan = jadwalRepository.findByRuanganId(id);
        jadwalRepository.deleteAll(jadwalRuangan);
        ruanganRepository.deleteById(id);
    }

    @DeleteMapping("/courses/{kode}")
    public void deleteCourse(@PathVariable String kode) {
        List<Jadwal> jadwalMatkul = jadwalRepository.findByMataKuliahKode(kode);
        jadwalRepository.deleteAll(jadwalMatkul);
        mataKuliahRepository.deleteById(kode);
    }

}