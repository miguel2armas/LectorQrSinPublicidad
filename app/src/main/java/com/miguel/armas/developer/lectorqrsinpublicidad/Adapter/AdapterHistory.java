package com.miguel.armas.developer.lectorqrsinpublicidad.Adapter;

import android.app.SearchManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.miguel.armas.developer.lectorqrsinpublicidad.AdminSQLiteOpenHelper;
import com.miguel.armas.developer.lectorqrsinpublicidad.Entidades.HistorySqlLite;
import com.miguel.armas.developer.lectorqrsinpublicidad.HistoryFragment;
import com.miguel.armas.developer.lectorqrsinpublicidad.R;

import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.HistoryHolder>{
    private final Context context;
    @NonNull
    @Override
    public AdapterHistory.HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_history,parent,false);
        final HistoryHolder viewHolder=new HistoryHolder(view);
        viewHolder.btnfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, historyItems.get(viewHolder.getAdapterPosition()).getsave_string());
                context.startActivity(intent);
            }
        });
        viewHolder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_found = String.valueOf(historyItems.get(viewHolder.getAdapterPosition()).getid_history());
                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "administracion", null, 1);
                SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();
                Cursor fund = BaseDeDatos.query("history", new String[]{"id_history"}, "id_history = ?", new String[] {id_found}, null, null, null);
                if(fund.moveToFirst()){
                    BaseDeDatos.delete("history", "id_history = ?", new String[] {id_found}); //este es para borrar
                }
                BaseDeDatos.close();
                Toast.makeText(context, R.string.deletereg, Toast.LENGTH_LONG).show();
                try {
                    HistoryFragment historyFragment = new HistoryFragment();
                    FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                    //for(int i = 0; i < manager.getBackStackEntryCount(); ++i) {
                        manager.popBackStack();
                    //}
                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    fragmentTransaction
                            .replace(R.id.content_main, historyFragment, "historyFragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null)
                            .commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        viewHolder.btncopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text",  String.valueOf(historyItems.get(viewHolder.getAdapterPosition()).getsave_string()));
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, R.string.copystring, Toast.LENGTH_LONG).show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistory.HistoryHolder holder, int position) {
        holder.id_history.setText(historyItems.get(position).getid_history());
        holder.save_string.setText(historyItems.get(position).getsave_string());
    }
    public List<HistorySqlLite> historyItems;
    public AdapterHistory(List<HistorySqlLite> historyItems, Context context){
        this.historyItems = historyItems;
        this.context=context;
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {
        private TextView save_string, id_history, date;
        private MaterialButton btndelete, btncopy,btnfind;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            save_string=itemView.findViewById(R.id.save_string);
            id_history=itemView.findViewById(R.id.id_history);
            date=itemView.findViewById(R.id.date);
            btndelete=itemView.findViewById(R.id.btndelete);
            btncopy=itemView.findViewById(R.id.btncopy);
            btnfind=itemView.findViewById(R.id.btnfind);
        }
    }
}
