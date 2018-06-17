package ocrreader

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val app: Application) {
    @Provides
    @Singleton
    fun provideHolder(): GridItemHolder = GridItemHolder()
}