package com.zavijavasoft.yafina.ui

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.ui.operation.TRANSACTION_REQUEST_TAG
import com.zavijavasoft.yafina.ui.operation.TransactionRequest
import com.zavijavasoft.yafina.ui.operation.TransactionType
import org.hamcrest.core.IsNot.not
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OpenTransactionActivityTest {

    @Rule
    @JvmField
    val rule: ActivityTestRule<OpenTransactionActivity> = OpenTransactionActivityTestRule()

    @Test
    fun test_initActivity() {
        onView(withId(R.id.enter_sum_editor))
                .check(matches(isDisplayed()))
        onView(withId(R.id.entered_sum_currency))
                .check(matches(isDisplayed()))
                .check(matches(withText("RUR")))
        onView(withId(R.id.chbx_is_scheduled))
                .check(matches(isDisplayed()))
                .check(matches(not(isChecked())))
        onView(withId(R.id.spinner_timeUnits))
                .check(matches(not(isDisplayed())))
        onView(withId(R.id.button_confirm_transaction))
                .check(matches(isDisplayed()))
        onView(withId(R.id.button_cancel_transaction))
                .check(matches(isDisplayed()))
    }

    @Test
    fun test_closeActivityOnCancel() {
        onView(withId(R.id.button_cancel_transaction))
                .perform(click())
        assertTrue(rule.activity.isFinishing)
    }

    private class OpenTransactionActivityTestRule:
            ActivityTestRule<OpenTransactionActivity>(OpenTransactionActivity::class.java) {

        override fun getActivityIntent(): Intent {
            val intent = Intent()
            intent.putExtra(TRANSACTION_REQUEST_TAG, TransactionRequest(TransactionType.INCOME,
                    false,
                    Float.NaN, "RUR",
                    -1, -1,
                    1, -1, -1, -1))
            return intent
        }

    }
}