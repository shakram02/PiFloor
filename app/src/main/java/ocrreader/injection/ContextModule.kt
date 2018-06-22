package ocrreader.injection

import android.app.Application
import dagger.Module
import dagger.Provides
import ocrreader.utils.GridItemHolder
import ocrreader.webserver.ConnectionUtils
import ocrreader.webserver.GameServer
import ocrreader.webserver.HttpGameServer
import ocrreader.webserver.WebSocketHandler
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

    @Provides
    @Singleton
    fun provideWebSocketServer(): WebSocketHandler = WebSocketHandler()

    @Provides
    @Singleton
    fun provideGameServer(): GameServer = GameServer(provideHttpGameServer(), provideWebSocketServer())

    @Provides
    @Singleton
    fun provideConnectionUtils(): ConnectionUtils = ConnectionUtils(provideApplication())
}
