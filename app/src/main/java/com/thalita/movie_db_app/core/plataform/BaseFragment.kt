package com.thalita.movie_db_app.core.plataform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(){

    private var layoutId: Int = 0
    private var titleId: Int = 0
    private var v: View? = null

    fun BaseFragment(layoutId: Int, titleId: Int) {
        this.layoutId = layoutId
        this.titleId = titleId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(layoutId, container, false)

        if (activity != null) {
            activity!!.title = getString(titleId)
        }

        startMethods()
        return v
    }

    private fun startMethods() {
        getObjects()
        setObjects()
    }

    fun inflateMenu(layoutMenu: Int, menu: Menu) {
        if (activity != null) {
            activity!!.menuInflater.inflate(layoutMenu, menu)
        }
    }

    abstract fun getObjects()

    abstract fun setObjects()

    override fun getView(): View? {
        return v
    }
}