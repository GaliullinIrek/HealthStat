package com.example.snoozestats

import android.app.Application
import com.example.snoozestats.data.MainDatabase

class App: Application() {
    val database by lazy  {
        MainDatabase.createDatabase(this)
    }
}