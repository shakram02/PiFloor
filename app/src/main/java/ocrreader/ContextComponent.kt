package ocrreader

import dagger.Component
import ocrreader.processing.CalibrationModeActivity
import ocrreader.processing.GameModeActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class])
interface ContextComponent {
    fun inject(target: MainActivity)
    fun inject(target: CalibrationModeActivity)
    fun inject(target: GameModeActivity)
}
