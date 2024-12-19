package com.example.pam_ucp2.ui.viewmodel.barang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_ucp2.data.entity.Barang
import com.example.pam_ucp2.data.repository.RepositoryBrg
import com.example.pam_ucp2.ui.navigasi.DestinasiUpdateBrg
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import toDetailUiEvent

// Menambahkan View untuk mengupdate data barang dengan validasi dan handling error.
class UpdateBrgViewModel ( savedStateHandle: SavedStateHandle, private val repositoryBrg: RepositoryBrg) : ViewModel()
{
    var updateUIState by mutableStateOf(BrgUIState())
        private set

    private val _id: String = checkNotNull(savedStateHandle[DestinasiUpdateBrg.ID])

    init {
        viewModelScope.launch {
            updateUIState = repositoryBrg.getBrg(_id)
                .filterNotNull()
                .first()
                .toUIStateBrg()
        }
    }

    fun updateState(barangEvent: BarangEvent) {
        updateUIState = updateUIState.copy(
            barangEvent = barangEvent,
        )
    }

    fun validateFields(): Boolean {
        val event = updateUIState.barangEvent
        val errorState = FormErrorState(
            id = if (event.id.isNotEmpty()) null else "ID tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            deskripsi = if (event.deskripsi.isNotEmpty()) null else "Deskripsi tidak boleh kosong",
            harga = if (event.harga.isNotEmpty()) null else "Harga tidak boleh kosong",
            stok = if (event.stok.isNotEmpty()) null else "Stok tidak boleh kosong",
            namaSupplier = if (event.namaSupplier.isNotEmpty()) null else "Nama Supplier tidak boleh kosong"

        )
        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val currentEvent = updateUIState.barangEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryBrg.updateBrg(currentEvent.toBarangEntity())
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        barangEvent = BarangEvent(),
                        isEntryValid = FormErrorState()
                    )
                    println("snackBarMessage diatur: ${updateUIState.
                    snackBarMessage}")
                } catch (e: Exception) {
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data gagal diupdate"
                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage() {
        updateUIState = updateUIState.copy(snackBarMessage = null)
    }
}

// untuk konversi objek Barang ke UIState Brg.
fun Barang.toUIStateBrg() : BrgUIState = BrgUIState(
    barangEvent = this.toDetailUiEvent(),
)