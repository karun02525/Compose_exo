package dazn.com.ui.screen.video_list


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class VideoListActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(VideoListActivity::class.java)

    @Test
    fun videoListActivityTest() {
        val frameLayout = onView(
            allOf(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java), isDisplayed())
        )
        frameLayout.check(matches(isDisplayed()))

        val view = onView(
            allOf(
                withParent(withParent(IsInstanceOf.instanceOf(android.view.View::class.java))),
                isDisplayed()
            )
        )
        view.check(matches(isDisplayed()))

        val view2 = onView(
            allOf(
                withParent(withParent(IsInstanceOf.instanceOf(android.view.View::class.java))),
                isDisplayed()
            )
        )
        view2.check(matches(isDisplayed()))

        val frameLayout2 = onView(
            allOf(
                withId(androidx.media3.ui.R.id.exo_ad_overlay),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        frameLayout2.check(matches(isDisplayed()))

        val view3 = onView(
            allOf(
                withParent(
                    allOf(
                        withId(androidx.media3.ui.R.id.exo_content_frame),
                        withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        view3.check(matches(isDisplayed()))

        val view4 = onView(
            allOf(
                withParent(
                    allOf(
                        withId(androidx.media3.ui.R.id.exo_content_frame),
                        withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        view4.check(matches(isDisplayed()))

        val view5 = onView(
            allOf(
                withParent(
                    allOf(
                        withId(androidx.media3.ui.R.id.exo_content_frame),
                        withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        view5.check(matches(isDisplayed()))

        val view6 = onView(
            allOf(
                withParent(
                    allOf(
                        withId(androidx.media3.ui.R.id.exo_content_frame),
                        withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        view6.check(matches(isDisplayed()))
    }
}
