package ocrreader

import android.app.Application

class EdGridApplication : Application() {
    lateinit var component: ContextComponent

    override fun onCreate() {
        super.onCreate()
        component =
                DaggerContextComponent.builder()
                        .contextModule(ContextModule(this))
                        .build()
    }
}
