package com.scheduler.dao;

import com.scheduler.model.ProgramStudi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramStudiDAO {

    // GET ALL
    public List<ProgramStudi> getAll() throws SQLException {
        List<ProgramStudi> list = new ArrayList<>();
        String sql = "SELECT * FROM program_studi ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ProgramStudi ps = new ProgramStudi();
                ps.setId(rs.getInt("id"));
                ps.setNama(rs.getString("nama"));
                list.add(ps);
            }
        }
        return list;
    }

    // INSERT
    public void insert(ProgramStudi ps) throws SQLException {
        String sql = "INSERT INTO program_studi (nama) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, ps.getNama());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) ps.setId(rs.getInt(1));
            }
        }
    }

    // UPDATE
    public void update(ProgramStudi ps) throws SQLException {
        String sql = "UPDATE program_studi SET nama=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ps.getNama());
            pstmt.setInt(2, ps.getId());
            pstmt.executeUpdate();
        }
    }

    // DELETE
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM program_studi WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // GET BY ID
    public ProgramStudi getById(int id) throws SQLException {
        String sql = "SELECT * FROM program_studi WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ProgramStudi ps = new ProgramStudi();
                    ps.setId(rs.getInt("id"));
                    ps.setNama(rs.getString("nama"));
                    return ps;
                }
            }
        }
        return null;
    }

    // MAIN TEST
    public static void main(String[] args) {
        ProgramStudiDAO dao = new ProgramStudiDAO();
        try {
            ProgramStudi test = new ProgramStudi(0, "Teknik Informatika");
            dao.insert(test);
            System.out.println("✅ Insert Prodi berhasil, ID: " + test.getId());
            System.out.println("📋 Daftar Prodi:");
            dao.getAll().forEach(p -> System.out.println(" - " + p.getNama()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}