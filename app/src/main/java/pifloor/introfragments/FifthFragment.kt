package pifloor.introfragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder
import pifloor.R




class FifthFragment : Fragment(), ISlideBackgroundColorHolder {

    var layoutContainer : LinearLayout?=null

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
        val view =  inflater.inflate(R.layout.fragment_fifth, container, false)
        layoutContainer = view.findViewById(R.id.container) as LinearLayout
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA),0)
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }
}
