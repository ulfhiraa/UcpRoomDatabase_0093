package com.example.pam_ucp2.ui.viewmodel.supplier

import com.example.pam_ucp2.data.entity.Supplier



// Menyimpan input form ke dalam entity
fun SupplierEvent.toSupplierEntity(): Supplier = Supplier(
    id = id,
    nama = nama,
    kontak = kontak,
    alamat = alamat
)