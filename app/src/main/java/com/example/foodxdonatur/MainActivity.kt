package com.example.foodxdonatur

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.foodxdonatur.fragment.AccountFragment
import com.example.foodxdonatur.fragment.HistoryFragment
import com.example.foodxdonatur.komunitas.KomunitasFragment
import com.example.foodxdonatur.fragment.NotificationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {item->
        when(item.itemId){
            R.id.home -> {
                println("homePressed")
                replaceFragment(KomunitasFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.history -> {
                println("homePressed")
                replaceFragment(HistoryFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.notifications -> {
                println("homePressed")
                replaceFragment(NotificationFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.account -> {
                println("homePressed")
                replaceFragment(AccountFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }




}
