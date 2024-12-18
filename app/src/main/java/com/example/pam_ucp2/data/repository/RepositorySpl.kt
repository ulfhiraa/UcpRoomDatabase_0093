package com.example.pam_ucp2.data.repository

import com.example.pam_ucp2.data.entity.Supplier
import kotlinx.coroutines.flow.Flow

interface RepositorySpl {
    suspend fun insertSpl(supplier: Supplier)

    fun getAllSpl(): Flow<List<Supplier>>

    fun getSpl(id: String) : Flow<Supplier>

    suspend fun deleteSpl(supplier: Supplier)

    suspend fun updateSpl(supplier: Supplier)
}