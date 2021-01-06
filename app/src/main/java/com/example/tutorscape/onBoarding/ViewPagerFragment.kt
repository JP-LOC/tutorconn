package com.example.tutorscape.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tutorscape.R
import com.example.tutorscape.onBoarding.screens.FirstScreen
import com.example.tutorscape.onBoarding.screens.SecondScreen
import com.example.tutorscape.onBoarding.screens.ThirdScreen
import kotlinx.android.synthetic.main.fragment_view_pager.view.*

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf<Fragment>(
                FirstScreen(),
                SecondScreen(),
                ThirdScreen()
        )

        val adapter = ViewPagerAdapter(
                fragmentList,
                requireActivity().supportFragmentManager,
                lifecycle
        )

        view.viewPager.adapter = adapter

        return view
    }

}