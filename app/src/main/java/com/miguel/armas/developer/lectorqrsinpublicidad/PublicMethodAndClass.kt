package com.miguel.armas.developer.lectorqrsinpublicidad

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

object PublicMethodAndClass {
    //OCULTA EL TECLADO CUANDO ESTE YA NO ES REQUERIDO
    @JvmStatic
    fun cerrarteclado(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}