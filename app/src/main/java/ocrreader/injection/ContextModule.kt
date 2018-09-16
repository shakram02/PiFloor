package ocrreader.injection

import android.app.Application
import dagger.Module
import dagger.Provides
import ocrreader.utils.VirtualGrid
import ocrreader.webserver.ConnectionUtils
import ocrreader.webserver.GameServer
import ocrreader.webserver.HttpGameServer
import ocrreader.webserver.WebSocketHandler
import javax.inject.Singleton

@Module
class ContextModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideHolder(): VirtualGrid = VirtualGrid()

    @Provides
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideHttpGameServer(app: Application): HttpGameServer {
        return HttpGameServer(app)
    }

    @Provides
    @Singleton
    fun provideWebSocketServer(): WebSocketHandler {
        return WebSocketHandler()
    }

    @Provides
    @Singleton
    fun provideGameServer(gameServer: HttpGameServer, webSocketHandler: WebSocketHandler): GameServer {
        return GameServer(gameServer, webSocketHandler)
    }

    @Provides
    @Singleton
    fun provideConnectionUtils(app: Application): ConnectionUtils {
        return ConnectionUtils(app)
    }
}
