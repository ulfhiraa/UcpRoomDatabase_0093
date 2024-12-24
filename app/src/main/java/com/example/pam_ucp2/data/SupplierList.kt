package com.example.pam_ucp2.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_ucp2.ui.viewmodel.PenyediaViewModel
import com.example.pam_ucp2.ui.viewmodel.supplier.HomeSplViewModel

object SupplierList { // objek untuk menampung daftar nama supplier
    @Composable
    fun NamaSpl(
        // untuk mengambil daftar nama spl dari ViewModel (HomeSplViewModel) dan mengembalikannya sebagai List<String>.
        HomeSpl: HomeSplViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<String> {
        // Mengumpulkan data dari ViewModel
        val getNamaSpl = HomeSpl.homeUiState.collectAsState()
        val listSupplier = getNamaSpl.value.listSpl // Berisi daftar semua supplier yang diambil dari ViewModel.

        val namaSupplier = listSupplier.map { supplier -> supplier.nama }
       // Untuk setiap supplier, hanya nama yang diambil dan disimpan dalam namaSupplier.

        return namaSupplier
       // Fungsi ini mengembalikan daftar nama supplier sebagai List<String>.

    }
}

// Fungsi ini membantu untuk mendapatkan hanya nama supplier
// dari semua data supplier di ViewModel, sehingga lebih mudah untuk ditampilkan di UI,
// seperti dalam dropdown menu atau daftar pilihan.