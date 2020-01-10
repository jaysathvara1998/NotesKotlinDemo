package com.example.noteskotlindemo.Activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.noteskotlindemo.Fragment.NotesFragment
import com.example.noteskotlindemo.Fragment.ReminderFragment
import com.example.noteskotlindemo.R
import com.example.noteskotlindemo.replaceFragmenty
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    companion object{
        @JvmStatic var drawerLayout: DrawerLayout? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        displaySelectedScreen(R.id.nav_note)
        Toast.makeText(applicationContext,"sdf",LENGTH_LONG)
    }

    private fun displaySelectedScreen(itemId: Int) {
        //creating fragment object
        var fragment: Fragment? = null
        //initializing the fragment object which is selected
        when (itemId) {
            R.id.nav_note -> fragment = NotesFragment()
            R.id.nav_reminder -> fragment = ReminderFragment()
        }

        //replacing the fragment
        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.content_frame, fragment)
            ft.commit()
        }
        drawer_layout.closeDrawer(GravityCompat.START)
    }

    private fun init() {
        val toggle = ActionBarDrawerToggle(this,drawer_layout,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawerLayout = findViewById(R.id.drawer_layout)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_note -> {
                replaceFragmenty(fragment = NotesFragment(),allowStateLoss = true,containerViewId = R.id.content_frame)
            }
            R.id.nav_reminder -> {
                replaceFragmenty(fragment = ReminderFragment(),allowStateLoss = true,containerViewId = R.id.content_frame)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}