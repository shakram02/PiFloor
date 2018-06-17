package ocrreader

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class])
interface ContextComponent {
    fun inject(target: MainActivity)
}