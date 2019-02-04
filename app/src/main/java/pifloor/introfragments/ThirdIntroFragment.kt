package pifloor.introfragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder
import pifloor.R


class ThirdIntroFragment : Fragment(), ISlideBackgroundColorHolder {

    var layoutContainer: LinearLayout? = null

    override fun getDefaultBackgroundColor(): Int {
        return Color.parseColor(R.color.black.toString())
    }

    override fun setBackgroundColor(backgroundColor: Int) {
        layoutContainer?.setBackgroundColor(backgroundColor)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_third, container, false)
        layoutContainer = view.findViewById(R.id.container) as LinearLayout
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }
}
