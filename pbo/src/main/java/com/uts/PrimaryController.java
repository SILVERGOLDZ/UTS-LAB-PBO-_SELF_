package com.uts;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSoal1() throws IOException {
        App.setRoot("soal1");
    }

    @FXML
    private void switchToSoal2() throws IOException {
        try {
            App.setRoot("soal2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToSoal3() throws IOException {
        App.setRoot("soal3");
    }
}
