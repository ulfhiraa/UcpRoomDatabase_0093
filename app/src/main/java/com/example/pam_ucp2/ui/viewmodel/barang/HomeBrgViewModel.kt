package com.example.pam_ucp2.ui.viewmodel.barang

import com.example.pam_ucp2.data.entity.Barang

// state; mengubah tampilan
data class HomeUiState(
    val listMhs: List<Barang> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)