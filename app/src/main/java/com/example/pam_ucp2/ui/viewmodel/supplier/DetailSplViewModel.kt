package com.example.pam_ucp2.ui.viewmodel.supplier

// memindahkan data dari entity ke ui
fun Supplier.toDetailUiEvent(): SupplierEvent {
    return SupplierEvent(
        id = id,
        nama = nama,
        kontak = kontak,
        alamat = alamat
    )
}