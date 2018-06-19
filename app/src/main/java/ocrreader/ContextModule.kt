package ocrreader

import android.app.Application
import dagger.Module
import dagger.Provides
import ocrreader.webserver.HttpGameServer
import javax.inject.Singleton

@Module
class ContextModule(private val app: Application) {
    @Provides
    @Singleton
    fun provideHolder(): GridItemHolder = GridItemHolder()

    @Provides
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideHttpGameServer(): HttpGameServer = HttpGameServer(app)
}
