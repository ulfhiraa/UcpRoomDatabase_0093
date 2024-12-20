package com.example.pam_ucp2.ui.viewmodel.supplier

import com.example.pam_ucp2.data.entity.Supplier

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