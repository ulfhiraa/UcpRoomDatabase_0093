package com.example.pam_ucp2.ui.viewmodel.supplier

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_ucp2.data.entity.Supplier
import com.example.pam_ucp2.data.repository.RepositorySpl
import kotlinx.coroutines.launch

//  untuk mengelola input data supplier, validasi form, penyimpanan data ke database, dan pengaturan pesan snackbar.
class SupplierViewModel(private val repositorySpl: RepositorySpl) : ViewModel()
{
    var uiState by mutableStateOf(SplUIState()) // event dapat berubah

    // memperbarui state berdasarkan input pengguna
    // untuk mengubah tampilan di textfield
    fun updateState(supplierEvent: SupplierEvent) {
        uiState = uiState.copy(
            supplierEvent = supplierEvent,
        )
    }

    private fun validateFields(): Boolean {
        val event = uiState.supplierEvent
        val errorState = FormErrorState(
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            kontak = if (event.kontak.isNotEmpty()) null else "Kontak tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // menyimpan data ke repository
    // menyimpan data ke database. dao untuk menghashnya melalui sebuah repository
    fun saveData() {
        val currentEvent = uiState.supplierEvent

        if (validateFields()) { // kalau datanya benar, tidak kosong
            viewModelScope.launch { // memunculkan view model scope
                try { // menggunakan try agar kita tahu errornya apa
                    repositorySpl.insertSpl(currentEvent.toSupplierEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        supplierEvent = SupplierEvent(), // Reset input form
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
    val id: Int = 0,
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