package com.example.pam_ucp2.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable // untuk button navigasi ke halaman barang dan supplier
fun PilihanHomeView(
    onBarangClick: () -> Unit,
    onSupplierClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pilih Halaman",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Tombol untuk Home Barang
            androidx.compose.material3.Button(
                onClick = onBarangClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(text = "Home Barang")
            }

            // Tombol untuk Home Supplier
            androidx.compose.material3.Button(
                onClick = onSupplierClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Home Supplier")
            }
        }
    }
}
