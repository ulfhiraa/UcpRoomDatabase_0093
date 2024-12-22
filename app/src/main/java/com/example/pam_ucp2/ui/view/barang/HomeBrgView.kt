package com.example.pam_ucp2.ui.view.barang

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_ucp2.R
import com.example.pam_ucp2.data.entity.Barang
import com.example.pam_ucp2.ui.customwidget.TopAppBar
import com.example.pam_ucp2.ui.viewmodel.PenyediaViewModel
import com.example.pam_ucp2.ui.viewmodel.barang.HomeBrgViewModel
import com.example.pam_ucp2.ui.viewmodel.barang.HomeUiState
import kotlinx.coroutines.launch

@Composable
fun HomeBrgView( // Fungsi utama untuk menampilkan halaman daftar barang dengan header
    viewModel: HomeBrgViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddBrg: () -> Unit = { },
    onDetailClick: (String) -> Unit = { },
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxSize() // Mengatur tata letak agar mengisi seluruh layar
    ) {
        // Bagian Header
        SectionHeaderHomeBrg(
            onBack = onBack,
        )

        // Bagian Daftar Barang
        Box(
            modifier = Modifier
        ) {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = onAddBrg,
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Tambah Barang"
                        )
                    }
                }
            ) { innerPadding ->
                val homeUiState by viewModel.homeUiState.collectAsState()

                BodyHomeBrgView(
                    modifier = Modifier.padding(innerPadding),
                    homeUiState = homeUiState,
                    onClick = { onDetailClick(it) }
                )
            }
        }
    }
}

@Composable
fun SectionHeaderHomeBrg(
    onBack: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.LightGray,
            RoundedCornerShape(bottomEnd = 50.dp))
    ){
        Box(){
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Spacer(Modifier.padding(20.dp))
                TopAppBar(
                    judul = "   H o m e\n\n  B a r a n g",
                    showBackButton = true,
                    onBack = onBack,
                    modifier = Modifier
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center) {

                Spacer(Modifier.padding(20.dp))

                Image(
                    painter = painterResource(id = R.drawable.bear),
                    contentDescription = " ",
                    Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(500.dp))
                        .shadow(50.dp, RoundedCornerShape(370.dp))
                )

                Spacer(Modifier.padding(20.dp))

            }
        }
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
                    text = "Tidak Ada Data Barang.",
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
                    onClick = { onClick(brg.id.toString()) }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardBrg( // untuk menampilkan informasi barang (nama, deskripsi, harga, stok, namaSupplier) dengan mengklik card
    brg: Barang,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    // Tentukan warna berdasarkan stok barang
    val cardColor = when {
        brg.stok == 0 -> Color.Gray // Warna abu-abu untuk stok 0
        brg.stok in 1..10 -> Color.Red // Merah untuk stok 1-10
        else -> Color.Green // Hijau jika stok lebih dari 10
    }

    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor // Set warna berdasarkan kondisi stok
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            // Row pertama untuk menampilkan id barang dan nama barang
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), // Padding untuk jarak antar elemen
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon Keranjang yang lebih besar
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "Keranjang",
                    modifier = Modifier.size(50.dp) // Ukuran ikon lebih besar
                )
                Spacer(modifier = Modifier.width(8.dp)) // Jarak antara ikon dan teks
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = brg.nama,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Row kedua untuk menampilkan jumlah stok dan nama supplier
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Stok barang
                Column {
                    Text(
                        text = "Stok: ${brg.stok}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                // Nama Supplier
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = brg.namaSupplier, // Tampilkan nama supplier
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }

            // Row ketiga untuk menampilkan harga barang
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End, // Menempatkan harga di kanan
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Harga barang
                Text(
                    text = "Harga: Rp ${brg.harga}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}


