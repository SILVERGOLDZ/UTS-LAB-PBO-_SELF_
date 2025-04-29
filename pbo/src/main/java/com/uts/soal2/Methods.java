package com.uts.soal2;

public class Methods {

    // NOTE!
    // Method untuk harga telah dikalkulasikan dalam class Kendaraan (check Kendaraan.java)

    public Kendaraan diskon(Kendaraan kendaraan){
        kendaraan.setTotalBiaya(kendaraan.getHarga() * kendaraan.getWaktu());
        if (kendaraan.getWaktu() > 5){
            kendaraan.setTotalBiaya(kendaraan.getTotalBiaya() - kendaraan.getTotalBiaya() / 10);
            return kendaraan;
        }
        return kendaraan;
    }

    public Kendaraan calculate(Kendaraan kendaraan){
        //durasi sudah ditentukan
        kendaraan = diskon(kendaraan);
        return kendaraan;
    }

    public Kendaraan calculate(Kendaraan kendaraan, int jamMasuk, int jamKeluar){
        if (jamKeluar < jamMasuk){
            kendaraan.setWaktu(24 - jamMasuk + jamKeluar);
        } else {
            kendaraan.setWaktu(jamKeluar - jamMasuk);
        }
        kendaraan = diskon(kendaraan);
        return kendaraan;
    }


}
