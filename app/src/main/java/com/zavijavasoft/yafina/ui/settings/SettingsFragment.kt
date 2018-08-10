package com.zavijavasoft.yafina.ui.settings


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.YaFinaApplication

class SettingsFragment : SettingsView, PreferenceFragmentCompat() {

    private lateinit var preferences: SharedPreferences

    companion object {
        const val TAG_YAFINA_SETTINGS_FRAGMENT = "TAG_YAFINA_SETTINGS_FRAGMENT"

        fun getInstance(): SettingsFragment {
            val fragment = SettingsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_general, rootKey)

    }

    override fun update(currencyList: List<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onAttach(context: Context?) {
        YaFinaApplication.component.inject(this)
        super.onAttach(context)
    }
}
