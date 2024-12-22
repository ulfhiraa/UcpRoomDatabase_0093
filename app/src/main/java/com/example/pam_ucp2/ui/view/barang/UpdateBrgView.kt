package com.example.pam_ucp2.ui.view.barang

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.pam_ucp2.ui.customwidget.TopAppBar
import com.example.pam_ucp2.ui.viewmodel.PenyediaViewModel
import com.example.pam_ucp2.ui.viewmodel.barang.UpdateBrgViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SectionHeaderUpdateBrg(
    onBack: () -> Unit,
    modifier: Modifier
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.LightGray, RoundedCornerShape(bottomEnd = 50.dp))
    ){
        Box(){
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Spacer(Modifier.padding(15.dp))
                TopAppBar(
                    onBack = onBack,
                    showBackButton = true,
                    judul = "U p d a t e\n\nB a r a n g",
                    modifier = modifier
                )
            }
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
                Spacer(Modifier.padding(15.dp))

                Image(
                    painter = painterResource(id = R.drawable.bear),
                    contentDescription = " ",
                    Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(500.dp))
                        .shadow(50.dp, RoundedCornerShape(370.dp))
                )
            }
            Spacer(Modifier.padding(bottom = 30.dp))
        }
    }
}

@Composable
fun UpdateBrgView( // untuk memperbarui informasi barang
    onBack: () -> Unit,
    onNavigasi: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateBrgViewModel = viewModel(factory = PenyediaViewModel.Factory) // inisialisasi ViewModel
){
    val uiState = viewModel.updateUIState // ambil UI state dari ViewModel
    val snackbarHostState = remember { SnackbarHostState() } // Snackbar state
    val coroutineScope = rememberCoroutineScope()

    // observasi perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        println("LaunchedEffect triggered")
        uiState.snackBarMessage?.let { message ->
            println("Snackbar message received: $message")
            coroutineScope.launch {
                println("Launching Coroutine for snackbar")
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold (
        modifier = Modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, // Tempatkan snackbar di scaffold
    ){ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Update Barang",
                modifier = modifier
            )

            // isi body
            InsertBodyBrg(
                uiState = uiState,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent) // update state di ViewModel
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()) {
                            viewModel.updateData()
                            delay(600)
                            withContext(Dispatchers.Main){
                                onNavigasi() // navigasi di main thread
                            }
                        }
                    }
                }
            )
        }
    }
}