package com.example.movieproject.presentation.view

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.movieproject.R
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityUITest {
//    private lateinit var activityScenario: ActivityScenario
//    var mActivityTestRule = ActivityTestRule<LoginActivity>(LoginActivity::class.java)

    private var lActivityScenario = ActivityScenario.launch(LoginActivity::class.java)

    @Before
    fun setUp() {
        lActivityScenario.moveToState(lActivityScenario.state)
    }

    @After
    fun tearDown() {
        lActivityScenario.moveToState(Lifecycle.State.DESTROYED)
    }


    @Test
    fun test_isActivityInitView(){

        onView(withId(R.id.login_form_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.et_email)).check(matches(isDisplayed()))
        onView(withId(R.id.et_password)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()))
    }
}