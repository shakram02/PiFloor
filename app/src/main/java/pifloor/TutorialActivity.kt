package pifloor

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class TutorialActivity : AppCompatActivity() {

    var steps: Array <TutorialStep> = arrayOf(TutorialStep.STEP1, TutorialStep.STEP2, TutorialStep.STEP3, TutorialStep.STEP4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pager = findViewById<ViewPager>(R.id.tutorial_step_pager) as ViewPager
        pager.adapter = StepAdapter(steps, this)
        val tabLayout = findViewById<TabLayout>(R.id.step_indicator) as TabLayout
        tabLayout.setupWithViewPager(pager, true)
    }

    class StepAdapter : PagerAdapter {
        private var steps: Array<TutorialStep>
        private var mContext: Context

        constructor(tutorialSteps: Array<TutorialStep>, context: Context) {
            steps = tutorialSteps
            mContext = context
        }

        override fun isViewFromObject(view: View, o: Any): Boolean {
            return o == view
        }

        override fun getCount(): Int {
            return steps.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var inflater = LayoutInflater.from(mContext)
            var stepLayout = inflater.inflate(R.layout.tutorial_step, container, false) as ViewGroup
            var stepTitleTV = stepLayout.findViewById<TextView>(R.id.step_title)
            stepTitleTV.text = mContext.getString(steps.get(position).getTitleResId())
            var stepDescTV = stepLayout.findViewById<TextView>(R.id.step_description)
            stepDescTV.text = mContext.getString(steps.get(position).getDescriptionResId())
            container.addView(stepLayout)
            return stepLayout
        }

        override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
            container.removeView(view as View)
        }

    }

    enum class TutorialStep(private var titleResId: Int, private var descriptionResId: Int) {
        STEP1(R.string.tutorial_title1, R.string.tutorial_message1),
        STEP2(R.string.tutorial_title2, R.string.tutorial_message2),
        STEP3(R.string.tutorial_title3, R.string.tutorial_message3),
        STEP4(R.string.tutorial_title4, R.string.tutorial_message4);

        public fun getTitleResId(): Int {
            return titleResId
        }
        public fun getDescriptionResId(): Int {
            return descriptionResId
        }
    }
}
