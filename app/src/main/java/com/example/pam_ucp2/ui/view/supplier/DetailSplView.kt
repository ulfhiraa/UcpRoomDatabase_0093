package com.example.pam_ucp2.ui.view.supplier

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

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