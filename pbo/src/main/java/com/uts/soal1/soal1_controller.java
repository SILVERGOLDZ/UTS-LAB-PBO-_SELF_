package com.uts.soal1;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Date;
import java.util.Optional;

import javafx.scene.control.TableCell;

public class soal1_controller {
    @FXML private Label Input_Berhasil;

    @FXML private TableColumn<Divisi, Void> Edit_Button;
    
    @FXML
    private TextField Karyawan_Id_Input;
    @FXML
    private TextField Karyawan_Nama_Input;
    @FXML
    private TextField Karyawan_Posisi_Input;
    @FXML
    private TextField Karyawan_Gaji_Input;
    @FXML
    private TextField Karyawan_Divisi_Input;

    @FXML
    private TableView<Divisi> Karyawan_List_Table;
    @FXML
    private TableColumn<Divisi, Integer> Karyawan_Id;
    @FXML
    private TableColumn<Divisi, String> Karyawan_Nama;
    @FXML
    private TableColumn<Divisi, String> Karyawan_Posisi;
    @FXML
    private TableColumn<Divisi, Double> Karyawan_Gaji;
    @FXML
    private TableColumn<Divisi, String> Karyawan_Divisi;
    @FXML
    private TableColumn<Divisi, Date> Karyawan_Tanggal_Masuk;

    @FXML private Button Simpan_Edit;
    @FXML private Button Cancel;
    @FXML private Button Karyawan_Tambah_Button;
    @FXML private Button Karyawan_Hapus_Button;
    @FXML private Button Karyawan_Cari_Button;
    @FXML private Button Karyawan_Ubah_Button;

    @FXML
    public void initialize(){
        tampilkanKaryawan();
        Simpan_Edit.setManaged(false);
        Cancel.setManaged(false);
    }

    //global declaration
    Perusahaan perusahaan = new Perusahaan();

    @FXML
    private void clearForm() {
        Karyawan_Id_Input.clear();
        Karyawan_Nama_Input.clear();
        Karyawan_Posisi_Input.clear();
        Karyawan_Gaji_Input.clear();
        Karyawan_Divisi_Input.clear();
    }

    @FXML
    private void tampilkanKaryawan(){
        try {
            //array list sebelum ditampilakan
            ObservableList<Divisi> karyawans = perusahaan.getKaryawans();

            Karyawan_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
            Karyawan_Nama.setCellValueFactory(new PropertyValueFactory<>("nama"));
            Karyawan_Posisi.setCellValueFactory(new PropertyValueFactory<>("posisi"));
            Karyawan_Gaji.setCellValueFactory(new PropertyValueFactory<>("gaji"));
            Karyawan_Divisi.setCellValueFactory(new PropertyValueFactory<>("namaDivisi"));
            Karyawan_Tanggal_Masuk.setCellValueFactory(new PropertyValueFactory<>("tanggalMasuk"));
            
            Karyawan_List_Table.getItems().clear(); // clear old data
            Karyawan_List_Table.setItems(karyawans); 
            Karyawan_List_Table.refresh(); 

        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }




    @FXML
    private void tambahKaryawan(){
        try {
            // sebelum di ubah, cek dulu apakah kosong
            String idText = Karyawan_Id_Input.getText().trim();
            String gajiText = Karyawan_Gaji_Input.getText().trim();

            String nama = Karyawan_Nama_Input.getText().trim();
            String posisi = Karyawan_Posisi_Input.getText().trim();
            String namaDivisi = Karyawan_Divisi_Input.getText().trim();
            
            if (idText.isEmpty() || nama.isEmpty() || posisi.isEmpty() || gajiText.isEmpty() || namaDivisi.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Semua field harus diisi.");
                alert.showAndWait();
                return; // stop //
            }

            // jika tidak kosong, ubah menjadi tipenya
            int id = Integer.parseInt(idText);
            Double gaji = Double.parseDouble(gajiText);

            if (id < 0 || gaji < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("ID dan Gaji tidak boleh negatif.");
                alert.showAndWait();
                return; // stop //
            }
        
            Divisi divisi = new Divisi(id, nama, posisi, gaji, namaDivisi);

            //Memasukkan data ke Database dan check apakah id sudah ada atau tidak
            boolean check_insertion = perusahaan.addKaryawan_DB(divisi);
            // id sudah ada mengembalikan true
            if (check_insertion) {
                return;
            }
            // 7 detik label berhasil input ditampilkan
            Input_Berhasil.setVisible(true);
            Input_Berhasil.setText("Data Berhasil Ditambahkan");
            PauseTransition pause = new PauseTransition(Duration.seconds(7));
            pause.setOnFinished(event -> Input_Berhasil.setVisible(false));
            pause.play();
        
            tampilkanKaryawan();
            clearForm();
            
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Input untuk Id atau Gaji");
            alert.setHeaderText(null);
            alert.setContentText("Perbaiki inputan ID dan Gaji");
            alert.showAndWait();
            System.out.println("Invalid number input: " + e.getMessage());
        }
    }

    @FXML
    private void addEditButtonToTable() {
    Edit_Button.setCellFactory(param -> new TableCell<Divisi, Void>() {
        private final Button editBtn = new Button("Edit");

        {
            editBtn.setOnAction(e -> {
                Divisi selected = getTableView().getItems().get(getIndex());
                if (selected != null) {
                    // Fill the inputs with selected data //
                    Karyawan_Id_Input.setText(String.valueOf(selected.getId()));
                    Karyawan_Nama_Input.setText(selected.getNama());
                    Karyawan_Posisi_Input.setText(selected.getPosisi());
                    Karyawan_Gaji_Input.setText(String.valueOf(selected.getGaji()));
                    Karyawan_Divisi_Input.setText(selected.getNamaDivisi());
                }
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(editBtn);
            }
        }
    });
    
    
}
    @FXML
    private void handleUbahButton() {
        if (!Edit_Button.isVisible()) {
            Edit_Button.setVisible(true);
            Simpan_Edit.setVisible(true);
            Simpan_Edit.setManaged(true);
            Cancel.setVisible(true);
            Cancel.setManaged(true);

            Karyawan_Tambah_Button.setVisible(false);
            Karyawan_Hapus_Button.setVisible(false);
            Karyawan_Cari_Button.setVisible(false);
            Karyawan_Ubah_Button.setVisible(false);

            Karyawan_Id_Input.setEditable(false);
            addEditButtonToTable();
        }
    }

    @FXML
    private void simpanKaryawan(){
        try {
            int id = Integer.parseInt(Karyawan_Id_Input.getText().trim());
            String nama = Karyawan_Nama_Input.getText().trim();
            String posisi = Karyawan_Posisi_Input.getText().trim();
            Double gaji = Double.parseDouble(Karyawan_Gaji_Input.getText().trim());
            String namaDivisi = Karyawan_Divisi_Input.getText().trim();

            if (id < 0 || gaji < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("ID dan Gaji tidak boleh negatif.");
                alert.showAndWait();
                return; // stop //
            }
        
            Divisi divisi = new Divisi(id, nama, posisi, gaji, namaDivisi);

            //Memasukkan data ke Database
            perusahaan.editKaryawans_DB(divisi);
            
            // 7 detik label berhasil input ditampilkan
            Input_Berhasil.setVisible(true);
            Input_Berhasil.setText("Data Berhasil Disimpan");
            PauseTransition pause = new PauseTransition(Duration.seconds(7));
            pause.setOnFinished(event -> Input_Berhasil.setVisible(false));
            pause.play();
        
            Edit_Button.setVisible(false);
            Simpan_Edit.setVisible(false);
            Simpan_Edit.setManaged(false);
            Cancel.setVisible(false);
            Cancel.setManaged(false);
    
            Karyawan_Tambah_Button.setVisible(true);
            Karyawan_Hapus_Button.setVisible(true);
            Karyawan_Cari_Button.setVisible(true);
            Karyawan_Ubah_Button.setVisible(true);
    
            Karyawan_Id_Input.setEditable(true);
            tampilkanKaryawan();
            clearForm();
            
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Input untuk Id atau Gaji");
            alert.setHeaderText(null);
            alert.setContentText("Perbaiki inputan ID dan Gaji");
            alert.showAndWait();
            System.out.println("Invalid number input: " + e.getMessage());
        }
    }

    @FXML
    private void cancelEdit(){
        Edit_Button.setVisible(false);
        Simpan_Edit.setVisible(false);
        Simpan_Edit.setManaged(false);
        Cancel.setVisible(false);
        Cancel.setManaged(false);

        Karyawan_Tambah_Button.setVisible(true);
        Karyawan_Hapus_Button.setVisible(true);
        Karyawan_Cari_Button.setVisible(true);
        Karyawan_Ubah_Button.setVisible(true);

        Karyawan_Id_Input.setEditable(true);
        clearForm();
    }

    // HAPUS BUTTON //
    @FXML
    private void addHapusButton() {
        Edit_Button.setCellFactory(param -> new TableCell<Divisi, Void>() {
            private final Button editBtn = new Button("Hapus");

            {
                editBtn.setOnAction(e -> {
                    Divisi selected = getTableView().getItems().get(getIndex());
                    // Konfirmasi penghapusan //
                    if (selected != null) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Konfirmasi Hapus");
                        alert.setHeaderText("Yakin ingin menghapus karyawan ini?");
                        alert.setContentText("ID: " + selected.getId() + "\nNama: " + selected.getNama());

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            perusahaan.hapusKaryawan(selected);
                            Edit_Button.setVisible(false);
                            Cancel.setVisible(false);
                            Cancel.setManaged(false);

                            Karyawan_Tambah_Button.setVisible(true);
                            Karyawan_Hapus_Button.setVisible(true);
                            Karyawan_Cari_Button.setVisible(true);
                            Karyawan_Ubah_Button.setVisible(true);
                            clearForm();
                            tampilkanKaryawan(); // refresh tabel
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editBtn);
                }
            }
        });
    }

    @FXML
    private void handleHapusButton() {
        if (!Edit_Button.isVisible()) {
            Edit_Button.setVisible(true);
            Cancel.setVisible(true);
            Cancel.setManaged(true);

            Karyawan_Tambah_Button.setVisible(false);
            Karyawan_Hapus_Button.setVisible(false);
            Karyawan_Cari_Button.setVisible(false);
            Karyawan_Ubah_Button.setVisible(false);

            addHapusButton();
        }
    }
}
