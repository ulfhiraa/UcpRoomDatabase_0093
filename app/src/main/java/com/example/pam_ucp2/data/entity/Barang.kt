package com.example.pam_ucp2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barang")
data class Barang( //tabel barang
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Auto-increment    val id: Int,
    val nama: String,
    val deskripsi: String,
    val harga: Double,
    val stok: Int,
    val namaSupplier: String
)
