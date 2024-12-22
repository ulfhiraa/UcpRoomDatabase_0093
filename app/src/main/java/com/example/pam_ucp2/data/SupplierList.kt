package com.example.pam_ucp2.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_ucp2.ui.viewmodel.PenyediaViewModel
import com.example.pam_ucp2.ui.viewmodel.supplier.HomeSplViewModel

object SupplierList {
    @Composable
    fun NamaSpl(
        HomeSpl: HomeSplViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<String> {
        // Mengumpulkan data dari ViewModel
        val getNamaSpl = HomeSpl.homeUiState.collectAsState()
        val listSupplier = getNamaSpl.value.listSpl

        // Ekstraksi nama dari setiap supplier
        val namaSupplier = listSupplier.map { supplier -> supplier.nama }

        return namaSupplier
    }
}