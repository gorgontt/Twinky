package com.example.twinky


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val circleImageView = onView(
            allOf(
                withId(R.id.addImage_signUp),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        circleImageView.perform(click())

        val circleImageView2 = onView(
            allOf(
                withId(R.id.addImage_signUp),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        circleImageView2.perform(click())

        val textInputEditText = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.editT_name),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(click())

        val textInputEditText2 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.editT_name),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(click())

        val textInputEditText3 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.editT_name),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("all"), closeSoftKeyboard())

        val textInputEditText4 = onView(
            allOf(
                withText("all"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.editT_name),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(click())

        pressBack()

        val textInputEditText5 = onView(
            allOf(
                withText("all"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.editT_name),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(click())

        val textInputEditText6 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.edT_email),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(replaceText("a@gmail.com"), closeSoftKeyboard())

        pressBack()

        val textInputEditText7 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.editT_password),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText7.perform(replaceText("12"), closeSoftKeyboard())

        pressBack()

        val textInputEditText8 = onView(
            allOf(
                withText("12"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.editT_password),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText8.perform(replaceText("123456"))

        val textInputEditText9 = onView(
            allOf(
                withText("123456"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.editT_password),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText9.perform(closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.sign_up_btn), withText("Register"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
