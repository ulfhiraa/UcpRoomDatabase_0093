package com.example.pam_ucp2.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pam_ucp2.ui.view.PilihanHomeView
import com.example.pam_ucp2.ui.view.barang.DestinasiInsertBrg
import com.example.pam_ucp2.ui.view.barang.DetailBrgView
import com.example.pam_ucp2.ui.view.barang.HomeBrgView
import com.example.pam_ucp2.ui.view.barang.InsertBrgView
import com.example.pam_ucp2.ui.view.barang.UpdateBrgView
import com.example.pam_ucp2.ui.view.supplier.DestinasiInsertSpl
import com.example.pam_ucp2.ui.view.supplier.DetailSplView
import com.example.pam_ucp2.ui.view.supplier.HomeSplView
import com.example.pam_ucp2.ui.view.supplier.InsertSplView

@Composable
fun PengelolaHalaman( // untuk mengelola navigasi antar halaman home, barang dan supplier
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost (
        navController = navController,
        startDestination = DestinasiMain.route) {

        composable(
            route = DestinasiMain.route
        ){
            PilihanHomeView(
                onBarangClick = {
                    navController.navigate(DestinasiHomeBrg.route)
                },
                onSupplierClick = {
                   navController.navigate(DestinasiHomeSpl.route)
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
                onBack = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        // Home Supplier
        composable(
            route = DestinasiHomeSpl.route
        ) {
            HomeSplView(
                onDetailClick = {
                },
                onAddSpl = {
                    navController.navigate(DestinasiInsertSpl.route)
                },
                onBack = {
                    navController.popBackStack()
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

        // Detail Supplier
        composable(
            DestinasiDetailSpl.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailSpl.ID) {
                    type = NavType.StringType
                }
            )
        ){
            val id = it.arguments?.getString(DestinasiDetailSpl.ID)
            id?.let { id ->
                DetailSplView(
                    onBack = {
                        navController.popBackStack()
                    },
                    modifier = modifier
                )
            }
        }
    }
}