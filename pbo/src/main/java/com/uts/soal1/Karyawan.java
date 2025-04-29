package com.uts.soal1;

import java.util.Date;

public class Karyawan {
    int id;
    String nama;
    String posisi;
    Double gaji;
    Date tanggal_masuk = new Date();

    public Karyawan(int id, String nama, String posisi, Double gaji) {
        this.id = id;
        this.nama = nama;
        this.posisi = posisi;
        this.gaji = gaji;
    }
    
    public Karyawan(int id, String nama, String posisi, Double gaji, Date tanggal_masuk) {
        this.id = id;
        this.nama = nama;
        this.posisi = posisi;
        this.gaji = gaji;
        this.tanggal_masuk = tanggal_masuk;
    }

    public int getId(){
        return id;
    }
    public String getNama(){
        return nama;
    }
    public String getPosisi(){
        return posisi;
    }
    public Double getGaji(){
        return gaji;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setNama(String nama){
        this.nama = nama;
    }
    public void setPosisi(String posisi){
        this.posisi = posisi;
    }
    public void setGaji(Double gaji){
        this.gaji = gaji;
    }
    public Date getTanggalMasuk(){
        return tanggal_masuk;
    }
    public void setTanggalMasuk(Date tanggal_masuk){
        this.tanggal_masuk = tanggal_masuk;
    }
}
