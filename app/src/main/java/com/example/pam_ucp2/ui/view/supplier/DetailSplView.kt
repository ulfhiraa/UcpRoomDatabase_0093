package com.example.pam_ucp2.ui.view.supplier

import DetailUiState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_ucp2.data.entity.Supplier
import com.example.pam_ucp2.ui.customwidget.TopAppBar
import com.example.pam_ucp2.ui.viewmodel.PenyediaViewModel
import com.example.pam_ucp2.ui.viewmodel.supplier.DetailSplViewModel
import com.example.pam_ucp2.ui.viewmodel.supplier.toSupplierEntity

@Composable //untuk menampilkan detail supplier
fun DetailSplView(
    modifier: Modifier = Modifier,
    viewModel: DetailSplViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = { }
) {
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Detail Supplier",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
        }
    ) { innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        BodyDetailSpl(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState
        )
    }
}

@Composable // Untuk tampilan detail supplier dengan kondisi loading, data ditemukan, dan data kosong
fun BodyDetailSpl(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState(),
) {
    when {
        detailUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator() // tampilkan loading
            }
        }

        detailUiState.isUiEventNotEmpty -> {
            val supplier = detailUiState.detailUiEvent.toSupplierEntity()
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailSpl(
                    supplier = supplier,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }

        detailUiState.isUiEventEmpty -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data tidak ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailSpl( // menampilkan informasi detail supplier pada card
    modifier: Modifier = Modifier,
    supplier: Supplier
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
            ComponentDetailSpl(judul = "Nama", isinya = supplier.nama)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailSpl(judul = "Kontak", isinya = supplier.kontak)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailSpl(judul = "Alamat", isinya = supplier.alamat)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun ComponentDetailSpl( // menampilkan detail informasi spl
    modifier: Modifier = Modifier,
    judul: String, // menampilkan judul dari informasi
    isinya: Any // menampilkan isi dari informasi
    // any memungkinkan untuk menerima tipe Int, Double, String.
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
            text = isinya.toString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}