package com.sample.groovytdd

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import com.sample.groovytdd.di.idlingResource
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class PlaylistFeature : BaseUITest() {

    val mActivityRule = ActivityTestRule(MainActivity::class.java)
        @Rule get


    @Test
    fun displayScreenTitle() {
        assertDisplayed("Playlists")
    }

    @Test
    fun displayListOfPlayList(){

        assertRecyclerViewItemCount(R.id.playlists_list,10)

        onView(allOf(withId(R.id.playlist_name),
        isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 0))))
            .check(matches(withText("Hard Rock Cafe")))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.playlist_category),
            isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 0))))
            .check(matches(withText("rock")))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.playlist_image),
            isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 0))))
            .check(matches(withDrawable(R.mipmap.rock)))
            .check(matches(isDisplayed()))

    }

    @Test
    fun displayLoaderWhileFetchingThePlaylists(){
        IdlingRegistry.getInstance().unregister(idlingResource)
        assertDisplayed(R.id.loader)
    }

    @Test
    fun hideLoader() {
        assertNotDisplayed(R.id.loader)
    }
    @Test
    fun displayRockImageForRockListItems(){

        onView(allOf(withId(R.id.playlist_image),
            isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 0))))
            .check(matches(withDrawable(R.mipmap.rock)))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.playlist_image),
            isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 3))))
            .check(matches(withDrawable(R.mipmap.rock)))
            .check(matches(isDisplayed()))
    }
}