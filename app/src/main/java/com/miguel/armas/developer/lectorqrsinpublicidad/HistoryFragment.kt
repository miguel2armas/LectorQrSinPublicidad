package com.miguel.armas.developer.lectorqrsinpublicidad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miguel.armas.developer.lectorqrsinpublicidad.Adapter.AdapterHistory
import com.miguel.armas.developer.lectorqrsinpublicidad.Entidades.HistorySqlLite
import java.util.*

/**
 * create by Miguel Armas.
 */
class HistoryFragment : Fragment() {
    private var adapterHistory: AdapterHistory? = null
    lateinit var recyclerhistory: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        recyclerhistory = view.findViewById(R.id.recyclerhistory)
        recyclerhistory.setLayoutManager(LinearLayoutManager(this.context))
        recyclerhistory.setHasFixedSize(true)
        //SE LLAMA LA LISTA DESDE LA BASE DE DATOS LOCAL
        val admin = AdminSQLiteOpenHelper(activity, "administracion", null, 1)
        val BaseDeDatos = admin.readableDatabase
        val fila = BaseDeDatos.rawQuery("SELECT * FROM history ORDER by id_history DESC", null)
        val historydb: MutableList<HistorySqlLite> = ArrayList()
        if (fila.moveToFirst()) {
            do {
                historydb.add(HistorySqlLite(fila.getString(0), fila.getString(1), fila.getInt(2)))
            } while (fila.moveToNext())
        }
        fila.close()
        BaseDeDatos.close()
        //SE LLENA UNA ENTIDAD Y LUEGO SE PASA A UN ADAPTER PARA POSTERIORMENTE MOSTRARLA EN UN RECYCLERVIEW
        adapterHistory = AdapterHistory(historydb, requireContext())
        recyclerhistory.setAdapter(adapterHistory)
        BaseDeDatos.close()
        return view
    }
}