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
class ObjetivosTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(ProyectoOrganizador::class.java)

    @Test
    fun objetivosTest() {
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
        appCompatEditText.perform(replaceText("Nueva Prueba"), closeSoftKeyboard())

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
                withId(R.id.tvProjectName), withText("Nueva Prueba"),
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
                withId(R.id.bottom_objective),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        linearLayout.perform(click())

        val materialTextView3 = onView(
            allOf(
                withId(R.id.tvNewPresupuesto), withText("Presupuesto: 0.0"),
                childAtPosition(
                    allOf(
                        withId(R.id.proyectosOrganizador),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialTextView3.perform(click())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.descPresupuesto),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("100"), closeSoftKeyboard())

        val materialButton3 = onView(
            allOf(
                withId(R.id.saveButton), withText("Modificar"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.design_bottom_sheet),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.tvNewPresupuesto), withText("Presupuesto: 100.0"),
                withParent(
                    allOf(
                        withId(R.id.proyectosOrganizador),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Presupuesto: 100.0")))

        val materialTextView4 = onView(
            allOf(
                withId(R.id.tvDeletePresupuesto), withText("Elimininar"),
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
        materialTextView4.perform(click())

        val materialButton4 = onView(
            allOf(
                withId(android.R.id.button1), withText("Eliminar"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.buttonPanel),
                        0
                    ),
                    3
                )
            )
        )
        materialButton4.perform(scrollTo(), click())

        val textView2 = onView(
            allOf(
                withId(R.id.tvNewPresupuesto), withText("Presupuesto: 0.0"),
                withParent(
                    allOf(
                        withId(R.id.proyectosOrganizador),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Presupuesto: 0.0")))

        val appCompatImageView = onView(
            allOf(
                withId(R.id.ivEditMeta),
                childAtPosition(
                    allOf(
                        withId(R.id.proyectosOrganizador),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    10
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.descMeta),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("1000"), closeSoftKeyboard())

        val materialButton5 = onView(
            allOf(
                withId(R.id.saveButtonMeta), withText("Modificar"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.design_bottom_sheet),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())

        val textView3 = onView(
            allOf(
                withId(R.id.tvMetaName), withText("Meta: 1000.0"),
                withParent(
                    allOf(
                        withId(R.id.proyectosOrganizador),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Meta: 1000.0")))

        val textView4 = onView(
            allOf(
                withId(R.id.MetaValue), withText("1000.0"),
                withParent(
                    allOf(
                        withId(R.id.proyectosOrganizador),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("1000.0")))

        val appCompatImageView2 = onView(
            allOf(
                withId(R.id.deleteMetaButton),
                childAtPosition(
                    allOf(
                        withId(R.id.proyectosOrganizador),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    11
                ),
                isDisplayed()
            )
        )
        appCompatImageView2.perform(click())

        val materialButton6 = onView(
            allOf(
                withId(android.R.id.button1), withText("Eliminar"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.buttonPanel),
                        0
                    ),
                    3
                )
            )
        )
        materialButton6.perform(scrollTo(), click())

        val textView5 = onView(
            allOf(
                withId(R.id.tvMetaName), withText("Meta: 0.0"),
                withParent(
                    allOf(
                        withId(R.id.proyectosOrganizador),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Meta: 0.0")))
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
