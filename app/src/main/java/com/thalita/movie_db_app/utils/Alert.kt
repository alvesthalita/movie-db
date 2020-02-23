package com.thalita.movie_db_app.utils

import android.app.Activity
import cn.pedant.SweetAlert.SweetAlertDialog


class Alert(private val activity: Activity, private val message: String) {

//    fun withItems(view: View?) {
//
//        val items = arrayOf("Doce", "Salgada")
//        val builder = AlertDialog.Builder(this)
//
//        with(builder)
//        {
//            setTitle("Selecione o tipo de receita")
//            setItems(items) { dialog, which ->
//                Toast.makeText(context, items[which] + " is clicked", Toast.LENGTH_SHORT).show()
//            }
//
//            setPositiveButton("Ok", positiveButtonClick)
//            show()
//        }
//    }
//
//    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
//        Toast.makeText(context, "Nenhum item selecionado", Toast.LENGTH_SHORT).show()
//    }

    fun simpleAlert(){
        val pDialog = SweetAlertDialog(activity)
        pDialog.setTitleText(message)
        pDialog.show()
    }

}