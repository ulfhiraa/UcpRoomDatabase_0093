package com.example.pam_ucp2.data.repository

import com.example.pam_ucp2.data.dao.BarangDao
import com.example.pam_ucp2.data.entity.Barang
import kotlinx.coroutines.flow.Flow

class LocalRepositoryBrg (
    private val barangDao: BarangDao
) : RepositoryBrg {
    // insert
    override suspend fun insertBrg(barang: Barang) {
        barangDao.insertBarang(barang)
    }

    override fun getAllBrg(): Flow<List<Barang>> {
        return barangDao.getAllBarang()
    }

    override fun getBrg(id: String): Flow<Barang> {
        return barangDao.getBarang(id)
    }

    // hapus
    override suspend fun deleteBrg(barang: Barang) {
        barangDao.deleteBarang(barang)
    }

    // update
    override suspend fun updateBrg(barang: Barang) {
        barangDao.updateBarang(barang)
    }
}