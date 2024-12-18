package com.example.pam_ucp2.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pam_ucp2.data.entity.Supplier
import kotlinx.coroutines.flow.Flow

interface SupplierDao {

    // Operasi
    @Insert
    suspend fun insertSupplier(
        supplier: Supplier
    )

    @Query("SELECT * FROM supplier ORDER BY nama ASC")
    fun getAllSupplier() : Flow<List<Supplier>>

    @Query("SELECT * FROM supplier WHERE id = :id")
    fun getSupplier (id: String) : Flow<Supplier>
}