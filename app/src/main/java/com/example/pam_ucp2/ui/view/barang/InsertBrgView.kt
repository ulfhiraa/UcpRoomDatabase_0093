package com.example.pam_ucp2.ui.view.barang

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_ucp2.R
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
    override val route: String = "DestinasiInsertBrg"
}

@Composable
fun SectionHeaderInsertBrg(
    onBack: () -> Unit,
    modifier: Modifier
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.LightGray, RoundedCornerShape(bottomEnd = 50.dp))
    ){
        Box(){
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceBetween)
            {
                Icon(
                    Icons.Filled.List,
                    contentDescription = " ",
                    Modifier.padding(end = 1.dp),
                    tint = Color.White
                )
            }
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Spacer(Modifier.padding(20.dp))
                TopAppBar(
                    onBack = onBack,
                    showBackButton = true,
                    judul = "Tambah Barang",
                    modifier = modifier
                )
            }
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.bear),
                    contentDescription = " ",
                    Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(500.dp))
                        .shadow(50.dp, RoundedCornerShape(370.dp))
                )
            }
        }
    }
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
                .padding(16.dp)
        ){
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Supplier",
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

@Composable
fun FormBarang(
    barangEvent: BarangEvent = BarangEvent(),
    onValueChange: (BarangEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
 //   supplierList: List<String> = emptyList(),
    modifier: Modifier = Modifier
) {
//    var expanded by remember { mutableStateOf(false) }
//    var selectedNamaSupplier by remember { mutableStateOf(barangEvent.namaSupplier ?: "") }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // TEXTFIELD NAMA
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.nama,
            onValueChange = {
                onValueChange(barangEvent.copy(nama = it))
            },
            label = { Text("Nama Barang") },
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

        // TEXTFIELD HARGA BARANG
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = if (barangEvent.harga == 0.0) "" else barangEvent.harga.toString(), // Konversi Double ke String
            onValueChange = { inputValue ->
                val newHarga = inputValue.toDoubleOrNull() // Konversi String ke Double
                if (newHarga != null) {
                    onValueChange(barangEvent.copy(harga = newHarga)) // Update harga
                }
            },
            label = { Text("Harga Barang") },
            isError = errorState.harga != null,
            placeholder = { Text("Masukkan harga") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            )
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
            label = { Text("Stok Barang") },
            isError = errorState.stok != null,
            placeholder = { Text("Masukkan stok") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            )
        )
        Text(
            text = errorState.stok ?: "",
            color = Color.Red
        )

        // TEXTFIELD NAMA SUPPLIER
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.namaSupplier,
            onValueChange = {
                onValueChange(barangEvent.copy(namaSupplier = it))
            },
            label = { Text("Nama Supplier") },
            isError = errorState.namaSupplier != null,
            placeholder = { Text("Masukkan nama supplier") },
        )
        Text( // validasi error pada text field
            text = errorState.namaSupplier ?: "",
            color = Color.Red
        )

//        //Input untuk Nama Supplier (Manual atau Dropdown)
//        Box(modifier = Modifier.fillMaxWidth()) {
//            OutlinedTextField(
//                value = selectedNamaSupplier,
//                onValueChange = {
//                    selectedNamaSupplier = it
//                    onValueChange(barangEvent.copy(namaSupplier = it))
//                },
//                modifier = Modifier.fillMaxWidth(),
//                label = { Text(text = "Pilih Nama Supplier") },
//                trailingIcon = {
//                    Icon(
//                        imageVector = Icons.Filled.ArrowDropDown,
//                        contentDescription = null,
//                        modifier = Modifier.clickable { expanded = !expanded }
//                    )
//                }
//            )
//
//            // DropdownMenu
//            DropdownMenu(
//                expanded = expanded,
//                onDismissRequest = { expanded = false },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                supplierList.forEach { namaSpl ->
//                    DropdownMenuItem(
//                        text = { Text(text = namaSpl) },
//                        onClick = {
//                            selectedNamaSupplier = namaSpl
//                            onValueChange(barangEvent.copy(namaSupplier = namaSpl))
//                            expanded = false
//                        }
//                    )
//                }
//            }
//        }

//        // Menampilkan error jika ada
//        if (!errorState.namaSupplier.isNullOrEmpty()) {
//            Text(
//                text = errorState.namaSupplier ?: "",
//                color = Color.Red
//            )
//        }
    }
}