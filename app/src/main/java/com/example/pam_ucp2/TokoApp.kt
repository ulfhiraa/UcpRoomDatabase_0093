package com.example.pam_ucp2

import android.app.Application
import com.example.pam_ucp2.data.dependenciesinjection.ContainerApp

class TokoApp : Application()  {
    // Fungsinya untuk menyimpan instance ContainerApp
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()
        //Membuat instance ContainerApp
        containerApp =  ContainerApp(this)
        // instance adalah object yang dibuat dari class
    }
}