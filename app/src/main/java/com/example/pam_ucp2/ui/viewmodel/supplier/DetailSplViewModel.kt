package com.example.pam_ucp2.ui.viewmodel.supplier

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_ucp2.data.entity.Supplier
import com.example.pam_ucp2.data.repository.RepositorySpl
import com.example.pam_ucp2.ui.navigasi.DestinasiDetailSpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

// untuk mengelola data detail supplier, termasuk pengambilan data, dan penanganan error.
class DetailSplViewModel(savedStateHandle: SavedStateHandle, private val repositorySpl: RepositorySpl, ) : ViewModel()
{
    private  val _id: String =  checkNotNull(savedStateHandle[DestinasiDetailSpl.ID])

    val detailUiState: StateFlow<DetailUiState> = repositorySpl.getSpl(_id)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoading = true,
            )
        )
}

// Data Class untuk menampung data yang akan ditampilkan di UI
data class DetailUiState(
    val detailUiEvent: SupplierEvent = SupplierEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == SupplierEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != SupplierEvent()
}

// memindahkan data dari entity ke ui
fun Supplier.toDetailUiEvent(): SupplierEvent {
    return SupplierEvent(
        id = id,
        nama = nama,
        kontak = kontak,
        alamat = alamat
    )
}