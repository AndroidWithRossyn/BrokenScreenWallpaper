package com.brokenscreen.prank.hdnaturewallpaper.ui

import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.brokenscreen.prank.hdnaturewallpaper.R

abstract class BaseActivity : AppCompatActivity() {

    protected fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }





}



