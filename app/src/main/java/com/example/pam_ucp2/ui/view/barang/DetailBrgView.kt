package com.example.pam_ucp2.ui.view.barang

import DetailBrgViewModel
import DetailUiState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_ucp2.R
import com.example.pam_ucp2.data.entity.Barang
import com.example.pam_ucp2.ui.customwidget.TopAppBar
import com.example.pam_ucp2.ui.viewmodel.PenyediaViewModel
import com.example.pam_ucp2.ui.viewmodel.barang.toBarangEntity

@Composable
fun DetailBrgView(
    viewModel: DetailBrgViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit,
    onEditClick: (Int) -> Unit = {},
    onDeleteClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SectionHeaderDetailBrg(onBack = onBack)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(viewModel.detailUiState.value.detailUiEvent.id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Barang",
                )
            }
        }
    ) { innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        BodyDetailBrg(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState,
            onDeleteClick = {
                viewModel.deleteBrg()
                onDeleteClick()
            }
        )
    }
}

@Composable
fun SectionHeaderDetailBrg(
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
                    judul = " D e t a i l\n\nB a r a n g",
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

                Spacer(Modifier.padding(20.dp))

            }
        }
    }
}

@Composable
fun BodyDetailBrg(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState(),
    onDeleteClick: () -> Unit = {}
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    when {
        detailUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Magenta) // Loading lebih menarik
            }
        }

        detailUiState.isUiEventNotEmpty -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(
                        color = Color(0xFFFFF3E0), // Background pastel
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                ItemDetailBrg(
                    barang = detailUiState.detailUiEvent.toBarangEntity(),
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        deleteConfirmationRequired = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFCDD2), // Tombol merah pastel
                        contentColor = Color.White
                    )
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
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data tidak ditemukan",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
@Composable
fun ItemDetailBrg(
    modifier: Modifier = Modifier,
    barang: Barang
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), // Tambahkan padding luar
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE3F2FD), // Warna biru pastel
            contentColor = Color(0xFF1E88E5)
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            ComponentDetailBrg(
                judul = "Nama",
                isinya = barang.nama,
                icon = Icons.Default.Person // Ikon default untuk nama
            )
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailBrg(
                judul = "Deskripsi",
                isinya = barang.deskripsi,
                icon = Icons.Default.Info // Ikon default untuk deskripsi
            )
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailBrg(
                judul = "Harga",
                isinya = barang.harga,
                icon = Icons.Default.Menu // Ikon default untuk harga
            )
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailBrg(
                judul = "Stok",
                isinya = barang.stok,
                icon = Icons.Default.ShoppingCart // Ikon default untuk stok
            )
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailBrg(
                judul = "Nama Supplier",
                isinya = barang.namaSupplier,
                icon = Icons.Default.Face // Ikon default untuk supplier
            )
        }
    }
}

@Composable
fun ComponentDetailBrg(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: Any,
    icon: ImageVector
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp), // Sedikit padding untuk estetika
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "$judul Icon",
            modifier = Modifier
                .size(32.dp) // Ukuran ikon
                .padding(end = 8.dp),
            tint = Color(0xFF1E88E5) // Warna ikon biru pastel
        )
        Column {
            Text(
                text = "$judul:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                text = isinya.toString(),
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do Nothing */ },
        title = { Text("Hapus Data", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
        text = { Text("Apakah Anda yakin ingin menghapus data ini?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Batal", color = Color.Gray)
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Ya", color = Color.Red)
            }
        }
    )
}
