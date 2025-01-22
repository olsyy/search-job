package com.example.jobsearching.main

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.jobsearching.R
import com.example.jobsearching.activities.ResumeActivity
import com.example.jobsearching.databinding.ActivityMainBinding
import com.example.jobsearching.recyclerview.ViewPagerAdapter
import com.example.jobsearching.utils.viewBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupTabLayout()
        setSupportActionBar(binding.mainToolBar)
        setupSidebar()
    }

    private fun setupSidebar() {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        drawerLayout = binding.drawerLayout
        drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.mainToolBar,
            R.string.nav_open,
            R.string.nav_close
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.action_my_resume -> {
                    ResumeActivity.start(this)
                }

                else -> throw RuntimeException("Unknown menu item")
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (drawerToggle.onOptionsItemSelected(item)) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setupTabLayout() {
        with(binding) {
            tabLayout = mainTabLayout
            viewPager = mainViewPager
        }

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabText(position)
            tab.icon = getTabIcon(position)
        }.attach()
    }

    private fun getTabText(position: Int): String {
        val tabNames = resources.getStringArray(R.array.tab_name)
        return tabNames.getOrNull(position) ?: throwWrongTabPosition()
    }

    private fun getTabIcon(position: Int): Drawable? = when (position) {
        0 -> ContextCompat.getDrawable(this, R.drawable.ic_companies_tab)
        1 -> ContextCompat.getDrawable(this, R.drawable.ic_vacancies_tab)
        else -> throwWrongTabPosition()
    }

    private fun throwWrongTabPosition(): Nothing = throw Exception("Wrong tab position")
}