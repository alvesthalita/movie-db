package com.thalita.movie_db_app.ui.activities.generic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.thalita.movie_db_app.R
import java.util.*

abstract class GenericActivity : AppCompatActivity(){

    private var fm: FragmentManager? = null
    private var ft: FragmentTransaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startMethods()
    }

    protected fun startMethods() {
        fm = supportFragmentManager
        setLayout()
        configActionBar()
        getObjects()
        setObjects()
    }

    private fun configActionBar() {
        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.elevation = 0f
        }
    }

    fun configBackButton() {
        val actionBar = supportActionBar

        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun startActivity(activityType: Class<*>, bundle: Bundle?) {
        val intent = Intent(applicationContext, activityType)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        if (bundle != null) {
            intent.putExtras(bundle)
        }

        startActivity(intent)
    }

    fun hideTop(fullScreen: Boolean) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        if (fullScreen) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        Objects.requireNonNull(supportActionBar)?.hide()
    }

    fun replaceFragment(fragment: Fragment) {
        fm!!.popBackStackImmediate()
        ft = fm!!.beginTransaction()
        ft!!.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim)
        ft!!.replace(R.id.main_contentframe, fragment)
        ft!!.commit()
    }


    fun replaceFragment(fragment: Fragment, layout: Int) {
        fm!!.popBackStackImmediate()
        ft = fm!!.beginTransaction()
        ft!!.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim)
        ft!!.replace(layout, fragment)
        ft!!.commit()
    }

    fun setHomeAsUp() {
        if (actionBar != null) {
            actionBar!!.hide()
        }

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    fun setActionBar() {
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    fun setKeyboardVisibility(editText: EditText, visible: Boolean) {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (visible) {
            editText.requestFocus()
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        } else {
            editText.clearFocus()
            editText.setText("")
            imm.hideSoftInputFromWindow(
                editText.rootView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    abstract fun setLayout()

    abstract fun getObjects()

    abstract fun setObjects()
}