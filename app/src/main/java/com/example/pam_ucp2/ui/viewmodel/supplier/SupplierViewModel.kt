package com.example.pam_ucp2.ui.viewmodel.supplier

import com.example.pam_ucp2.data.entity.Supplier


// event adalah aksi yang merubah kondisi
// state adalah keadaan yang terjadi setelah ada trigger dari event
// Menambahkan data class SplUIState untuk merepresentasikan state UI, termasuk event, validasi input, dan pesan snackbar.
data class SplUIState(
    val supplierEvent: SupplierEvent = SupplierEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)

// untuk menghandle atau memberikan nilai validasi apakah data benar atau tidak
data class FormErrorState(
    val nama: String? = null,
    val kontak: String? = null,
    val alamat: String? = null,
) {
    fun isValid(): Boolean {
        return nama == null && kontak == null &&  alamat == null
    }
}

// data class variabel yang menyimpan data input form
data class SupplierEvent(
    val id: Int,
    val nama: String = "",
    val kontak: String = "",
    val alamat: String = ""
)

// Menyimpan input form ke dalam entity
fun SupplierEvent.toSupplierEntity(): Supplier = Supplier(
    id = 0, // default 0 karna auto increment
    nama = nama,
    kontak = kontak,
    alamat = alamat
)