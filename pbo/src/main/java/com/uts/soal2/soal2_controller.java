package com.uts.soal2;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;

public class soal2_controller {

    @FXML private TextField Durasi_TextField;
    @FXML private TextField JamMasuk_TextField;
    @FXML private TextField JamKeluar_TextField;

    @FXML private Label Summary_Type;
    @FXML private Label Summary_Time;
    @FXML private Label Summary_Fee;

    @FXML private VBox Summary_Container;
    @FXML private VBox Durasi_Method_Container;
    @FXML private VBox Jam_Method_Container;

    // Drop down
    @FXML private ComboBox<String> Jenis_Kendaraan;

    @FXML
    private TableView<Kendaraan> Kendaraan_List_Table;
    @FXML
    private TableColumn<Kendaraan, String> Kendaraan_Jenis;
    @FXML
    private TableColumn<Kendaraan, Integer> Kendaraan_Durasi;
    @FXML
    private TableColumn<Kendaraan, Double> Kendaraan_TotalBiaya;

    // Deklarasi Universal
    Methods methods = new Methods();

    // Total Biaya dan total Seluruh Kendaraan
    double totalPendapatan = 0;
    int totalKendaraan = 0;

    //untuk tabel
    ObservableList<Kendaraan> kendaraan_list = FXCollections.observableArrayList();




    @FXML
    private void initialize(){
        Jenis_Kendaraan.getItems().addAll("Mobil", "Motor", "Truk");
    }

    @FXML
    private void showDurasi(){
        Durasi_Method_Container.setVisible(true);
        Jam_Method_Container.setVisible(false);
    }

    @FXML
    private void showJam(){
        Jam_Method_Container.setVisible(true);
        Durasi_Method_Container.setVisible(false);
    }

    @FXML 
    private void summary(Kendaraan kendaraan){

        totalPendapatan += kendaraan.getTotalBiaya();
        totalKendaraan += 1;

        kendaraan_list.add(kendaraan);

        Kendaraan_Jenis.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        Kendaraan_Durasi.setCellValueFactory(new PropertyValueFactory<>("waktu"));
        Kendaraan_TotalBiaya.setCellValueFactory(new PropertyValueFactory<>("totalBiaya"));
        
        Kendaraan_List_Table.setItems(kendaraan_list); 
        Kendaraan_List_Table.refresh(); 
        
        Summary_Type.setText("Tipe Kendaraan : " + kendaraan.getJenis());
        Summary_Time.setText("Lama Parkir : " + String.valueOf(kendaraan.getWaktu()) + " jam");
        Summary_Fee.setText("Total Biaya : Rp" + String.valueOf(kendaraan.getTotalBiaya()));

        Summary_Container.setVisible(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(10));
        pause.setOnFinished(event -> Summary_Container.setVisible(false));

        pause.play();
        
        // Summary_Type.setVisible(true);
        // Summary_Time.setVisible(true);
        // Summary_Fee.setVisible(true);

        clearForm();
    }

    @FXML
    private void finalReport(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Final Report");
        alert.setHeaderText("===== FINAL REPORT =====");
        alert.setContentText("Total Kendaraan Keseluruhan : " + totalKendaraan + 
                             "\nTotal Pendapatan dari Biaya : " + totalPendapatan + 
                             "\nThank You...");
        alert.showAndWait();

        kendaraan_list.clear();
        Kendaraan_List_Table.refresh();
        totalPendapatan = 0;
        totalKendaraan = 0;
        clearForm();
    }

    @FXML
    private void hitung(){
        try {
            Kendaraan kendaraan = new Kendaraan();
            String jenis = Jenis_Kendaraan.getValue();

            if (jenis == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Harus memilih Jenis Kendaraan");
                alert.showAndWait();
                return; // stop //
            }

            kendaraan.setJenis(jenis);
            kendaraan.setHarga(kendaraan.getJenis());
    
            String durasiText = Durasi_TextField.getText().trim();
    
            if (durasiText.isEmpty()){
                int jamMasuk =  Integer.parseInt(JamMasuk_TextField.getText().trim());
                int jamKeluar =  Integer.parseInt(JamKeluar_TextField.getText().trim());

                if (jamMasuk < 0 || jamKeluar < 0 || jamMasuk > 24 || jamKeluar > 24) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText("Kamu Bercanda?");
                    alert.setContentText("Jam tidak boleh negatif atau lebih dari 24");
                    alert.showAndWait();
                    return; // stop //
                }
                kendaraan = methods.calculate(kendaraan, jamMasuk, jamKeluar);

                summary(kendaraan);
    
            } else{
                int durasi = Integer.parseInt(durasiText); 
                if (durasi < 0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText("Kamu Bercanda?");
                    alert.setContentText("Durasi tidak boleh negatif");
                    alert.showAndWait();
                    return; // stop //
                }

                kendaraan.setWaktu(durasi);
                kendaraan = methods.calculate(kendaraan);


                summary(kendaraan);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void clearForm(){
        Durasi_TextField.clear();
        JamMasuk_TextField.clear();
        JamKeluar_TextField.clear();
    }
}
