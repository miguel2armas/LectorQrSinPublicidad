package com.miguel.armas.developer.lectorqrsinpublicidad.Entidades

class HistorySqlLite {
    private var id_history: String? = null
    private var save_string: String? = null
    private var date = 0

    constructor() {}
    constructor(id_history: String?, save_string: String?, date: Int) {
        this.id_history = id_history
        this.save_string = save_string
        this.date = date
    }

    fun getid_history(): String? {
        return id_history
    }

    fun setid_history(id_history: String?) {
        this.id_history = id_history
    }

    fun getsave_string(): String? {
        return save_string
    }

    fun setsave_string(save_string: String?) {
        this.save_string = save_string
    }

    fun getdate(): Int {
        return date
    }

    fun setdate(date: Int) {
        this.date = date
    }
}