package com.miguel.armas.developer.lectorqrsinpublicidad.Adapter

import android.app.SearchManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.miguel.armas.developer.lectorqrsinpublicidad.Adapter.AdapterHistory.HistoryHolder
import com.miguel.armas.developer.lectorqrsinpublicidad.AdminSQLiteOpenHelper
import com.miguel.armas.developer.lectorqrsinpublicidad.Entidades.HistorySqlLite
import com.miguel.armas.developer.lectorqrsinpublicidad.HistoryFragment
import com.miguel.armas.developer.lectorqrsinpublicidad.R

class AdapterHistory(var historyItems: List<HistorySqlLite>, private val context: Context) : RecyclerView.Adapter<HistoryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_history, parent, false)
        val viewHolder = HistoryHolder(view)
        viewHolder.btnfind.setOnClickListener {
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra(SearchManager.QUERY, historyItems[viewHolder.adapterPosition].getsave_string())
            context.startActivity(intent)
        }
        viewHolder.btndelete.setOnClickListener {
            val id_found = historyItems[viewHolder.adapterPosition].getid_history().toString()
            val admin = AdminSQLiteOpenHelper(context, "administracion", null, 1)
            val BaseDeDatos = admin.readableDatabase
            val fund = BaseDeDatos.query("history", arrayOf("id_history"), "id_history = ?", arrayOf(id_found), null, null, null)
            if (fund.moveToFirst()) {
                BaseDeDatos.delete("history", "id_history = ?", arrayOf(id_found)) //este es para borrar
            }
            BaseDeDatos.close()
            fund.close()
            Toast.makeText(context, R.string.deletereg, Toast.LENGTH_LONG).show()
            try {
                val historyFragment = HistoryFragment()
                val manager = (context as AppCompatActivity).supportFragmentManager
                //for(int i = 0; i < manager.getBackStackEntryCount(); ++i) {
                manager.popBackStack()
                //}
                val fragmentTransaction = manager.beginTransaction()
                fragmentTransaction
                        .replace(R.id.content_main, historyFragment, "historyFragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        viewHolder.btncopy.setOnClickListener {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("text", historyItems[viewHolder.adapterPosition].getsave_string().toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, R.string.copystring, Toast.LENGTH_LONG).show()
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        holder.id_history.text = historyItems[position].getid_history()
        holder.save_string.text = historyItems[position].getsave_string()
    }

    override fun getItemCount(): Int {
        return historyItems.size
    }

    inner class HistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val save_string: TextView
        val id_history: TextView
        private val date: TextView
        val btndelete: MaterialButton
        val btncopy: MaterialButton
        val btnfind: MaterialButton

        init {
            save_string = itemView.findViewById(R.id.save_string)
            id_history = itemView.findViewById(R.id.id_history)
            date = itemView.findViewById(R.id.date)
            btndelete = itemView.findViewById(R.id.btndelete)
            btncopy = itemView.findViewById(R.id.btncopy)
            btnfind = itemView.findViewById(R.id.btnfind)
        }
    }

}