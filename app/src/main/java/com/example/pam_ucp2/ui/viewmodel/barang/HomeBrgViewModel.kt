package com.example.pam_ucp2.ui.viewmodel.barang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_ucp2.data.entity.Barang
import com.example.pam_ucp2.data.repository.RepositoryBrg
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

/*
untuk mengelola status UI pada tampilan utama mahasiswa,
termasuk menangani status loading, error, dan data mahasiswa.
*/
class HomeBrgViewModel ( private val repositoryBrg: RepositoryBrg) : ViewModel()
{
    val homeUiState: StateFlow<HomeUiState> = repositoryBrg.getAllBrg()
        .filterNotNull()
        .map {
            HomeUiState(
                listBrg = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(
                isLoading = true,
            )
        )

}

// state; mengubah tampilan
data class HomeUiState(
    val listBrg: List<Barang> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)