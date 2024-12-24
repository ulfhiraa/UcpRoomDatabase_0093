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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
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
        .background(color = Color(0xFFc4b5c0),
            RoundedCornerShape(bottomEnd = 50.dp))
    ){
        Box(){
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Spacer(Modifier.padding(20.dp))
                TopAppBar(
                    judul = "  H o m e\n\n B a r a n g",
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
                        .size(80.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .shadow(50.dp, RoundedCornerShape(100.dp))
                )
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
fun CardBrg( // Untuk menampilkan informasi barang
    brg: Barang,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    // Tentukan warna berdasarkan stok barang
    val cardColor = when {
        brg.stok == 0 -> Color(0xffa39d9d) // Warna abu-abu untuk stok habis
        brg.stok in 1..10 -> Color(0xFFb35959) // Warna merah muda untuk stok 1-10
        else -> Color(0xFFC8E6C9) // Warna hijau muda untuk stok lebih dari 10
    }

    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)), // Sudut membulat
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Kolom kiri untuk ID, Stok, nama Supplier
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // ID Barang
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Info, // Ikon untuk ID
                        contentDescription = "ID Barang",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${brg.id}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Stok Barang
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Menu, // Ikon untuk stok
                        contentDescription = "Stok Barang",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${brg.stok}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                // Nama Supplier
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Face, // Ikon untuk supplier
                        contentDescription = "Nama Supplier",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(25.dp) // Ikon besar
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = brg.namaSupplier,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Kolom kanan untuk Nama Barang dan Nama Supplier
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Nama Barang
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = brg.nama,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp, // Ukuran besar untuk nama barang
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "Nama Barang",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(65.dp) // Ikon besar
                )

                // Harga Barang di bawah
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Rp ${brg.harga}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}






