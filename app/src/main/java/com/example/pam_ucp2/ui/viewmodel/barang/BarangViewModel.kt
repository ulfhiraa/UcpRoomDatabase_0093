package com.example.pam_ucp2.ui.viewmodel.barang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_ucp2.data.entity.Barang
import com.example.pam_ucp2.data.repository.RepositoryBrg
import kotlinx.coroutines.launch

//  untuk mengelola input data barang, validasi form, penyimpanan data ke database, dan pengaturan pesan snackbar.
class BarangViewModel(private val repositoryBrg: RepositoryBrg) : ViewModel()
{
    var uiState by mutableStateOf(BrgUIState()) // event dapat berubah

    // memperbarui state berdasarkan input pengguna
    // untuk mengubah tampilan di textfield
    fun updateState(barangEvent: BarangEvent) {
        uiState = uiState.copy(
            barangEvent = barangEvent,
        )
    }

    private fun validateFields(): Boolean {
        val event = uiState.barangEvent
        val errorState = FormErrorState(
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            deskripsi = if (event.deskripsi.isNotEmpty()) null else "Deskripsi tidak boleh kosong",
            harga = if (event.harga > 0) null else "Harga tidak boleh kosong", // Pastikan harga > 0
            stok = if (event.stok > 0) null else "Stok tidak boleh kosong", // Pastikan stok > 0
            namaSupplier = if (event.namaSupplier.isNotEmpty()) null else "Nama Supplier tidak boleh kosong"
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // menyimpan data ke repository
    // menyimpan data ke database. dao untuk menghashnya melalui sebuah repository
    fun saveData() {
        val currentEvent = uiState.barangEvent

        if (validateFields()) { // kalau datanya benar, tidak kosong
            viewModelScope.launch { // memunculkan view model scope
                try { // menggunakan try agar kita tahu errornya apa
                    repositoryBrg.insertBrg(currentEvent.toBarangEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        barangEvent = BarangEvent(), // Reset input form
                        isEntryValid = FormErrorState() // Reset error state
                    )
                } catch (e: Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data Anda."
            )
        }
    }

    // Reset pesan Snackbar setelah ditampilkan
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}

// event adalah aksi yang merubah kondisi
// state adalah keadaan yang terjadi setelah ada trigger dari event
// Menambahkan data class BrgUIState untuk merepresentasikan state UI, termasuk event, validasi input, dan pesan snackbar.
data class BrgUIState(
    val barangEvent: BarangEvent = BarangEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)

// untuk menghandle atau memberikan nilai validasi apakah data benar atau tidak
data class FormErrorState(
    val nama: String? = null,
    val deskripsi: String? = null,
    val harga: String? = null,
    val stok: String? = null,
    val namaSupplier: String? = null
) {
    fun isValid(): Boolean {
        return nama == null && deskripsi == null &&
                harga == null && stok == null && namaSupplier == null
    }
}

// data class variabel yang menyimpan data input form
data class BarangEvent(
    val id: Int = 0,  // Default 0 karena ID auto increment
    val nama: String = "",
    val deskripsi: String = "",
    val harga: Double = 0.0,
    val stok: Int = 0,
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