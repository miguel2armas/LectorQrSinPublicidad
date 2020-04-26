package com.miguel.armas.developer.lectorqrsinpublicidad;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.miguel.armas.developer.lectorqrsinpublicidad.Adapter.AdapterHistory;
import com.miguel.armas.developer.lectorqrsinpublicidad.Entidades.HistorySqlLite;

import java.util.ArrayList;
import java.util.List;


/**
 * create by Miguel Armas.
 */
public class HistoryFragment extends Fragment {
    private AdapterHistory adapterHistory;
    RecyclerView recyclerhistory;
    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerhistory = view.findViewById(R.id.recyclerhistory);
        recyclerhistory.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerhistory.setHasFixedSize(true);
        //SE LLAMA LA LISTA DESDE LA BASE DE DATOS LOCAL
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();
        Cursor fila=BaseDeDatos.rawQuery("SELECT * FROM history ORDER by id_history DESC", null);
        List<HistorySqlLite> historydb = new ArrayList<>();
        if (fila.moveToFirst()){
            do{
                historydb.add(new HistorySqlLite(fila.getString(0),fila.getString(1), fila.getInt(2)));
            }while (fila.moveToNext());
        }
        fila.close();
        BaseDeDatos.close();
        //SE LLENA UNA ENTIDAD Y LUEGO SE PASA A UN ADAPTER PARA POSTERIORMENTE MOSTRARLA EN UN RECYCLERVIEW
        adapterHistory =new AdapterHistory(historydb, getContext());
        recyclerhistory.setAdapter(adapterHistory);
        BaseDeDatos.close();
        return view;
    }
}
