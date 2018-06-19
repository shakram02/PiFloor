package ocrreader.processing

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import ocrreader.R
import ocrreader.injection.EdGridApplication
import ocrreader.utils.GridItemHolder
import javax.inject.Inject

class CalibrationOverlayFragment : Fragment() {
    @Inject
    lateinit var gridItemHolder: GridItemHolder

    private lateinit var unbinder: Unbinder

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_calibration_overlay, container, false)
        unbinder = ButterKnife.bind(this, fragmentView)


        return fragmentView
    }

    override fun onAttach(context: Context?) {
        (this.activity.application as EdGridApplication).component.inject(this)
        super.onAttach(context)
    }

    @OnClick(R.id.btn_clear_calibration_fragment)
    fun clearCalibration() {
        gridItemHolder.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }
}
