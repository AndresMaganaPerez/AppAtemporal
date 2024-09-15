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
class ActividadesTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(ProyectoOrganizador::class.java)

    @Test
    fun actividadesTest() {
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

        val materialTextView2 = onView(
            allOf(
                withId(R.id.tvProjectName), withText("Prueba"),
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
                withId(R.id.bottom_activity),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        linearLayout.perform(click())

        val floatingActionButton = onView(
            allOf(
                withId(R.id.newTaskButton),
                childAtPosition(
                    allOf(
                        withId(R.id.DeleteActivity),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.nameActivity),
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
        appCompatEditText2.perform(replaceText("Nueva actividad"), closeSoftKeyboard())

        val materialButton3 = onView(
            allOf(
                withId(R.id.saveBtn), withText("Agregar Tarea"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    9
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.txtShowTitle), withText("Nueva actividad"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Nueva actividad")))

        val textView2 = onView(
            allOf(
                withId(R.id.txtShowPrioridad), withText("Baja"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Baja")))

        val textView3 = onView(
            allOf(
                withId(R.id.txtShowArea), withText("Negocios"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Negocios")))

        val textView4 = onView(
            allOf(
                withId(R.id.txtShowArea), withText("Negocios"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Negocios")))

        val textView5 = onView(
            allOf(
                withId(R.id.txtShowEstatus), withText("En Proceso"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("En Proceso")))

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

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.nameModifedActivity), withText("Nueva actividad"),
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
        appCompatEditText3.perform(replaceText("Nueva actividad modificada"))

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.nameModifedActivity), withText("Nueva actividad modificada"),
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
        appCompatEditText4.perform(closeSoftKeyboard())

        val materialButton4 = onView(
            allOf(
                withId(R.id.saveModifiedBtn), withText("Modificar Tarea"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    9
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        val textView6 = onView(
            allOf(
                withId(R.id.txtShowTitle), withText("Nueva actividad modificada"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("Nueva actividad modificada")))
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
