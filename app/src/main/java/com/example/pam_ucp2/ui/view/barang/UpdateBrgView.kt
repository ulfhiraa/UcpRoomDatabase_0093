package com.example.pam_ucp2.ui.view.barang

import com.example.pam_ucp2.data.entity.Barang
import com.example.pam_ucp2.ui.viewmodel.barang.BrgUIState
import toDetailUiEvent

// untuk konversi objek Barang ke UIState Brg.
fun Barang.toUIStateBrg() : BrgUIState = BrgUIState(
    barangEvent = this.toDetailUiEvent(),
)