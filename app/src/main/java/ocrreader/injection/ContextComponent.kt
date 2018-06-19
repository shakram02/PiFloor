package ocrreader.injection

import dagger.Component
import ocrreader.MainActivity
import ocrreader.ServerActivity
import ocrreader.processing.CalibrationModeActivity
import ocrreader.processing.GameModeActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class])
interface ContextComponent {
    fun inject(target: MainActivity)
    fun inject(target: CalibrationModeActivity)
    fun inject(target: GameModeActivity)
    fun inject(target: ServerActivity)
}
