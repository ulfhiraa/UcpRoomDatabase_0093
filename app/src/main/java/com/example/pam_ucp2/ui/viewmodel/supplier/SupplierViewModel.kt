package com.example.pam_ucp2.ui.viewmodel.supplier

import com.example.pam_ucp2.data.entity.Supplier

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
    id = id,
    nama = nama,
    kontak = kontak,
    alamat = alamat
)