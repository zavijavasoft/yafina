package com.zavijavasoft.yafina.ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.ui.balance.BalanceFragment
import com.zavijavasoft.yafina.ui.operation.OperationFragment
import com.zavijavasoft.yafina.ui.settings.SettingsFragment
import com.zavijavasoft.yafina.ui.transactions.TransactionsFragment
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun test_initActivity() {
        onView(withId(R.id.navigation)).check(matches(isDisplayed()))
        onView(withId(R.id.screenViewPager)).check(matches(isDisplayed()))

        val currentFragment = rule.activity.supportFragmentManager.fragments[0]
        assertThat(currentFragment, instanceOf(BalanceFragment::class.java))
    }

    @Test
    fun test_clickOperationButton() {
        onView(withId(R.id.navigation_operations)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_operations)).perform(click())
        val currentFragment = rule.activity.supportFragmentManager.fragments[0]
        assertThat(currentFragment, instanceOf(OperationFragment::class.java))
    }

    @Test
    fun test_clickTransactionsButton() {
        onView(withId(R.id.navigation_transactions)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_transactions)).perform(click())
        val currentFragment = rule.activity.supportFragmentManager.fragments[0]
        assertThat(currentFragment, instanceOf(TransactionsFragment::class.java))
    }

    @Test
    fun test_clickSettingsButton() {
        onView(withId(R.id.navigation_settings)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_settings)).perform(click())
        val currentFragment = rule.activity.supportFragmentManager.fragments[0]
        assertThat(currentFragment, instanceOf(SettingsFragment::class.java))
    }

    @Test
    fun test_clickAboutFragment() {
        onView(withId(R.id.navigation_about)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_about)).perform(click())
        val currentFragment = rule.activity.supportFragmentManager.fragments[0]
        assertThat(currentFragment, instanceOf(AboutFragment::class.java))
    }
}