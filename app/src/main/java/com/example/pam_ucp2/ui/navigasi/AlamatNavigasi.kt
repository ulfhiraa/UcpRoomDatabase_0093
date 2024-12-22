package com.example.pam_ucp2.ui.navigasi

//navigasi untuk Home, Barang, dan Supplier, termasuk detail dan update dengan ID
interface AlamatNavigasi {
    val route: String
}

// main home
object DestinasiMain : AlamatNavigasi {
    override val route = "DestinasiMain"
}

// home barang
object DestinasiHomeBrg : AlamatNavigasi {
    override val route = "DestinasiHomeBrg"
}

// detail barang
object DestinasiDetailBrg : AlamatNavigasi {
    override val route = "DestinasiDetailBrg"
    const val IDbrg = "idBrg"
    val routesWithArg = "$route/{$IDbrg}"
}

// update barang
object DestinasiUpdateBrg : AlamatNavigasi {
    override val route = "DestinasiUpdateBrg"
    const val IDbrg = "idBrg"
    val routesWithArg = "$route/{$IDbrg}"
}

// home supplier
object DestinasiHomeSpl : AlamatNavigasi {
    override val route = "DestinasiHomeSpl"
}