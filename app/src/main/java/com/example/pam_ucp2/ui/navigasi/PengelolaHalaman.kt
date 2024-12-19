package com.example.pam_ucp2.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pam_ucp2.ui.view.PilihanHomeView
import com.example.pam_ucp2.ui.view.barang.DetailBrgView
import com.example.pam_ucp2.ui.view.barang.HomeBrgView
import com.example.pam_ucp2.ui.view.barang.UpdateBrgView

@Composable
fun PengelolaHalaman( // untuk mengelola navigasi antar halaman home, barang dan supplier
    navController: NavController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = DestinasiMain.route // mulai dari halaman pilihan home
    ) {
        // Halaman Pilihan HOME
        composable(
            route = DestinasiMain.route
        ){
            PilihanHomeView(
                onBarangClick = {
                    navController.navigate(DestinasiHomeBrg.route)
                },
                onSupplierClick = {
                    navController.navigate(DestinasiSplHome.route)
                }
            )
        }

        // Home Barang
        composable(
            route = DestinasiHomeBrg.route
        ) {
            HomeBrgView(
                onDetailClick = {
                },
                onAddBrg = {
                    navController.navigate(DestinasiInsertBrg.route)
                },
                modifier = modifier
            )
        }

        // Home Supplier
        composable(
            route = DestinasiSplHome.route
        ) {
            HomeSplView(
                onDetailClick = {
                },
                onAddSpl = {
                    navController.navigate(DestinasiInsertSpl.route)
                },
                modifier = modifier
            )
        }

        // Insert Barang
        composable(
            route = DestinasiInsertBrg.route
        ) {
            InsertBrgView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }

        // Detail Barang
        composable(
            DestinasiDetailBrg.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailBrg.ID) {
                    type = NavType.StringType
                }
            )
        ){
            val id = it.arguments?.getString(DestinasiDetailBrg.ID)
            id?.let { id ->
                DetailBrgView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateBrg.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        //Update barang
        composable(
            DestinasiUpdateBrg.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateBrg.ID){
                    type = NavType.StringType
                }
            )
        ){
            UpdateBrgView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigasi = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        // Insert Supplier
        composable(
            route = DestinasiInsertSpl.route
        ) {
            InsertSplView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }
    }
}