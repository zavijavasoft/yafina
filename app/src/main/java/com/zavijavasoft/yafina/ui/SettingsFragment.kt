package com.zavijavasoft.yafina.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.zavijavasoft.yafina.R

class SettingsFragment : MvpAppCompatFragment(), SettingsView {


    companion object {
        val TAG_YAFINA_SETTINGS_FRAGMENT = "TAG_YAFINA_SETTINGS_FRAGMENT"

        fun getInstance(): SettingsFragment {
            val fragment = SettingsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


    override fun update(currencyList: List<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


}
