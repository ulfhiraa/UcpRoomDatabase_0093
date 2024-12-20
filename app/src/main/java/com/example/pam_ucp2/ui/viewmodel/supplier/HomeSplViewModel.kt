package com.example.pam_ucp2.ui.viewmodel.supplier

import com.example.pam_ucp2.data.entity.Supplier

// state; untuk mengubah tampilan
data class HomeUiState(
    val listBrg: List<Supplier> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)