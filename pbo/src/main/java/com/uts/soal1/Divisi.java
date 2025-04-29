package com.uts.soal1;

import java.util.Date;

public class Divisi extends Karyawan {
    String namaDivisi;

    public Divisi(int id, String nama, String posisi, Double gaji, String namaDivisi) {
        super(id, nama, posisi, gaji);
        this.namaDivisi = namaDivisi;
    }

    public Divisi(int id, String nama, String posisi, Double gaji, Date tanggal_masuk, String namaDivisi) {
        super(id, nama, posisi, gaji, tanggal_masuk);
        this.namaDivisi = namaDivisi;
    }

    public String getNamaDivisi(){
        return namaDivisi;
    }
}
