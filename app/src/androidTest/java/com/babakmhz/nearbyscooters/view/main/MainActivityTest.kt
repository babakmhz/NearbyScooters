package com.babakmhz.nearbyscooters.view.main

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.babakmhz.nearbyscooters.R
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest{


    @Test
    fun testStartingMainActivityShouldStartMapsFragment(){
        ActivityScenario.launch(MainActivity::class.java)
        Espresso.onView(withId(R.id.maps_root))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}