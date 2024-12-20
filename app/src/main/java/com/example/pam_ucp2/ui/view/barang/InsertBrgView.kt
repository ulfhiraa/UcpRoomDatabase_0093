package com.example.pam_ucp2.ui.view.barang

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_ucp2.ui.customwidget.TopAppBar
import com.example.pam_ucp2.ui.navigasi.AlamatNavigasi
import com.example.pam_ucp2.ui.viewmodel.PenyediaViewModel
import com.example.pam_ucp2.ui.viewmodel.barang.BarangEvent
import com.example.pam_ucp2.ui.viewmodel.barang.BarangViewModel
import com.example.pam_ucp2.ui.viewmodel.barang.BrgUIState
import com.example.pam_ucp2.ui.viewmodel.barang.FormErrorState
import kotlinx.coroutines.launch

// menambah destinasi untuk halaman insert barang
object DestinasiInsertBrg : AlamatNavigasi {
    override val route: String = "iDestinasiInsertBrg"
}

@Composable
fun InsertBrgView( // untuk menampilkan form input barang dengan snackbar
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BarangViewModel = viewModel(factory = PenyediaViewModel.Factory) // Inisialisasi viewModel
){
    val uiState = viewModel.uiState // ambil UI state dari viewModel
    val snackbarHostState = remember { SnackbarHostState() } // Snackbar state
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message) // Tampilkan Snackbar
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ){ padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                //.padding(padding)
                .padding(16.dp)
        ){
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Barang",
                modifier = modifier
            )
            // Isi Body
            InsertBodyBrg(
                uiState = uiState,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent) // update state di ViewModel
                },
                onClick = {
                    viewModel.saveData()
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyBrg( // Menambahkan tampilan form untuk memasukkan data barang dan button simpan.
    modifier: Modifier = Modifier,
    onValueChange: (BarangEvent) -> Unit,
    uiState: BrgUIState,
    onClick: () -> Unit
) {
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormBarang(
            barangEvent = uiState.barangEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormBarang(
    barangEvent: BarangEvent = BarangEvent(),
    onValueChange: (BarangEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
){
    Column() {
        // TEXTFIELD NAMA
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.nama,
            onValueChange = {
                onValueChange(barangEvent.copy(nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan nama") },
        )
        Text( // validasi error pada text field
            text = errorState.nama ?: "",
            color = Color.Red
        )

        // TEXTFIELD DESKRIPSI
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.deskripsi,
            onValueChange = {
                onValueChange(barangEvent.copy(deskripsi = it))
            },
            label = { Text("Deskripsi") },
            isError = errorState.deskripsi != null,
            placeholder = { Text("Masukkan deskripsi") },
        )
        Text( // validasi error pada text field
            text = errorState.deskripsi ?: "",
            color = Color.Red
        )

        // TEXTFIELD HARGA
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = if (barangEvent.harga == 0.0) "" else barangEvent.harga.toString(), // Konversi Double ke String
            onValueChange = { inputValue ->
                val newHarga = inputValue.toDoubleOrNull() // Konversi String ke Double
                if (newHarga != null) {
                    onValueChange(barangEvent.copy(harga = newHarga)) // Update harga
                }
            },
            label = { Text("Harga") },
            isError = errorState.harga != null,
            placeholder = { Text("Masukkan harga") },
        )
        Text(
            text = errorState.harga ?: "",
            color = Color.Red
        )

        // TEXTFIELD STOK
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = if (barangEvent.stok == 0) "" else barangEvent.stok.toString(), // Konversi Int ke String
            onValueChange = { inputValue ->
                val newStok = inputValue.toIntOrNull() // Konversi String ke Int
                if (newStok != null) {
                    onValueChange(barangEvent.copy(stok = newStok)) // Update stok
                }
            },
            label = { Text("Stok") },
            isError = errorState.stok != null,
            placeholder = { Text("Masukkan stok") },
        )
        Text(
            text = errorState.stok ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Supplier")

        // DROPDOWN NAMA SUPPLIER
        // Menambahkan form input barang dengan validasi dan dropdown supplier
//        Row (
//            modifier = modifier.fillMaxWidth()
//        ){
//            namaSupplier.forEach{ namaSupplier ->
//                Row (
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Start
//                ){
//                    RadioButton(
//                        selected = barangEvent.namaSupplier == namaSupplier,
//                        onClick = {
//                            onValueChange(barangEvent.copy(namaSupplier = namaSupplier))
//                        },
//                    )
//                    Text(
//                        text = namaSupplier,
//                    )
//                }
//            }
//        }
        Text(
            text = errorState.namaSupplier ?: "",
            color = Color.Red
        )
    }
}