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
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CostosTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(ProyectoOrganizador::class.java)

    @Test
    fun costosTest() {
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
        appCompatEditText.perform(replaceText("Nueva Prueba 1"), closeSoftKeyboard())

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

        val materialTextView2 = onView(
            allOf(
                withId(R.id.tvProjectName), withText("Nueva Prueba 1"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerViewProjects),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView2.perform(click())

        val linearLayout = onView(
            allOf(
                withId(R.id.buttonCostos),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        1
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        linearLayout.perform(click())

        val floatingActionButton = onView(
            allOf(
                withId(R.id.newCostoButton),
                childAtPosition(
                    allOf(
                        withId(R.id.DeleteCosto),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.nameCosto),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("Ejemplo"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.montoCosto),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("200"), closeSoftKeyboard())

        val materialButton3 = onView(
            allOf(
                withId(R.id.savecostoBtn), withText("Agregar Proveedor"),
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

        val textView = onView(
            allOf(
                withId(R.id.txtShowTitle), withText("Ejemplo"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Ejemplo")))

        val textView2 = onView(
            allOf(
                withId(R.id.txtShowMonto), withText("200"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("200")))

        val appCompatImageView = onView(
            allOf(
                withId(R.id.ivEditIcon),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.RelativeLayout")),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.newNameCosto),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(replaceText("Ejemplo2"), closeSoftKeyboard())

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.newMontoCosto), withText("200"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(replaceText("210"))

        val appCompatEditText6 = onView(
            allOf(
                withId(R.id.newMontoCosto), withText("210"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText6.perform(closeSoftKeyboard())

        val materialButton4 = onView(
            allOf(
                withId(R.id.newcostoBtn), withText("Modificar Proveedor"),
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
        materialButton4.perform(click())

        val textView3 = onView(
            allOf(
                withId(R.id.txtShowTitle), withText("Ejemplo2"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Ejemplo2")))

        val textView4 = onView(
            allOf(
                withId(R.id.txtShowMonto), withText("210"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("210")))
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
