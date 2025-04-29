package com.uts.soal3;

public class LotreBoard {
    public char[][] board;
    public boolean[][] revealed;
    public int[][] data;

    public LotreBoard() {
        board = new char[4][5];
        data = new int[4][5];
        revealed = new boolean[4][5];
    }
}
