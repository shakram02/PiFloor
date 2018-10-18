package pifloor.injection

import dagger.Component
import pifloor.MainActivity
import pifloor.webserver.ServerFragment
import pifloor.processing.CalibrationModeActivity
import pifloor.processing.CalibrationOverlayFragment
import pifloor.processing.GameModeActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class])
interface ContextComponent {
    fun inject(target: MainActivity)
    fun inject(target: CalibrationModeActivity)
    fun inject(target: GameModeActivity)
    fun inject(target: CalibrationOverlayFragment)
    fun inject(target: ServerFragment)
}
