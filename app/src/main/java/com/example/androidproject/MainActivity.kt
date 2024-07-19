package com.example.androidproject

import com.example.androidproject.presentation.splash.SplashScreenFragment
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.room.Room
import com.example.androidproject.database.AppDatabase
import com.example.androidproject.presentation.detail.DetailScreenFragment
import com.example.androidproject.presentation.favorite.FavoriteScreenFragment
import com.example.androidproject.presentation.home.HomeScreenFragment
import com.example.androidproject.presentation.home.MovieModule
import com.example.androidproject.presentation.register.RegisterScreenFragment
import com.example.androidproject.presentation.search.SearchScreenFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    //region properties
    private lateinit var bottomNav: BottomNavigationView

    public val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "AppDatabase"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    //endregion


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configListeners()
        setContentView(R.layout.activity_main)
        MovieModule.initialize(appDatabase)
        bottomNav = findViewById(R.id.bottom_navigation)
        replaceFragment(SplashScreenFragment())
        setupBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
//        checkItems()
    }

    private fun configListeners() {}

    //region methods

    //region navigation
    fun setNavigationVisibility(isVisible: Boolean) {
        setBottomNavigationVisibility(isVisible)
    }

    fun replaceFragment(fragment: Fragment) {
        try {
            Log.d("MainActivity", "Replacing fragment with ${fragment.javaClass.simpleName}")
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()

            setBottomNavigationVisibility(fragment !is SplashScreenFragment && fragment !is RegisterScreenFragment && fragment !is DetailScreenFragment)
        } catch (e: Exception) {
            Log.e("MainActivity", "Error replacing fragment", e)
        }
    }

    private fun setupBottomNavigation() {
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeScreenFragment())
                    true
                }

                R.id.nav_search -> {
                    replaceFragment(SearchScreenFragment())
                    true
                }

                R.id.nav_favorites -> {
                    replaceFragment(FavoriteScreenFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun setBottomNavigationVisibility(isVisible: Boolean) {
        bottomNav.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    interface NavigationVisibilityListener {
        fun setNavigationVisibility(isVisible: Boolean)
    }
}
    //endregion

    //region on Back pressed
//    override fun onBackPressed() {
//        if (supportFragmentManager.backStackEntryCount == 0) {
//            if (bottomNav.selectedItemId == R.id.nav_home) {
//                super.onBackPressed()
//            } else {
//                bottomNav.selectedItemId = R.id.nav_home
//            }
//        } else {
//            super.onBackPressed()
//        }
//    }

    //endregion

