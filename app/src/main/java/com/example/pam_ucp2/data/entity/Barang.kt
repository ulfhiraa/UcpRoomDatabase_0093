package com.example.pam_ucp2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barang")
data class Barang(
    @PrimaryKey
    val id: String,
    val nama: String,
    val deskripsi: String,
    val harga: String,
    val stok: String,
    val namaSupplier: String
)
