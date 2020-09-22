package com.example.callofdutymw_stats.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {

    private val fragmentsList = ArrayList<Fragment>()
    private val fragmentTitles = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentsList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentsList.add(fragment)
        fragmentTitles.add(title)
    }

    override fun getCount(): Int {
        return fragmentsList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitles[position]

    }

}