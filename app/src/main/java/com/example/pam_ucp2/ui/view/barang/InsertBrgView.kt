package com.example.pam_ucp2.ui.view.barang

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pam_ucp2.ui.viewmodel.barang.BarangEvent
import com.example.pam_ucp2.ui.viewmodel.barang.BrgUIState
import com.example.pam_ucp2.ui.viewmodel.barang.FormErrorState


@Composable
fun InsertBodyBrg( // Menambahkan tampilan form untuk memasukkan data barang dan button simpan.
    modifier: Modifier = Modifier,
    onValueChange: (BarangEvent) -> Unit,
    uiState: BrgUIState,
    onClick: () -> Unit
) {
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormBarang(
            barangEvent = uiState.barangEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormBarang(
    barangEvent: BarangEvent = BarangEvent(),
    onValueChange: (BarangEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
){
    Column() {
        // TEXTFIELD NAMA
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.nama,
            onValueChange = {
                onValueChange(barangEvent.copy(nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan nama") },
        )
        Text( // validasi error pada text field
            text = errorState.nama ?: "",
            color = Color.Red
        )

        // TEXTFIELD ID
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.id,
            onValueChange = {
                onValueChange(barangEvent.copy(id = it))
            },
            label = { Text("ID") },
            isError = errorState.id != null,
            placeholder = { Text("Masukkan ID") },
        )
        Text( // validasi error pada text field
            text = errorState.id ?: "",
            color = Color.Red
        )

        // TEXTFIELD DESKRIPSI
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.deskripsi,
            onValueChange = {
                onValueChange(barangEvent.copy(deskripsi = it))
            },
            label = { Text("Deskripsi") },
            isError = errorState.deskripsi != null,
            placeholder = { Text("Masukkan deskripsi") },
        )
        Text( // validasi error pada text field
            text = errorState.deskripsi ?: "",
            color = Color.Red
        )

        // TEXTFIELD HARGA
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.harga,
            onValueChange = {
                onValueChange(barangEvent.copy(harga = it))
            },
            label = { Text("Harga") },
            isError = errorState.harga != null,
            placeholder = { Text("Masukkan harga") },
        )
        Text( // validasi error pada text field
            text = errorState.harga ?: "",
            color = Color.Red
        )


        // TEXTFIELD STOK
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.stok,
            onValueChange = {
                onValueChange(barangEvent.copy(stok = it))
            },
            label = { Text("Stok") },
            isError = errorState.stok != null,
            placeholder = { Text("Masukkan stok") },
        )
        Text( // validasi error pada text field
            text = errorState.stok ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Supplier")

        // DROPDOWN NAMA SUPPLIER
        // Menambahkan form input barang dengan validasi dan dropdown supplier
//        Row (
//            modifier = modifier.fillMaxWidth()
//        ){
//            namaSupplier.forEach{ namaSupplier ->
//                Row (
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Start
//                ){
//                    RadioButton(
//                        selected = barangEvent.namaSupplier == namaSupplier,
//                        onClick = {
//                            onValueChange(barangEvent.copy(namaSupplier = namaSupplier))
//                        },
//                    )
//                    Text(
//                        text = namaSupplier,
//                    )
//                }
//            }
//        }
        Text(
            text = errorState.namaSupplier ?: "",
            color = Color.Red
        )
    }
}