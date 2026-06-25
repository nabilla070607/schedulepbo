package com.scheduler.dao;

import com.scheduler.model.Ruangan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RuanganDAO {

    public List<Ruangan> getAll() throws SQLException {
        List<Ruangan> list = new ArrayList<>();
        String sql = "SELECT * FROM ruangan ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ruangan r = new Ruangan();
                r.setId(rs.getInt("id"));
                r.setNama(rs.getString("nama"));
                r.setJenis(rs.getString("jenis"));
                r.setKapasitas(rs.getInt("kapasitas"));
                list.add(r);
            }
        }
        return list;
    }

    public void insert(Ruangan r) throws SQLException {
        String sql = "INSERT INTO ruangan (nama, jenis, kapasitas) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, r.getNama());
            pstmt.setString(2, r.getJenis());
            pstmt.setInt(3, r.getKapasitas());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) r.setId(rs.getInt(1));
            }
        }
    }

    public void update(Ruangan r) throws SQLException {
        String sql = "UPDATE ruangan SET nama=?, jenis=?, kapasitas=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, r.getNama());
            pstmt.setString(2, r.getJenis());
            pstmt.setInt(3, r.getKapasitas());
            pstmt.setInt(4, r.getId());
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM ruangan WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public Ruangan getById(int id) throws SQLException {
        String sql = "SELECT * FROM ruangan WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Ruangan r = new Ruangan();
                    r.setId(rs.getInt("id"));
                    r.setNama(rs.getString("nama"));
                    r.setJenis(rs.getString("jenis"));
                    r.setKapasitas(rs.getInt("kapasitas"));
                    return r;
                }
            }
        }
        return null;
    }

    // MAIN TEST
    public static void main(String[] args) {
        RuanganDAO dao = new RuanganDAO();
        try {
            Ruangan test = new Ruangan(0, "Lab Komputer 1", "lab", 30);
            dao.insert(test);
            System.out.println("✅ Insert Ruangan berhasil, ID: " + test.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}