package com.example.pam_ucp2.ui.view.barang

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_ucp2.data.entity.Barang
import com.example.pam_ucp2.ui.customwidget.TopAppBar
import com.example.pam_ucp2.ui.viewmodel.PenyediaViewModel
import com.example.pam_ucp2.ui.viewmodel.barang.HomeBrgViewModel
import com.example.pam_ucp2.ui.viewmodel.barang.HomeUiState
import kotlinx.coroutines.launch

@Composable
fun HomeBrgView( // untuk tampilan halaman utama daftar barang
    viewModel: HomeBrgViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddBrg: () -> Unit = { }, // fungsi yang dipanggil saat btn Tambah Barang diklik.
    onDetailClick: (String) -> Unit = { }, // Fungsi yang dipanggil saat Barang di daftar diklik.
    modifier: Modifier = Modifier // mengatur layout
){
    Scaffold ( // Agar UI konsisten
        topBar = {
            TopAppBar(
                judul = "Daftar Barang",
                showBackButton = false,
                onBack = { },
                modifier = Modifier
            )
        },
        floatingActionButton = { // tombol aksi untuk add brg
            FloatingActionButton(
                onClick = onAddBrg,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Barang",
                )
            }
        }
    ){ innerPadding ->
        val homeUiState by viewModel.homeUiState.collectAsState()

        BodyHomeBrgView(
            modifier = Modifier.padding(innerPadding),
            homeUiState = homeUiState,
            onClick = {
                onDetailClick(it)
            },
        )
    }
}

@Composable //  untuk menampilkan status UI (loading, error, data kosong, atau daftar barang dengan dukungan tampilan dinamis dan Snackbar.
fun BodyHomeBrgView( // untuk menampilkan data
    homeUiState: HomeUiState, // berisi status UI. apakah data sedang loading, ada error atau sudah ada data
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier // mengatur properti layout
){
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() } //Snackbar state
    when {
        homeUiState.isLoading -> {
            // Menampilkan indikator loading
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        homeUiState.isError -> {
            // menampilkan pesan error
            LaunchedEffect(homeUiState.errorMessage) {
                homeUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message) // Tampilkan Snackbar
                    }
                }
            }
        }

        homeUiState.listBrg.isEmpty() -> {
            // menampilkan pesan jika data kosong
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Tidak ada data Barang.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            // Menampilkan daftar barang
            ListBarang(
                ListBrg = homeUiState.listBrg,
                onClick = {
                    onClick(it)
                    println(
                        it
                    )
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListBarang( // menampilkan daftar barang
    ListBrg: List<Barang>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
) {
    LazyColumn ( // agar kolom dapat digeser (scroll)
        modifier = modifier
    ){
        items(
            items = ListBrg,
            itemContent = { brg ->
                CardBrg(
                    brg = brg,
                    onClick = { onClick(brg.id) }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardBrg( // untuk menampilkan informasi barang(nama, deskripsi, harga, stok, namaSupplier ) dengan mengklik card
    brg: Barang,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            // nama barang
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = brg.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            // id barang
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = brg.id.toString(), // agar tipe data numerik dapat dikonversi menjadi String
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            // deskripsi barang
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = brg.deskripsi,
                    fontWeight = FontWeight.Bold,
                )
            }

            // harga barang
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = brg.harga.toString(), // agar tipe data numerik dapat dikonversi menjadi String
                    fontWeight = FontWeight.Bold,
                )
            }

            // stok barang
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = brg.stok.toString(), // agar tipe data numerik dapat dikonversi menjadi String
                    fontWeight = FontWeight.Bold,
                )
            }

        }
    }
}