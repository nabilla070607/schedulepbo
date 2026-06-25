package com.scheduler.dao;

import com.scheduler.model.MataKuliah;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MataKuliahDAO {

    // 1. Ambil semua data mata kuliah
    public List<MataKuliah> getAll() throws SQLException {
        List<MataKuliah> list = new ArrayList<>();
        String sql = "SELECT * FROM mata_kuliah ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                MataKuliah mk = new MataKuliah();
                mk.setId(rs.getInt("id"));
                mk.setKode(rs.getString("kode"));
                mk.setNama(rs.getString("nama"));
                mk.setSks(rs.getInt("sks"));
                mk.setJenisRuang(rs.getString("jenis_ruang"));
                mk.setProgramStudiId(rs.getInt("program_studi_id"));
                list.add(mk);
            }
        }
        return list;
    }

    // 2. Tambah data mata kuliah baru (INSERT)
    public void insert(MataKuliah mk) throws SQLException {
        String sql = "INSERT INTO mata_kuliah (kode, nama, sks, jenis_ruang, program_studi_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, mk.getKode());
            ps.setString(2, mk.getNama());
            ps.setInt(3, mk.getSks());
            ps.setString(4, mk.getJenisRuang());
            ps.setInt(5, mk.getProgramStudiId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        mk.setId(rs.getInt(1)); // ambil ID yang di-generate oleh PostgreSQL (SERIAL)
                    }
                }
            }
        }
    }

    // 3. Update data
    public void update(MataKuliah mk) throws SQLException {
        String sql = "UPDATE mata_kuliah SET kode=?, nama=?, sks=?, jenis_ruang=?, program_studi_id=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, mk.getKode());
            ps.setString(2, mk.getNama());
            ps.setInt(3, mk.getSks());
            ps.setString(4, mk.getJenisRuang());
            ps.setInt(5, mk.getProgramStudiId());
            ps.setInt(6, mk.getId());

            ps.executeUpdate();
        }
    }

    // 4. Hapus data berdasarkan ID
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM mata_kuliah WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // 5. Cari berdasarkan ID
    public MataKuliah getById(int id) throws SQLException {
        String sql = "SELECT * FROM mata_kuliah WHERE id = ?";
        MataKuliah mk = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    mk = new MataKuliah();
                    mk.setId(rs.getInt("id"));
                    mk.setKode(rs.getString("kode"));
                    mk.setNama(rs.getString("nama"));
                    mk.setSks(rs.getInt("sks"));
                    mk.setJenisRuang(rs.getString("jenis_ruang"));
                    mk.setProgramStudiId(rs.getInt("program_studi_id"));
                }
            }
        }
        return mk;
    }

    // === MAIN TEST ===
    public static void main(String[] args) {
        MataKuliahDAO dao = new MataKuliahDAO();
        try {
            // Tes insert data dummy (pastikan program_studi_id = 1 sudah ada di tabel program_studi)
            System.out.println("🟡 Mencoba insert data dummy...");
            MataKuliah test = new MataKuliah(0, "MK001", "Algoritma", 3, "teori", 1);
            dao.insert(test);
            System.out.println("✅ Insert berhasil! ID baru: " + test.getId());

            // Tes ambil semua data
            System.out.println("🟡 Mengambil semua data mata kuliah...");
            List<MataKuliah> semua = dao.getAll();
            for (MataKuliah m : semua) {
                System.out.println(" - " + m.getKode() + ": " + m.getNama());
            }

        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}