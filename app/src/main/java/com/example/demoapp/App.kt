package com.example.demoapp

import android.app.Application
import android.content.Context
import android.util.TypedValue
import java.io.File

fun Context.dipToPixels(dipValue: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, resources.displayMetrics)

class App: Application()  {
    companion object {
        //FIXME: not ideal, but good enough for now
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    private fun unpack(asset: String, toFolder: File) {
        var file = File(toFolder, asset)
        if (!file.exists()) {
            applicationContext.assets.open(asset).use { input ->
                toFolder.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
    }
}