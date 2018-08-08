package com.zavijavasoft.yafina.ui.settings.account

import android.os.Bundle
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.ui.settings.account.edit.EditAccountFragment
import com.zavijavasoft.yafina.ui.settings.account.list.AccountListFragment

class AccountActivity : MvpAppCompatActivity(),
        AccountListFragment.OnFragmentInteractionListener,
        EditAccountFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounts)

        showAccountList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val fm = supportFragmentManager
                if (fm.backStackEntryCount > 1) {
                    fm.popBackStack()
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onEditAccount(accountId: Long) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, EditAccountFragment.newInstance(accountId))
                .commit()
    }

    override fun onCreateAccount() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, EditAccountFragment.newInstance())
                .commit()
    }

    override fun close() {
        showAccountList()
    }

    private fun showAccountList() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, AccountListFragment.newInstance())
                .commit()
    }
}
