package com.example.pam_ucp2.ui.viewmodel.barang


// Menyimpan input form ke dalam entity
fun BarangEvent.toBarangEntity(): Barang = Barang(
    id = id,
    nama = nama,
    deskripsi = deskripsi,
    harga = harga,
    stok = stok,
    namaSupplier = namaSupplier
)