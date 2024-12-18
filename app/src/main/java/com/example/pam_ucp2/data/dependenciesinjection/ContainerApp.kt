package com.example.pam_ucp2.data.dependenciesinjection

import android.content.Context
import com.example.pam_ucp2.data.database.TokoDatabase
import com.example.pam_ucp2.data.repository.LocalRepositoryBrg
import com.example.pam_ucp2.data.repository.LocalRepositorySpl
import com.example.pam_ucp2.data.repository.RepositoryBrg
import com.example.pam_ucp2.data.repository.RepositorySpl

// mengimplement repo barang dan supplier
interface InterfaceContainerApp {
    val repositoryBrg: RepositoryBrg
    val repositorySpl: RepositorySpl
}

//  menyediakan instance dari repositoryBrg dan repositorySpl secara lazy.
class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val repositoryBrg: RepositoryBrg by lazy {
        LocalRepositoryBrg(TokoDatabase.getDatabase(context).barangDao())
    }

    override val repositorySpl: RepositorySpl by lazy {
        LocalRepositorySpl(TokoDatabase.getDatabase(context).supplierDao())
    }
}