package com.example.pam_ucp2.ui.navigasi

interface AlamatNavigasi {
    val route: String
}

// main home
object DestinasiMain : AlamatNavigasi {
    override val route = "home-main"
}

// home barang
object DestinasiHomeBrg : AlamatNavigasi {
    override val route = "home-brg"
}

// detail barang
object DestinasiDetailBrg : AlamatNavigasi {
    override val route = "detail-brg"
    const val ID = "id"
    val routesWithArg = "$route/{$ID}"
}

// update barang
object DestinasiUpdateBrg : AlamatNavigasi {
    override val route = "update-brg"
    const val ID = "id"
    val routesWithArg = "$route/{$ID}"
}

// home supplier
object DestinasiHomeSpl : AlamatNavigasi {
    override val route = "home-spl"
}

// detail supplier
object DestinasiDetailSpl : AlamatNavigasi {
    override val route = "detail-spl"
    const val ID = "id"
    val routesWithArg = "$route/{$ID}"
}