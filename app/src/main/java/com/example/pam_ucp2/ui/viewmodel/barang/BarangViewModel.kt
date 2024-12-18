package com.example.pam_ucp2.ui.viewmodel.barang

// event adalah aksi yang merubah kondisi
// state adalah keadaan yang terjadi setelah ada trigger dari event
data class BrgUIState(
    val barangEvent: BarangEvent = BarangEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)

// untuk menghandle atau memberikan nilai validasi apakah data benar atau tidak
data class FormErrorState(
    val id: String? = null,
    val nama: String? = null,
    val deskripsi: String? = null,
    val harga: String? = null,
    val stok: String? = null,
    val namaSupplier: String? = null
) {
    fun isValid(): Boolean {
        return id == null && nama == null && deskripsi == null &&
                harga == null && stok == null && namaSupplier == null
    }
}

// data class variabel yang menyimpan data input form
data class BarangEvent(
    val id: String = "",
    val nama: String = "",
    val deskripsi: String = "",
    val harga: String = "",
    val stok: String = "",
    val namaSupplier: String = ""
)

// Menyimpan input form ke dalam entity
fun BarangEvent.toBarangEntity(): Barang = Barang(
    id = id,
    nama = nama,
    deskripsi = deskripsi,
    harga = harga,
    stok = stok,
    namaSupplier = namaSupplier
)