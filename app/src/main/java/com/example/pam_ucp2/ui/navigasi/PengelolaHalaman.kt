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
import com.example.pam_ucp2.ui.view.SectionHeader
import com.example.pam_ucp2.ui.view.barang.DestinasiInsertBrg
import com.example.pam_ucp2.ui.view.barang.DetailBrgView
import com.example.pam_ucp2.ui.view.barang.HomeBrgView
import com.example.pam_ucp2.ui.view.barang.InsertBrgView
import com.example.pam_ucp2.ui.view.barang.SectionHeaderDetailBrg
import com.example.pam_ucp2.ui.view.barang.SectionHeaderHomeBrg
import com.example.pam_ucp2.ui.view.barang.SectionHeaderInsertBrg
import com.example.pam_ucp2.ui.view.barang.SectionHeaderUpdateBrg
import com.example.pam_ucp2.ui.view.barang.UpdateBrgView
import com.example.pam_ucp2.ui.view.supplier.DestinasiInsertSpl
import com.example.pam_ucp2.ui.view.supplier.DetailSplView
import com.example.pam_ucp2.ui.view.supplier.HomeSplView
import com.example.pam_ucp2.ui.view.supplier.InsertSplView
import com.example.pam_ucp2.ui.view.supplier.SectionHeaderDetailSpl
import com.example.pam_ucp2.ui.view.supplier.SectionHeaderHomeSpl
import com.example.pam_ucp2.ui.view.supplier.SectionHeaderInsertSpl

@Composable
fun PengelolaHalaman( // untuk mengelola navigasi antar halaman home, barang dan supplier
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost (
        navController = navController,
        startDestination = DestinasiMain.route) {

        // MAIN PAGE
        composable(
            route = DestinasiMain.route
        ){
            PilihanHomeView(
                onBarangClick = {
                    navController.navigate(DestinasiHomeBrg.route)
                },
                onSupplierClick = {
                   navController.navigate(DestinasiHomeSpl.route)
                },
                onAddBrgClick = {
                    navController.navigate(DestinasiInsertBrg.route)
                },
                onAddSplClick = {
                    navController.navigate(DestinasiInsertSpl.route)
                }
            )
            SectionHeader()
        }

        // Home Barang
        composable(
            route = DestinasiHomeBrg.route
        ) {
            HomeBrgView(
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailBrg.route}/$id")
                },
                onAddBrg = {
                    navController.navigate(DestinasiInsertBrg.route)
                },
                onBack = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
           SectionHeaderHomeBrg(
               onBack = {
                    navController.popBackStack()
               }
           )
        }

        // Home Supplier
        composable(
            route = DestinasiHomeSpl.route
        ) {
            HomeSplView(
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailSpl.route}/$id")
                },
                onAddSpl = {
                    navController.navigate(DestinasiInsertSpl.route)
                },
                onBack = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
            SectionHeaderHomeSpl(
                onBack = {
                    navController.popBackStack()
                }
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
            SectionHeaderInsertBrg(
                onBack = {
                    navController.popBackStack()
                },
                modifier = modifier
                )
        }

        // Detail Barang
        composable(
            DestinasiDetailBrg.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailBrg.IDbrg) {
                    type = NavType.StringType
                }
            )
        ){
            val id = it.arguments?.getString(DestinasiDetailBrg.IDbrg)
            id?.let { id ->
                DetailBrgView(
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateBrg.route}/$it")
                    },
                    onDeleteClick = {
                        navController.popBackStack()
                    },
                    onBack = {
                        navController.popBackStack()
                    },
                    modifier = modifier
                )
                SectionHeaderDetailBrg(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

        //Update barang
        composable(
            DestinasiUpdateBrg.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateBrg.IDbrg){
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
            SectionHeaderUpdateBrg(
                onBack = {
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
            SectionHeaderInsertSpl(
                onBack = {
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
                SectionHeaderDetailSpl(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}