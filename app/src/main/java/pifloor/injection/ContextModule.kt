package pifloor.injection

import android.app.Application
import dagger.Module
import dagger.Provides
import pifloor.utils.VirtualGrid
import pifloor.webserver.ConnectionUtils
import pifloor.webserver.GameServer
import pifloor.webserver.HttpGameServer
import pifloor.webserver.WebSocketHandler
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
