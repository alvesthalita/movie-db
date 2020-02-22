package com.thalita.movie_db_app.ui.fragments.generic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class GenericFragment : Fragment(){

    private var layoutId: Int = 0
    private var titleId: Int = 0
    private var v: View? = null

    fun GenericFragment(layoutId: Int, titleId: Int) {
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
        //ButterKnife.bind(this, v)
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