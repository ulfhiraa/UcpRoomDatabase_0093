package com.example.pam_ucp2.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pam_ucp2.data.entity.Barang
import com.example.pam_ucp2.data.entity.Supplier

// mendefinisikan database dengan tabel Barang dan Supplier
@Database(entities = [Barang::class, Supplier::class], version = 2, exportSchema = false)
abstract class TokoDatabase : RoomDatabase(){

}