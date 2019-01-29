package pifloor

import android.os.Bundle
import android.support.v4.app.Fragment
import com.github.paolorotolo.appintro.AppIntro
import pifloor.introfragments.*

class IntroActivity : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addSlide(ZeroFragment())
        addSlide(FirstFragment())
        addSlide(SecondFragment())
        addSlide(ThirdFragment())
        addSlide(FourthFragment())
        addSlide(FifthFragment())
        addSlide(SixthFragment())
        showSkipButton(false)
        setDepthAnimation()
        //askForPermissions(arrayOf(Manifest.permission.CAMERA), 1)
        setSwipeLock(false)
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        finish()
    }
}
