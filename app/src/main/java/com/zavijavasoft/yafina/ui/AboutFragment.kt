package com.zavijavasoft.yafina.ui


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zavijavasoft.yafina.R


class AboutFragment : Fragment() {

    companion object {
        const val TAG_YAFINA_ABOUT_FRAGMENT = "TAG_YAFINA_ABOUT_FRAGMENT"

        fun getInstance(): AboutFragment {
            val fragment = AboutFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }


}
