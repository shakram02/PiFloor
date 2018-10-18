package ocrreader.injection

import android.app.Application

class PiFloorApplication : Application() {
    lateinit var component: ContextComponent

    override fun onCreate() {
        super.onCreate()
        component =
                DaggerContextComponent.builder()
                        .contextModule(ContextModule(this))
                        .build()
    }
}
