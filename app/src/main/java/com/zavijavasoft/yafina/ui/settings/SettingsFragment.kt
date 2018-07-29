package com.zavijavasoft.yafina.ui.settings


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.YaFinaApplication

class SettingsFragment : MvpAppCompatFragment(), SettingsView {


    companion object {
        const val TAG_YAFINA_SETTINGS_FRAGMENT = "TAG_YAFINA_SETTINGS_FRAGMENT"

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


    override fun onAttach(context: Context?) {
        YaFinaApplication.component.inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


}
