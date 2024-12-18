package com.example.pam_ucp2.data.repository

import com.example.pam_ucp2.data.dao.SupplierDao
import com.example.pam_ucp2.data.entity.Supplier
import kotlinx.coroutines.flow.Flow

class LocalRepositorySpl (
    private val supplierDao: SupplierDao
) : RepositorySpl {
    // insert
    override suspend fun insertSpl(supplier: Supplier) {
        supplierDao.insertSupplier(supplier)
    }

    override fun getAllSpl(): Flow<List<Supplier>> {
        return supplierDao.getAllSupplier()
    }

    override fun getSpl(id: String): Flow<Supplier> {
        return supplierDao.getSupplier(id)
    }

    // hapus
    override suspend fun deleteSpl(supplier: Supplier) {
        supplierDao.deleteSupplier(supplier)
    }

    // update
    override suspend fun updateSpl(supplier: Supplier) {
        supplierDao.updateSupplier(supplier)
    }
}