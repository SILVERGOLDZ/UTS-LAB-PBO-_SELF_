CREATE TABLE IF NOT EXISTS `karyawans` (
    `id` INTEGER PRIMARY KEY,
    `nama` VARCHAR(255) NOT NULL,
    `posisi` TEXT NOT NULL,
    `gaji` FLOAT NOT NULL,
    `divisi` TEXT NOT NULL,
    `tanggal_masuk` DATE NOT NULL
);


