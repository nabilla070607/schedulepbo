package com.scheduler.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // ⚠️ WAJIB GANTI dengan kredensial Supabase Anda
    // Ambil dari Settings > Database > Connection String (JDBC)
    private static final String URL = "postgresql://postgres:[YOUR-PASSWORD]@db.hahctvifjfdonrgsenge.supabase.co:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Yayakpolines";

    private static Connection connection = null;

    // Static block untuk memuat driver (opsional, tapi aman)
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method untuk mendapatkan koneksi
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    // Method untuk menutup koneksi (opsional)
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // MAIN TEST: Jalankan method ini untuk cek koneksi!
   public static void main(String[] args) {
    try {
        ProgramStudiDAO dao = new ProgramStudiDAO();
        dao.getAll().forEach(p -> System.out.println(p.getNama()));
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}