package com.scheduler.dao;

import com.scheduler.model.Jadwal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JadwalDAO {

    // --- CRUD DASAR ---

    public List<Jadwal> getAll() throws SQLException {
        List<Jadwal> list = new ArrayList<>();
        String sql = "SELECT * FROM jadwal ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        }
        return list;
    }

    public void insert(Jadwal j) throws SQLException {
        String sql = "INSERT INTO jadwal (mata_kuliah_id, dosen_id, ruangan_id, hari, jam_mulai, jam_selesai) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setPreparedStatement(pstmt, j);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) j.setId(rs.getInt(1));
            }
        }
    }

    public void update(Jadwal j) throws SQLException {
        String sql = "UPDATE jadwal SET mata_kuliah_id=?, dosen_id=?, ruangan_id=?, hari=?, jam_mulai=?, jam_selesai=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setPreparedStatement(pstmt, j);
            pstmt.setInt(7, j.getId());
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM jadwal WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public Jadwal getById(int id) throws SQLException {
        String sql = "SELECT * FROM jadwal WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return mapResultSet(rs);
            }
        }
        return null;
    }

    // --- METHOD KHUSUS UNTUK PROYEK ---

    // 1. Hapus semua data jadwal (untuk generate ulang)
    public void clearAll() throws SQLException {
        String sql = "DELETE FROM jadwal";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    // 2. Simpan banyak jadwal sekaligus (Batch Insert) untuk hasil GA
    public void saveBatch(List<Jadwal> listJadwal) throws SQLException {
        if (listJadwal == null || listJadwal.isEmpty()) return;

        String sql = "INSERT INTO jadwal (mata_kuliah_id, dosen_id, ruangan_id, hari, jam_mulai, jam_selesai) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            conn.setAutoCommit(false); // matikan auto-commit untuk batch
            int count = 0;
            for (Jadwal j : listJadwal) {
                setPreparedStatement(pstmt, j);
                pstmt.addBatch();
                count++;
                // Kirim batch tiap 1000 record agar memory tidak overload
                if (count % 1000 == 0) {
                    pstmt.executeBatch();
                    pstmt.clearBatch();
                }
            }
            pstmt.executeBatch(); // sisa terakhir
            conn.commit();
            conn.setAutoCommit(true);
        }
    }

    // 3. Ambil jadwal berdasarkan Dosen (untuk Panel Dosen)
    public List<Jadwal> getByDosen(int dosenId) throws SQLException {
        List<Jadwal> list = new ArrayList<>();
        String sql = "SELECT * FROM jadwal WHERE dosen_id = ? ORDER BY hari, jam_mulai";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dosenId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapResultSet(rs));
            }
        }
        return list;
    }

    // 4. Ambil jadwal berdasarkan Program Studi (untuk Panel Mahasiswa)
    //    Join dengan tabel mata_kuliah untuk filter prodi
    public List<Jadwal> getByProdi(int prodiId) throws SQLException {
        List<Jadwal> list = new ArrayList<>();
        String sql = "SELECT j.* FROM jadwal j " +
                     "JOIN mata_kuliah mk ON j.mata_kuliah_id = mk.id " +
                     "WHERE mk.program_studi_id = ? " +
                     "ORDER BY j.hari, j.jam_mulai";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, prodiId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapResultSet(rs));
            }
        }
        return list;
    }

    // --- HELPER PRIVATE METHODS ---

    private Jadwal mapResultSet(ResultSet rs) throws SQLException {
        Jadwal j = new Jadwal();
        j.setId(rs.getInt("id"));
        j.setMataKuliahId(rs.getInt("mata_kuliah_id"));
        j.setDosenId(rs.getInt("dosen_id"));
        j.setRuanganId(rs.getInt("ruangan_id"));
        j.setHari(rs.getString("hari"));
        j.setJamMulai(rs.getString("jam_mulai"));
        j.setJamSelesai(rs.getString("jam_selesai"));
        return j;
    }

    private void setPreparedStatement(PreparedStatement pstmt, Jadwal j) throws SQLException {
        pstmt.setInt(1, j.getMataKuliahId());
        pstmt.setInt(2, j.getDosenId());
        pstmt.setInt(3, j.getRuanganId());
        pstmt.setString(4, j.getHari());
        pstmt.setString(5, j.getJamMulai());
        pstmt.setString(6, j.getJamSelesai());
    }

    // --- MAIN TEST (Lengkap) ---
    public static void main(String[] args) {
        JadwalDAO dao = new JadwalDAO();
        try {
            // Test insert 1 data dummy
            Jadwal test = new Jadwal(0, 1, 1, 1, "Senin", "08:00:00", "09:40:00");
            dao.insert(test);
            System.out.println("✅ Insert jadwal berhasil, ID: " + test.getId());

            // Test batch insert
            List<Jadwal> batch = new ArrayList<>();
            batch.add(new Jadwal(0, 2, 2, 2, "Senin", "10:00:00", "11:40:00"));
            batch.add(new Jadwal(0, 3, 3, 3, "Selasa", "08:00:00", "09:40:00"));
            dao.saveBatch(batch);
            System.out.println("✅ Batch insert berhasil!");

            // Test get by dosen
            System.out.println("📋 Jadwal untuk Dosen ID 1:");
            dao.getByDosen(1).forEach(j -> 
                System.out.println(" - " + j.getHari() + " " + j.getJamMulai())
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}