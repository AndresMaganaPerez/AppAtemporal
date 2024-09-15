package com.example.appatemporal.framework.view


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.appatemporal.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ProyectosTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(ProyectoOrganizador::class.java)

    @Test
    fun proyectosTest() {
        val materialTextView = onView(
            allOf(
                withId(R.id.tvNewProject), withText("Nuevo Proyecto"),
                childAtPosition(
                    allOf(
                        withId(R.id.proyectosOrganizador),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.nameCreateNewProject),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("Prueba"), closeSoftKeyboard())

        val textInputEditText = onView(
            allOf(
                withId(R.id.dateEdt),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.DateLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(click())

        val materialButton = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton.perform(scrollTo(), click())

        val materialButton2 = onView(
            allOf(
                withId(R.id.button), withText("SUBMIT"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.tvProjectName), withText("Prueba"),
                withParent(withParent(withId(R.id.recyclerViewProjects))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Prueba")))

        val appCompatImageView = onView(
            allOf(
                withId(R.id.ivEditIcon),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerViewProjects),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.nameModifyProject), withText("Prueba"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("Prueba Modificada"))

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.nameModifyProject), withText("Prueba Modificada"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(closeSoftKeyboard())

        val materialButton3 = onView(
            allOf(
                withId(R.id.modifybutton), withText("Modify"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val textView2 = onView(
            allOf(
                withId(R.id.tvProjectName), withText("Prueba Modificada"),
                withParent(withParent(withId(R.id.recyclerViewProjects))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Prueba Modificada")))

        val appCompatImageView2 = onView(
            allOf(
                withId(R.id.deleteProjectButton),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerViewProjects),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageView2.perform(click())

        val materialButton4 = onView(
            allOf(
                withId(android.R.id.button1), withText("Eliminar"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton4.perform(scrollTo(), click())
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
