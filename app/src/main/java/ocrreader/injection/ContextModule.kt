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
    private var httpServer: HttpGameServer? = null
    private var websocketServer: WebSocketHandler? = null

    @Provides
    @Singleton
    fun provideHolder(): GridItemHolder = GridItemHolder()

    @Provides
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideHttpGameServer(): HttpGameServer {
        // TODO: dagger should manage singleton instantiation
        if (httpServer == null) {
            httpServer = HttpGameServer(app)
        }

        return httpServer!!
    }

    @Provides
    @Singleton
    fun provideWebSocketServer(): WebSocketHandler {
        // TODO: dagger should manage singleton instantiation
        if (websocketServer == null) {
            websocketServer = WebSocketHandler()
        }

        return websocketServer!!
    }

    @Provides
    @Singleton
    fun provideGameServer(): GameServer {
        return GameServer(provideHttpGameServer(), provideWebSocketServer())
    }

    @Provides
    @Singleton
    fun provideConnectionUtils(): ConnectionUtils {
        return ConnectionUtils(app)
    }
}
