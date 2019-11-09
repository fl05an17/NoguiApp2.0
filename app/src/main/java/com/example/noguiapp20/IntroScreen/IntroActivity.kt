package com.example.noguiapp20.IntroScreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import com.example.noguiapp20.Registro
import com.example.noguiapp20.IntroScreen.IntroViewPagerAdapter
import com.example.noguiapp20.MainActivity
import com.example.noguiapp20.R
import com.google.android.material.tabs.TabLayout

class IntroActivity : AppCompatActivity() {

    private var screenPager: ViewPager? = null
    protected lateinit var introViewPagerAdapter: IntroViewPagerAdapter
    protected lateinit var tabIndicator: TabLayout
    protected lateinit var btnNext: Button
    protected var position = 0
    protected lateinit var btnGetStart: Button
    protected lateinit var btnAnim: Animation
    private lateinit var title1: String
    private lateinit var title2: String
    private lateinit var title3: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        //make the activity on full screen
        /*requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )*/

        //when this activity is about to be launch we need to check if its opened before or not

        if (restorePrefData()) {
            val mainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        }

        setContentView(R.layout.activity_intro)

        //hide the action bar
        /*supportActionBar!!.hide()*/

        //ini views
        btnNext = findViewById(R.id.btnNext)
        btnGetStart = findViewById(R.id.btn_get_start)
        tabIndicator = findViewById(R.id.tab_indicator)
        btnAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.button_animation)

        title1 = (resources.getString(R.string.title_intro_1))
        title2 = (resources.getString(R.string.title_intro_2))
        title3 = (resources.getString(R.string.title_intro_3))

        //fill list screen
        val mList = ArrayList<ScreenItem>()
        mList.add(
            ScreenItem(
                "$title1",
                R.drawable.img_intro_1
            )
        )
        mList.add(
            ScreenItem(
                "$title2",
                R.drawable.img_intro_2
            )
        )
        mList.add(
            ScreenItem(
                "$title3",
                R.drawable.img_intro_3
            )
        )
        //Setup viewPager
        screenPager = findViewById(R.id.screen_viewpager)
        introViewPagerAdapter = IntroViewPagerAdapter(this, mList)
        screenPager!!.adapter = introViewPagerAdapter

        //setup tablayout with viewpager
        tabIndicator.setupWithViewPager(screenPager)

        //next button click listener
        btnNext.setOnClickListener {
            position = screenPager!!.currentItem
            if (position < mList.size - 1) {
                position++
                screenPager!!.currentItem = position
            }
            if (position == mList.size) {//when we rench to the last screen
                //TODO : show the GETSTARTED Button and hide the indicator and the next button
                loadLastScreen()
            }
        }

        //tablayout add change listener
        tabIndicator.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == mList.size - 1) {
                    loadLastScreen()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        //get started button click listener
        btnGetStart.setOnClickListener {
            //open main activity
            val mainActivity = Intent(applicationContext, Registro::class.java)
            startActivity(mainActivity)

            /*also we need to save a boolean value to storage so next time when the
                user run the app we could now that he is already checked the intro screen activity */

            savePrefsData()
            finish()
        }

    }

    private fun restorePrefData(): Boolean {
        val pref = applicationContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        return pref.getBoolean("isIntroOpened", false)
    }

    private fun savePrefsData() {
        val pref = applicationContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isIntroOpened", true)
        editor.commit()
    }

    //show the GETSTARTED Button and hide the indicator and the next button
    private fun loadLastScreen() {
        btnNext.visibility = View.INVISIBLE
        btnGetStart.visibility = View.VISIBLE
        tabIndicator.visibility = View.INVISIBLE
        //TODO : ADD an animation the getstarted button
        //setup animation
        btnGetStart.animation = btnAnim

    }
}