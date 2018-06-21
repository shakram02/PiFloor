package ocrreader.injection

import dagger.Component
import ocrreader.MainActivity
import ocrreader.webserver.ServerFragment
import ocrreader.processing.CalibrationModeActivity
import ocrreader.processing.CalibrationOverlayFragment
import ocrreader.processing.GameModeActivity
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
