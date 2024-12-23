package com.example.pam_ucp2.ui.view.supplier

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_ucp2.R
import com.example.pam_ucp2.ui.customwidget.TopAppBar
import com.example.pam_ucp2.ui.navigasi.AlamatNavigasi
import com.example.pam_ucp2.ui.viewmodel.PenyediaViewModel
import com.example.pam_ucp2.ui.viewmodel.supplier.FormErrorState
import com.example.pam_ucp2.ui.viewmodel.supplier.SplUIState
import com.example.pam_ucp2.ui.viewmodel.supplier.SupplierEvent
import com.example.pam_ucp2.ui.viewmodel.supplier.SupplierViewModel
import kotlinx.coroutines.launch

// menambah destinasi untuk halaman insert supplier
object DestinasiInsertSpl : AlamatNavigasi {
    override val route: String = "DestinasiInsertSpl"
}

@Composable
fun SectionHeaderInsertSpl(
    onBack: () -> Unit,
    modifier: Modifier
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.LightGray, RoundedCornerShape(bottomEnd = 50.dp))
    ){
        Box(){
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Spacer(Modifier.padding(20.dp))
                TopAppBar(
                    onBack = onBack,
                    showBackButton = true,
                    judul = " T a m b a h \n\nS u p p l i e r",
                    modifier = modifier
                )
            }
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {

                Spacer(Modifier.padding(20.dp))

                Image(
                    painter = painterResource(id = R.drawable.bear),
                    contentDescription = " ",
                    Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(500.dp))
                        .shadow(50.dp, RoundedCornerShape(370.dp))
                )
            }
        }
    }
}

@Composable
fun InsertSplView( // untuk menampilkan form input supplier dengan snackbar
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SupplierViewModel = viewModel(factory = PenyediaViewModel.Factory) // Inisialisasi viewModel
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
                .padding(16.dp)
        ){
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Supplier",
                modifier = modifier
            )
            // Isi Body
            InsertBodySpl(
                uiState = uiState,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent) // update state di ViewModel
                },
                onClick = {
                    viewModel.saveData()
                    if (uiState.snackBarMessage == "Data berhasil disimpan")
                    {
                        onNavigate()
                    }
                }
            )
        }
    }
}

@Composable
fun InsertBodySpl( // Menambahkan tampilan form untuk memasukkan data supplier dan button simpan.
    modifier: Modifier = Modifier,
    onValueChange: (SupplierEvent) -> Unit,
    uiState: SplUIState,
    onClick: () -> Unit
) {
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormSupplier(
            supplierEvent = uiState.supplierEvent,
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
@Composable // untuk menampilkan input form textfield data Supplier
fun FormSupplier(
    supplierEvent: SupplierEvent = SupplierEvent(),
    onValueChange: (SupplierEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    Column() {
        Spacer(Modifier.padding(20.dp))

        // TEXTFIELD NAMA
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = supplierEvent.nama,
            onValueChange = {
                onValueChange(supplierEvent.copy(nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan nama") },
        )
        Text( // validasi error pada text field
            text = errorState.nama ?: "",
            color = Color.Red
        )

        // TEXTFIELD KONTAK
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = supplierEvent.kontak,
            onValueChange = {
                onValueChange(supplierEvent.copy(kontak = it))
            },
            label = { Text("Kontak") },
            isError = errorState.kontak != null,
            placeholder = { Text("Masukkan kontak") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            )
        )
        Text( // validasi error pada text field
            text = errorState.kontak ?: "",
            color = Color.Red
        )

        // TEXTFIELD ALAMAT
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = supplierEvent.alamat,
            onValueChange = {
                onValueChange(supplierEvent.copy(alamat = it))
            },
            label = { Text("Alamat") },
            isError = errorState.alamat != null,
            placeholder = { Text("Masukkan alamat") },
        )
        Text( // validasi error pada text field
            text = errorState.alamat ?: "",
            color = Color.Red
        )
    }
}