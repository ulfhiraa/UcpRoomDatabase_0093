package com.example.pam_ucp2.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pam_ucp2.R

@Composable
fun SectionHeader() {
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
                Text(
                    text = "M A I N  P A G E",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 25.sp,
                    color = Color.Black
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

@Composable // untuk button navigasi ke halaman barang dan supplier
fun PilihanHomeView(
    onBarangClick: () -> Unit,
    onSupplierClick: () -> Unit,
    onAddBrgClick: () -> Unit,
    onAddSplClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pilih Halaman",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Tombol untuk Home Barang
            androidx.compose.material3.Button(
                onClick = onBarangClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(text = "Home Barang")
            }

            // Tombol untuk Home Supplier
            androidx.compose.material3.Button(
                onClick = onSupplierClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(text = "Home Supplier")
            }

            // Tombol untuk Add Barang
            androidx.compose.material3.Button(
                onClick = onAddBrgClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(text = "Add Barang")
            }

            // Tombol untuk Add Supplier
            androidx.compose.material3.Button(
                onClick = onAddSplClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add Supplier")
            }
        }
    }
}
