package com.example.pam_ucp2.ui.view.barang

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pam_ucp2.data.entity.Barang
import com.example.pam_ucp2.ui.viewmodel.barang.DetailUiState
import com.example.pam_ucp2.ui.viewmodel.barang.toBarangEntity


@Composable
fun BodyDetailBrg( // untuk menampilkan tampilan detail barang
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState(), // isLoading, isError, ada data atau tidak ada data
    onDeleteClick: () -> Unit = { } // Fungsi callback yang akan dipanggil saat pengguna mengonfirmasi penghapusan data.
){
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    when {
        detailUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator() // tampilkan loading
            }
        }

        detailUiState.isUiEventNotEmpty -> {
            Column (
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                ItemDetailBrg(
                    barang = detailUiState.detailUiEvent.toBarangEntity(),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = {
                        deleteConfirmationRequired = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Delete")
                }

                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        detailUiState.isUiEventEmpty -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Data tidak ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailBrg( // menampilkan informasi detail barang pada card
    modifier: Modifier = Modifier,
    barang: Barang
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailBrg(judul = "Id", isinya = barang.id)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailBrg(judul = "Nama", isinya = barang.nama)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailBrg(judul = "Deskripsi", isinya = barang.deskripsi)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailBrg(judul = "Harga", isinya = barang.harga)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailBrg(judul = "Stok", isinya = barang.stok)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailBrg(judul = "Nama Supplier", isinya = barang.namaSupplier)
            Spacer(modifier = Modifier.padding(4.dp))
        }

    }
}

@Composable
fun ComponentDetailBrg( // menampilkan detail informasi brgk
    modifier: Modifier = Modifier,
    judul: String, // menampilkan judul dari informasi
    isinya: String // menampilkan isi dari informasi
){
    Column (
        modifier = modifier.fillMaxWidth(),

        horizontalAlignment = Alignment.Start
    ){
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DeleteConfirmationDialog( // Konfirmasi penghapusan data
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(onDismissRequest = { },
        title = { Text("Delete Data") },
        text = { Text("Apakah Anda yakin ingin menghapus data? ") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}