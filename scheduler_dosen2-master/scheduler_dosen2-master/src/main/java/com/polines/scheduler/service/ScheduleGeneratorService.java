package com.polines.scheduler.service;

import com.polines.scheduler.model.*;
import com.polines.scheduler.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScheduleGeneratorService {

    @Autowired private DosenRepository dosenRepository;
    @Autowired private MataKuliahRepository mataKuliahRepository;
    @Autowired private RuanganRepository ruanganRepository;
    @Autowired private JadwalRepository jadwalRepository;

    private static final String[] HARI = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat"};
    private static final String[] JAM = {"07:00", "08:00", "09:00", "10:00", "11:00", "13:00", "14:00"};
    public List<String> generate() {
        List<String> logs = new ArrayList<>();
        logs.add("[INFO] Memulai generate jadwal...");

        // Ambil semua data dari DB
        List<Dosen> dosenList = dosenRepository.findAll();
        List<MataKuliah> mkList = mataKuliahRepository.findAll();
        List<Ruangan> ruanganList = ruanganRepository.findAll();

        logs.add("[INFO] Dosen: " + dosenList.size() + ", MK: " + mkList.size() + ", Ruangan: " + ruanganList.size());

        // Hapus jadwal lama
        jadwalRepository.deleteAll();
        logs.add("[INFO] Jadwal lama dihapus.");

        // Generate jadwal sederhana
        List<Jadwal> hasil = new ArrayList<>();
        Random random = new Random();

        for (MataKuliah mk : mkList) {
            Jadwal j = new Jadwal();
            int jamMulaiIdx = random.nextInt(JAM.length - 1);
            j.setJamMulai(JAM[jamMulaiIdx]);
            j.setJamSelesai(JAM[jamMulaiIdx + 1]);
            j.setMataKuliah(mk);
            j.setDosen(dosenList.get(random.nextInt(dosenList.size())));
            j.setRuangan(ruanganList.get(random.nextInt(ruanganList.size())));
            j.setHari(HARI[random.nextInt(HARI.length)]);
            hasil.add(j);
            logs.add("[GA] Jadwal dibuat: " + mk.getNama() + " → " + j.getHari());
        }

        jadwalRepository.saveAll(hasil);
        logs.add("[SUCCESS] " + hasil.size() + " jadwal berhasil disimpan!");
        return logs;
    }
}