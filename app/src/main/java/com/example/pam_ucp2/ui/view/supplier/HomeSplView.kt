package com.example.pam_ucp2.ui.view.supplier

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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import com.example.pam_ucp2.data.entity.Supplier
import com.example.pam_ucp2.ui.customwidget.TopAppBar
import com.example.pam_ucp2.ui.viewmodel.PenyediaViewModel
import com.example.pam_ucp2.ui.viewmodel.supplier.HomeSplViewModel
import com.example.pam_ucp2.ui.viewmodel.supplier.HomeUiStateSpl
import kotlinx.coroutines.launch

@Composable
fun HomeSplView( // Fungsi utama untuk menampilkan halaman daftar supplier dengan header
    viewModel: HomeSplViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddSpl: () -> Unit = { },
    onDetailClick: (String) -> Unit = { },
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxSize() // Mengatur tata letak agar mengisi seluruh layar
    ) {
        // Bagian Header
        SectionHeaderHomeSpl(
            onBack = onBack, // Tambahkan callback untuk tombol kembali jika diperlukan
            //    modifier = Modifier.fillMaxWidth()
        )

        // Bagian Daftar Supplier
        Box(
            modifier = Modifier
        ) {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = onAddSpl,
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Tambah Supplier"
                        )
                    }
                }
            ) { innerPadding ->
                val homeUiStateSpl  by viewModel.homeUiState.collectAsState()

                BodyHomeSplView(
                    modifier = Modifier.padding(innerPadding),
                    homeUiStateSpl = homeUiStateSpl,
                    onClick = { onDetailClick(it) }
                )
            }
        }
    }
}

@Composable
fun SectionHeaderHomeSpl(
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
                    judul = "    H o m e\n\n S u p p l i e r",
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
                        .clip(RoundedCornerShape(500.dp))
                        .shadow(50.dp, RoundedCornerShape(370.dp))
                )
            }
        }
    }
}

@Composable //  untuk menampilkan status UI (loading, error, data kosong, atau daftar supplier dengan dukungan tampilan dinamis dan Snackbar.
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
                    text = "Tidak Ada Data Supplier.",
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
fun CardSpl( // Menampilkan informasi supplier (nama, kontak, alamat) dengan mengklik card
    spl: Supplier,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp) // Padding yang lebih besar untuk ruang kosong
            .clip(RoundedCornerShape(12.dp)) // Membuat sudut card lebih bulat
            .shadow(4.dp, RoundedCornerShape(12.dp)), // Menambahkan bayangan pada card
        colors = CardDefaults.cardColors(
            containerColor = Color(0x9e6f8a) // Warna latar belakang card yang lembut
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp), // Menambahkan padding dalam card
            verticalArrangement = Arrangement.spacedBy(12.dp) // Menambahkan jarak antar elemen
        ) {
            // Nama supplier
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Person, contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary, // Menggunakan warna utama untuk ikon
                    modifier = Modifier.size(28.dp) // Menambahkan ukuran ikon
                )
                Spacer(modifier = Modifier.width(12.dp)) // Menambahkan jarak antar ikon dan teks
                Text(
                    text = spl.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface // Menggunakan warna teks yang kontras
                )
            }

            // ID supplier
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange, contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "ID: ${spl.id}",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Kontak supplier
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Phone, contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = spl.kontak,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Alamat supplier
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn, contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = spl.alamat.toString(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
