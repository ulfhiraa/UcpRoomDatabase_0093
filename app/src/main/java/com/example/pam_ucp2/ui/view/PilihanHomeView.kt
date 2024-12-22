package com.example.pam_ucp2.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pam_ucp2.R

@Composable
fun SectionHeader() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.LightGray,
            RoundedCornerShape(bottomEnd = 50.dp)
        )
    ){
        Box(){
            Column (
                modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ){
                Spacer(Modifier.padding(30.dp))
                Icon(
                    Icons.Filled.List,
                    contentDescription = " ",
                    tint = Color.Black,
                )

                // Header Title
                Spacer(Modifier.padding(8.dp))
                Text(
                    text = "BT21",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    ),
                    fontSize = 30.sp,
                )
                Text(
                    text = "Universtar ",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
                Spacer(Modifier.padding(16.dp))
            }
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
                Spacer(Modifier.padding(20.dp))

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

@Composable
fun PilihanHomeView(
    onBarangClick: () -> Unit,
    onSupplierClick: () -> Unit,
    onAddBrgClick: () -> Unit,
    onAddSplClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White) // Tambahkan warna latar jika diperlukan
    ) {
        // Header Section
        SectionHeader()

        // Spacer untuk memberikan jarak antara header dan card
        Spacer(modifier = Modifier.height(16.dp))

        // Card Section
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pilih Halaman",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(32.dp)
            )

            Spacer(modifier = Modifier.padding(bottom = 20.dp))

            // Baris Pertama: Home Barang & Add Barang
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // HOME BARANG CARD
                OutlinedCard(
                    onClick = onBarangClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Gambar Bear
                        Image(
                            painter = painterResource(id = R.drawable.bear),
                            contentDescription = "Home Barang Icon",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(30.dp)) // Sesuaikan bentuk gambar
                                .shadow(10.dp, RoundedCornerShape(30.dp)) // Tambahkan bayangan
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Teks "Home Barang"
                        Text(
                            text = "Home Barang",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center
                        )
                    }

                }

                // ADD BARANG CARD
                Card(
                    onClick = onAddBrgClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Gambar Bear
                        Image(
                            painter = painterResource(id = R.drawable.bear),
                            contentDescription = "Add Barang Icon",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(30.dp)) // Sesuaikan bentuk gambar
                                .shadow(10.dp, RoundedCornerShape(30.dp)) // Tambahkan bayangan
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Teks "Add Barang"
                        Text(
                            text = "Add Barang",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(bottom = 30.dp))

            // Baris Kedua: Home Supplier & Add Supplier
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // HOME SUPPLIER CARD
                Card(
                    onClick = onSupplierClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Gambar Bear
                        Image(
                            painter = painterResource(id = R.drawable.bear),
                            contentDescription = "Home Supplier Icon",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(30.dp)) // Sesuaikan bentuk gambar
                                .shadow(10.dp, RoundedCornerShape(30.dp)) // Tambahkan bayangan
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Teks "Home Supplier"
                        Text(
                            text = "Home Supplier",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // ADD SUPPLIER CARD
                Card(
                    onClick = onAddSplClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                ) {                    Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Gambar Bear
                    Image(
                        painter = painterResource(id = R.drawable.bear),
                        contentDescription = "Add Supplier Icon",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(30.dp)) // Sesuaikan bentuk gambar
                            .shadow(10.dp, RoundedCornerShape(30.dp)) // Tambahkan bayangan
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Teks "Add Supplier"
                    Text(
                        text = "Add Supplier",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                    )
                }
                }
            }
        }
    }
}
