package com.example.pam_ucp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pam_ucp2.data.dao.BarangDao
import com.example.pam_ucp2.data.dao.SupplierDao
import com.example.pam_ucp2.data.entity.Barang
import com.example.pam_ucp2.data.entity.Supplier

// mendefinisikan database dengan tabel Barang dan Supplier
@Database(entities = [Barang::class, Supplier::class], version = 2, exportSchema = false)
abstract class TokoDatabase : RoomDatabase(){

    // Mendefinisikan fungsi untuk mengakses data Barang
    abstract fun barangDao(): BarangDao

    // Mendefinisikan fungsi untuk mengakses data Supplier
    abstract fun supplierDao(): SupplierDao

    //bisa mengakses properti dan fungsi dalam companion object langsung dari kelasnya, tanpa membuat instance.
    companion object{
        @Volatile //Memastikan bahwa nilai variabel Instance selalu sama di semua thread
        private var Instance: TokoDatabase? = null

        fun getDatabase(context: Context): TokoDatabase {
            return (Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    TokoDatabase::class.java, //Class Database
                    "TokoDatabase" //Nama Database
                )
                    .build().also { Instance = it }
            })
        }
    }
}