package com.example.terabitemobile

import android.app.Application
import com.example.terabitemobile.koin.moduloGeral
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/*
É necessário criar uma classe com qualquer nome
que seja subclasse de Application.
Mencionar ela no AndroidManifest.xml
 */
class AplicacaoKoin : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger() // Opcional. Ativa mais logs do Koin no logcat

            androidContext(this@AplicacaoKoin) // Obrigatório. Contexto da aplicação

            // indica qual(is) módulo(s) serão usados
            modules(moduloGeral)
        }
    }
}
