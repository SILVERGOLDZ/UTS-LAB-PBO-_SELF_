package com.uts.soal3;


public class Methods {

    public void isGameOver() {
        System.out.println("Game Over! Anda menemukan bom.");
    }

    public boolean guess(LotreBoard lotreBoard, int baris, int kolom) {
        if (lotreBoard.data[baris][kolom] == 1) {
            lotreBoard.revealed[baris][kolom] = true;
            return false; // Bom ditemukan, matikan perulangan pada Main.java
        } else {
            lotreBoard.board[baris][kolom] = 'O';
            lotreBoard.revealed[baris][kolom] = true;
            return true; // Tidak ada bom
        }
    }

    public void displayBoard(LotreBoard lotreBoard) {
        for (int i = 0; i < lotreBoard.board.length; i++) {
            for (int j = 0; j < lotreBoard.board[i].length; j++) {
                if (lotreBoard.revealed[i][j]) {
                    System.out.print(lotreBoard.data[i][j] + " "); //menampilkan angka 1 atau 0
                } else {
                    System.out.print(lotreBoard.board[i][j] + " "); //menampilkan bintang
                }
            }
            System.out.println();
        }
    }

    public static void generateBoard(LotreBoard lotreBoard) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                lotreBoard.board[i][j] = '*';
                lotreBoard.data[i][j] = 0;
                lotreBoard.revealed[i][j] = false;
            }
        }

        int bombsPlaced = 0;
        while (bombsPlaced < 2) {
            int baris = (int) (Math.random() * 4);  
            int kolom = (int) (Math.random() * 5);  
        
            if (lotreBoard.data[baris][kolom] == 0) { // memastikan bahwa bom tidak diletak di tempat yang sama
                lotreBoard.data[baris][kolom] = 1;
                bombsPlaced++;
            }
        }
    }
    
}
