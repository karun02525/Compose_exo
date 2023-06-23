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
class VideoListActivityTest2 {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(VideoListActivity::class.java)

    @Test
    fun videoListActivityTest2() {
        val frameLayout = onView(
            allOf(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java), isDisplayed())
        )
        frameLayout.check(matches(isDisplayed()))

        val composeView = onView(
            allOf(
                withParent(
                    allOf(
                        withId(android.R.id.content),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        composeView.check(matches(isDisplayed()))

        val composeView2 = onView(
            allOf(
                withParent(
                    allOf(
                        withId(android.R.id.content),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        composeView2.check(matches(isDisplayed()))

        val composeView3 = onView(
            allOf(
                withParent(
                    allOf(
                        withId(android.R.id.content),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        composeView3.check(matches(isDisplayed()))

        val linearLayout = onView(
            allOf(
                withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java)),
                isDisplayed()
            )
        )
        linearLayout.check(matches(isDisplayed()))

        val composeView4 = onView(
            allOf(
                withParent(
                    allOf(
                        withId(android.R.id.content),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        composeView4.check(matches(isDisplayed()))

        val frameLayout2 = onView(
            allOf(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java), isDisplayed())
        )
        frameLayout2.check(matches(isDisplayed()))

        val view = onView(
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
        view.check(matches(isDisplayed()))

        val view2 = onView(
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
        view2.check(matches(isDisplayed()))

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
                withContentDescription("Forward 10 seconds"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.View::class.java))),
                isDisplayed()
            )
        )
        view4.check(matches(isDisplayed()))

        val view5 = onView(
            allOf(
                withContentDescription("Forward 10 seconds"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.View::class.java))),
                isDisplayed()
            )
        )
        view5.check(matches(isDisplayed()))

        val view6 = onView(
            allOf(
                withContentDescription("Forward 10 seconds"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.View::class.java))),
                isDisplayed()
            )
        )
        view6.check(matches(isDisplayed()))

        val view7 = onView(
            allOf(
                withContentDescription("Replay 5 seconds"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.View::class.java))),
                isDisplayed()
            )
        )
        view7.check(matches(isDisplayed()))

        val view8 = onView(
            allOf(
                withContentDescription("Replay 5 seconds"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.View::class.java))),
                isDisplayed()
            )
        )
        view8.check(matches(isDisplayed()))

        val button = onView(
            allOf(
                withParent(withParent(IsInstanceOf.instanceOf(android.view.View::class.java))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val button2 = onView(
            allOf(
                withParent(withParent(IsInstanceOf.instanceOf(android.view.View::class.java))),
                isDisplayed()
            )
        )
        button2.check(matches(isDisplayed()))

        val button3 = onView(
            allOf(
                withParent(withParent(IsInstanceOf.instanceOf(android.view.View::class.java))),
                isDisplayed()
            )
        )
        button3.check(matches(isDisplayed()))
    }
}
