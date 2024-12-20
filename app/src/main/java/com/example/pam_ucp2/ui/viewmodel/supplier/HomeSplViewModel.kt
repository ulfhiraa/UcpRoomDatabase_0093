package com.example.pam_ucp2.ui.viewmodel.supplier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_ucp2.data.entity.Supplier
import com.example.pam_ucp2.data.repository.RepositorySpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

// untuk mengelola status UI pada tampilan utama supplier, termasuk menangani status loading, error, dan data supplier.
class HomeSplViewModel ( private val repositorySpl: RepositorySpl) : ViewModel()
{
    val homeUiState: StateFlow<HomeUiStateSpl> = repositorySpl.getAllSpl()
        .filterNotNull()
        .map {
            HomeUiStateSpl(
                listSpl = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeUiStateSpl(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiStateSpl(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiStateSpl(
                isLoading = true,
            )
        )
}

// state; untuk mengubah tampilan
data class HomeUiStateSpl(
    val listSpl: List<Supplier> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)