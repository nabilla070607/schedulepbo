package com.scheduler.dao;

import com.scheduler.model.Dosen;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DosenDAO {

    public List<Dosen> getAll() throws SQLException {
        List<Dosen> list = new ArrayList<>();
        String sql = "SELECT * FROM dosen ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Dosen d = new Dosen();
                d.setId(rs.getInt("id"));
                d.setNama(rs.getString("nama"));
                d.setMaxSks(rs.getInt("max_sks"));
                d.setProgramStudiId(rs.getInt("program_studi_id"));
                list.add(d);
            }
        }
        return list;
    }

    public void insert(Dosen d) throws SQLException {
        String sql = "INSERT INTO dosen (nama, max_sks, program_studi_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, d.getNama());
            pstmt.setInt(2, d.getMaxSks());
            pstmt.setInt(3, d.getProgramStudiId());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) d.setId(rs.getInt(1));
            }
        }
    }

    public void update(Dosen d) throws SQLException {
        String sql = "UPDATE dosen SET nama=?, max_sks=?, program_studi_id=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, d.getNama());
            pstmt.setInt(2, d.getMaxSks());
            pstmt.setInt(3, d.getProgramStudiId());
            pstmt.setInt(4, d.getId());
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM dosen WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public Dosen getById(int id) throws SQLException {
        String sql = "SELECT * FROM dosen WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Dosen d = new Dosen();
                    d.setId(rs.getInt("id"));
                    d.setNama(rs.getString("nama"));
                    d.setMaxSks(rs.getInt("max_sks"));
                    d.setProgramStudiId(rs.getInt("program_studi_id"));
                    return d;
                }
            }
        }
        return null;
    }

    // MAIN TEST
    public static void main(String[] args) {
        DosenDAO dao = new DosenDAO();
        try {
            Dosen test = new Dosen(0, "Dr. Andi", 24, 1);
            dao.insert(test);
            System.out.println("✅ Insert Dosen berhasil, ID: " + test.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}