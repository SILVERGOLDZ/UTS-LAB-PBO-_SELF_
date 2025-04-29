package com.uts.soal2;

public class Kendaraan {
    String jenis;
    int waktu;
    double harga;
    double totalBiaya;

    public Kendaraan(){

    }
    
    public Kendaraan(String jenis, int waktu) {
        this.jenis = jenis;
        this.waktu = waktu;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
    public void setWaktu(int waktu) {
        this.waktu = waktu;
    }
    public void setHarga(String Jenis){
        switch (Jenis) {
            case "Motor":
                this.harga = 2000;
                break;

            case "Mobil":
                this.harga = 5000;
                break;

            case "Truk":
                this.harga = 8000;
                break;
        
                default:
                break;
            }
        }
    public void setTotalBiaya(double totalBiaya) {
        this.totalBiaya = totalBiaya;
    }
        public String getJenis() {
        return jenis;
    }
    public int getWaktu() {
        return waktu;
    }
    public double getHarga(){
        return harga;
    }
    public double getTotalBiaya() {
        return totalBiaya;
    }


}
