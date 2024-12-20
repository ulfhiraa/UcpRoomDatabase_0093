package com.example.pam_ucp2.ui.viewmodel

import DetailBrgViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pam_ucp2.TokoApp
import com.example.pam_ucp2.ui.viewmodel.barang.BarangViewModel
import com.example.pam_ucp2.ui.viewmodel.barang.HomeBrgViewModel
import com.example.pam_ucp2.ui.viewmodel.barang.UpdateBrgViewModel
import com.example.pam_ucp2.ui.viewmodel.supplier.HomeSplViewModel
import com.example.pam_ucp2.ui.viewmodel.supplier.SupplierViewModel


object PenyediaViewModel {
    val Factory = viewModelFactory {
        // BARANG
        initializer {
            BarangViewModel(
                TokoApp().containerApp.repositoryBrg
            )
        }

        initializer {
            HomeBrgViewModel (
                TokoApp().containerApp.repositoryBrg
            )
        }

        initializer {
            DetailBrgViewModel (
                createSavedStateHandle(), //  untuk menyimpan data sementara di ViewModel.
                TokoApp().containerApp.repositoryBrg
            )
        }

        initializer {
            UpdateBrgViewModel (
                createSavedStateHandle(), // Dengan SavedStateHandle, data akan tetap tersimpan dan bisa diambil kembali.
                TokoApp().containerApp.repositoryBrg
            )
        }

        // SUPPLIER
        initializer {
            SupplierViewModel(
                TokoApp().containerApp.repositorySpl
            )
        }

        initializer {
            HomeSplViewModel (
                TokoApp().containerApp.repositorySpl
            )
        }
    }
}

fun CreationExtras.TokoApp(): TokoApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TokoApp)