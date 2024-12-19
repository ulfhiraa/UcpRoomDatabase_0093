package com.example.pam_ucp2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supplier")
data class Supplier(
    @PrimaryKey
    val id: Int,
    val nama: String,
    val kontak: String,
    val alamat: String
)
