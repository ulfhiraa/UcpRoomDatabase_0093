package com.example.pam_ucp2.ui.view.supplier

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
import com.example.pam_ucp2.data.entity.Supplier
import com.example.pam_ucp2.ui.customwidget.TopAppBar
import com.example.pam_ucp2.ui.viewmodel.PenyediaViewModel
import com.example.pam_ucp2.ui.viewmodel.supplier.HomeSplViewModel
import com.example.pam_ucp2.ui.viewmodel.supplier.HomeUiStateSpl
import kotlinx.coroutines.launch

@Composable
fun HomeSplView( // untuk tampilan halaman utama daftar supplier
    viewModel: HomeSplViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onDetailClick: (String) -> Unit = { }, // Fungsi yang dipanggil saat supplier di daftar diklik.
    onBack: () -> Unit,
    modifier: Modifier = Modifier // mengatur layout
){
    Scaffold ( // Agar UI konsisten
        topBar = {
            TopAppBar(
                judul = "Daftar Supplier",
                showBackButton = true,
                onBack = onBack,
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth()
            )
        }
    ){ innerPadding ->
        val homeUiStateSpl by viewModel.homeUiState.collectAsState()

        BodyHomeSplView(
            modifier = Modifier.padding(innerPadding),
            homeUiStateSpl = homeUiStateSpl,
            onClick = {
                onDetailClick(it)
            },
        )
    }
}

@Composable //  untuk menampilkan status UI (loading, error, data kosong, atau daftar supplier dengan tampilan dinamis dan Snackbar.
fun BodyHomeSplView( // untuk menampilkan data
    homeUiStateSpl: HomeUiStateSpl, // berisi status UI. apakah data sedang loading, ada error atau sudah ada data
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier // mengatur properti layout
){
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() } //Snackbar state
    when {
        homeUiStateSpl.isLoading -> {
            // Menampilkan indikator loading
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        homeUiStateSpl.isError -> {
            // menampilkan pesan error
            LaunchedEffect(homeUiStateSpl.errorMessage) {
                homeUiStateSpl.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message) // Tampilkan Snackbar
                    }
                }
            }
        }

        homeUiStateSpl.listSpl.isEmpty() -> {
            // menampilkan pesan jika data kosong
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Tidak ada data Supplier.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            // Menampilkan daftar supplier
            ListSupplier(
                ListSpl = homeUiStateSpl.listSpl,
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
fun ListSupplier( // menampilkan daftar supplier
    ListSpl: List<Supplier>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
) {
    LazyColumn ( // agar kolom dapat digeser (scroll)
        modifier = modifier
    ){
        items(
            items = ListSpl,
            itemContent = { spl ->
                CardSpl(
                    spl = spl,
                    onClick = { onClick(spl.id.toString()) }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardSpl( // untuk menampilkan informasi supplier(nama, kontak, alamat) dengan mengklik card
    spl: Supplier,
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
            // nama supplier
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = spl.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            // id supplier
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = spl.id.toString(), // agar tipe data numerik dapat dikonversi menjadi String
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            // kontak supplier
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = spl.kontak,
                    fontWeight = FontWeight.Bold,
                )
            }

            // Alamat supplier
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = spl.alamat.toString(), // agar tipe data numerik dapat dikonversi menjadi String
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}