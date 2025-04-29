package com.uts.soal1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import com.uts.Database_conn;

import java.util.Optional;
import java.util.Date;
import java.sql.*;

public class Perusahaan {
    private Connection conn;

    // Connect ke database
    public Perusahaan(){
        try{
            conn = Database_conn.connect();
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public boolean addKaryawan_DB(Divisi divisi) {
        // check kesamaan ID karyawan //
        if (check_ID(divisi.getId())){return true;}

        String sql = "INSERT INTO karyawans (id, nama, posisi, gaji, divisi, tanggal_masuk) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, divisi.getId());
            pstmt.setString(2, divisi.getNama());
            pstmt.setString(3, divisi.getPosisi());
            pstmt.setDouble(4, divisi.getGaji());
            pstmt.setString(5, divisi.getNamaDivisi());
            pstmt.setDate(6, new java.sql.Date(divisi.tanggal_masuk.getTime()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding karyawan: " + e.getMessage());
        }
        return false;
    }

    public ObservableList<Divisi> getKaryawans(){
        ObservableList<Divisi> karyawans_List = FXCollections.observableArrayList();
        String sql = "SELECT * FROM karyawans";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()){

            while(rs.next()){
                int id = rs.getInt("id");
                String nama = rs.getString("nama");
                String posisi = rs.getString("posisi");
                double gaji = rs.getFloat("gaji");
                String divisi = rs.getString("divisi"); 
                Date tanggalMasuk = rs.getDate("tanggal_masuk");

                Divisi k = new Divisi(id, nama, posisi, gaji, tanggalMasuk, divisi);
                karyawans_List.add(k);
            }
            
        } catch (Exception e) {
            System.out.println("Error getting karyawans: " + e.getMessage());
        }
        System.out.println("Jumlah data: " + karyawans_List.size());

        return karyawans_List;
    }

    public boolean editKaryawans_DB(Divisi divisi){

        String sql = "UPDATE karyawans SET nama = ?, posisi = ?, gaji = ?, divisi = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, divisi.getNama());
            pstmt.setString(2, divisi.getPosisi());
            pstmt.setDouble(3, divisi.getGaji());
            pstmt.setString(4, divisi.getNamaDivisi());
            pstmt.setInt(5, divisi.getId());

            int updated = pstmt.executeUpdate();
            System.out.println(updated + " record(s) updated.");
        } catch (Exception e) {
            System.out.println("Error getting karyawans: " + e.getMessage());
        }
        return false;
    }

    public void hapusKaryawan(Divisi divisi){
        String sql = "DELETE FROM karyawans WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, divisi.id);
            int deleted = pstmt.executeUpdate();
            System.out.println(deleted + " record(s) deleted.");
        } catch (Exception e) {
            System.out.println("Error deleting karyawan: " + e.getMessage());
        }
    }

    //check ID karyawan //
    private boolean check_ID(int id){
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM karyawans WHERE id = ?")){
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Karyawan ID sudah ada");
                alert.setHeaderText("ID " + id + " sudah digunakan.");
                alert.setContentText("Membatalkan penambahan karyawan...");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking karyawan: " + e.getMessage());
        }
        return false;
    }
}