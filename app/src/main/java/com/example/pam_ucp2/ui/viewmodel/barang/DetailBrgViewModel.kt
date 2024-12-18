
/*
Data Class untuk menampung data yang akan ditampilkan di UI
* */

// memindahkan data dari entity ke ui
fun Barang.toDetailUiEvent(): BarangEvent {
    return BarangEvent(
        id = id,
        nama = nama,
        deskripsi = deskripsi,
        harga = harga,
        stok = stok,
        namaSupplier =  namaSupplier
    )
}