import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_ucp2.data.entity.Barang
import com.example.pam_ucp2.data.repository.RepositoryBrg
import com.example.pam_ucp2.ui.navigasi.DestinasiDetailBrg
import com.example.pam_ucp2.ui.viewmodel.barang.BarangEvent
import com.example.pam_ucp2.ui.viewmodel.barang.toBarangEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/*
Menambahkan ViewModel DetailBrgViewModel untuk mengelola data detail barang,
termasuk pengambilan data, penanganan error, dan penghapusan barang.
* */

class DetailBrgViewModel( savedStateHandle: SavedStateHandle, private val repositoryBrg: RepositoryBrg, ) : ViewModel()
{
    private  val _id: String =  checkNotNull(savedStateHandle[DestinasiDetailBrg.ID])

    val detailUiState: StateFlow<DetailUiState> = repositoryBrg.getBrg(_id)
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

    fun deleteBrg(){
        detailUiState.value.detailUiEvent.toBarangEntity().let {
            viewModelScope.launch {
                repositoryBrg.deleteBrg(it)
            }
        }
    }
}

/*
Data Class untuk menampung data yang akan ditampilkan di UI
* */

data class DetailUiState(
    val detailUiEvent: BarangEvent = BarangEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == BarangEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != BarangEvent()
}


// memindahkan data dari entity ke ui
fun Barang.toDetailUiEvent(): BarangEvent {
    return BarangEvent(
        id = id,
        nama = nama,
        deskripsi = deskripsi,
        harga = harga,
        stok = stok,
        namaSupplier =  namaSupplier
    )
}