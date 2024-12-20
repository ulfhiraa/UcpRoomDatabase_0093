package com.example.pam_ucp2.ui.view.supplier

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pam_ucp2.data.entity.Supplier
import com.example.pam_ucp2.ui.view.barang.ComponentDetailBrg

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
            ComponentDetailBrg(judul = "Nama", isinya = supplier.nama)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailBrg(judul = "Kontak", isinya = supplier.kontak)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailBrg(judul = "Alamat", isinya = supplier.alamat)
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