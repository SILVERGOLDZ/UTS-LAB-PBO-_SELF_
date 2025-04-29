package com.uts.soal3;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Methods methods = new Methods();
        boolean isBomb = true;
        
        LotreBoard lotreBoard = new LotreBoard();
        Methods.generateBoard(lotreBoard); //pasang 2 bom

        while (isBomb){
            
            methods.displayBoard(lotreBoard); //tampilkan papan

            int baris, kolom;
            while (true) {
                System.out.print("Masukkan baris dan kolom (contoh: 1 2): ");
                String line = scanner.nextLine();
                String[] parts = line.trim().split("\\s+");
            
                if (parts.length == 2) {
                    try {
                        baris = Integer.parseInt(parts[0]) - 1;
                        kolom = Integer.parseInt(parts[1]) - 1;
            
                        if (baris >= 0 && baris < 4 && kolom >= 0 && kolom < 5) {
                            break;
                        } else {
                            System.out.println("\nBaris atau kolom di luar jangkauan. Coba lagi.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("\nInput harus berupa angka. Coba lagi.");
                    }
                } else {
                    System.out.println("\nMasukkan tepat dua angka dipisah spasi. Coba lagi.");
                }
            }

            isBomb = methods.guess(lotreBoard, baris, kolom);
        }

        methods.displayBoard(lotreBoard); //tampilkan papan setelah game over
        methods.isGameOver();
        scanner.close();
    }
}
