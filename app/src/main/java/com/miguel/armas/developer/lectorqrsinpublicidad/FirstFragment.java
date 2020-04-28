package com.miguel.armas.developer.lectorqrsinpublicidad;

import android.app.SearchManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.miguel.armas.developer.lectorqrsinpublicidad.PublicMethodAndClass.cerrarteclado;

public class FirstFragment extends Fragment {
    int scanner = 0;
    String resultado;
    LinearLayout codeview;
    int foundstring = 0;
    TextInputEditText codetext;
    MaterialCardView qrcode,matrixcode, code128, code93, code39, codeitf, codeean13, codeupca, codeean8, codeupce, codepdf417, rssexpanded, coderss14;
    MaterialButton btncopy, btnviewhistory, btnopenweb;
    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private final int MIS_PERMISOS = 100;
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        solicitaPermisosVersionesSuperiores();
        coderss14 = view.findViewById(R.id.coderss14);
        btncopy = view.findViewById(R.id.btncopy);
        btnopenweb = view.findViewById(R.id.btnopenweb);
        btnviewhistory = view.findViewById(R.id.btnviewhistory);
        codetext = view.findViewById(R.id.codetext);
        codeview = view.findViewById(R.id.codeview);
        qrcode = view.findViewById(R.id.qrcode);
        matrixcode = view.findViewById(R.id.matrixcode);
        code128 = view.findViewById(R.id.code128);
        code93 = view.findViewById(R.id.code93);
        code39 = view.findViewById(R.id.code39);
        codeitf = view.findViewById(R.id.codeitf);
        codeean13 = view.findViewById(R.id.codeean13);
        codeupca = view.findViewById(R.id.codeupca);
        codeean8 = view.findViewById(R.id.codeean8);
        codeupce = view.findViewById(R.id.codeupce);
        rssexpanded= view.findViewById(R.id.rssexpanded);
        codepdf417 = view.findViewById(R.id.codepdf417);
        btnopenweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, resultado);
                getContext().startActivity(intent);
            }
        });
        btnviewhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryFragment historyFragment = new HistoryFragment();
                //Bundle bundle = new Bundle();
                //bundle.putString("id_shop",id_shop);
                //FragmentManager fm = getActivity().getSupportFragmentManager();
                //for(int i = 0; i < fm.getBackStackEntryCount(); ++i) { fm.popBackStack(); }
                //historyFragment.setArguments(bundle);
                FragmentManager manager = (getActivity().getSupportFragmentManager());
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, historyFragment, "historyFragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                cerrarteclado(getActivity());
            }
        });
        btncopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text",  resultado);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), R.string.copystring, Toast.LENGTH_LONG).show();
            }
        });
        coderss14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getscannercoderss14();
            }
        });
        rssexpanded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getscannerrssexpanded();
            }
        });
        codepdf417.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getscannercodepdf417();
            }
        });
        codeupce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getscannercodeupce();
            }
        });
        codeean8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getscannercodeean8();
            }
        });
        codeupca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getscannercodeupca();
            }
        });
        codeean13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getscannercodeean13();
            }
        });
        codeitf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getscannercodeitf();
            }
        });
        code39.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getscannercode39();
            }
        });
        code93.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getscannercode93();
            }
        });
        matrixcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getscannermatrixcode();
            }
        });
        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  getscannerqrcode();
            }
        });
        code128.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getscannercode128();
            }
        });
        return view;
    }
    private void getscannercoderss14() {
        IntentIntegrator intent = IntentIntegrator.forSupportFragment(FirstFragment.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        intent.setPrompt("SCANNER CODE RSS 14");
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setOrientationLocked(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }
    private void getscannerrssexpanded() {
        IntentIntegrator intent = IntentIntegrator.forSupportFragment(FirstFragment.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        intent.setPrompt("SCANNER CODE RSS EXPANDED");
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setOrientationLocked(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }
    private void getscannercodepdf417() {
        IntentIntegrator intent = IntentIntegrator.forSupportFragment(FirstFragment.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.PDF_417);
        intent.setPrompt("SCANNER CODE PDF 417");
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setOrientationLocked(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }
    private void getscannercodeupce() {
        IntentIntegrator intent = IntentIntegrator.forSupportFragment(FirstFragment.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        intent.setPrompt("SCANNER CODE UPC E");
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setOrientationLocked(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }
    private void getscannercodeean8() {
        IntentIntegrator intent = IntentIntegrator.forSupportFragment(FirstFragment.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        intent.setPrompt("SCANNER CODE EAN 8");
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setOrientationLocked(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }
    private void getscannercodeupca() {
        IntentIntegrator intent = IntentIntegrator.forSupportFragment(FirstFragment.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        intent.setPrompt("SCANNER CODE UPC A");
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setOrientationLocked(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }
    private void getscannercodeean13() {
        IntentIntegrator intent = IntentIntegrator.forSupportFragment(FirstFragment.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        intent.setPrompt("SCANNER CODE EAN 13");
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setOrientationLocked(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }
    private void getscannercodeitf() {
        IntentIntegrator intent = IntentIntegrator.forSupportFragment(FirstFragment.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        intent.setPrompt("SCANNER CODE ITF");
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setOrientationLocked(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }
    private void getscannercode39() {
        IntentIntegrator intent = IntentIntegrator.forSupportFragment(FirstFragment.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        intent.setPrompt("SCANNER CODE 39");
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setOrientationLocked(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }
    private void getscannercode93() {
        IntentIntegrator intent = IntentIntegrator.forSupportFragment(FirstFragment.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        intent.setPrompt("SCANNER CODE 93");
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setOrientationLocked(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }
    private void getscannercode128() {
        IntentIntegrator intent = IntentIntegrator.forSupportFragment(FirstFragment.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.CODE_128);
        intent.setPrompt("SCANNER CODE 128");
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setOrientationLocked(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }
    private void getscannerqrcode() {
        IntentIntegrator intent = IntentIntegrator.forSupportFragment(FirstFragment.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intent.setPrompt("SCANNER CODE QR");
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setOrientationLocked(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }
    private void getscannermatrixcode() {
        IntentIntegrator intent = IntentIntegrator.forSupportFragment(FirstFragment.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.DATA_MATRIX);
        intent.setPrompt("SCANNER CODE MATRIX");
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setOrientationLocked(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    //PARA PEDIR LOS PERMISOS NECESARIOS DE LA APLICACION
    private boolean solicitaPermisosVersionesSuperiores() {
        //validamos si los permisos ya fueron aceptados
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(getContext().checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        if (shouldShowRequestPermissionRationale(CAMERA)){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{CAMERA}, MIS_PERMISOS);
        }

        return false;//implementamos el que procesa el evento dependiendo de lo que se defina aqui
    }
    //SI NO ACEPTA LOS PERMISOS SE LE INFORMA AL USUARIO QUE LA APLICACION NO PODRÁ TENER UN BUEN FUNCIONAMIENTO
    private void cargarDialogoRecomendacion() {
        androidx.appcompat.app.AlertDialog.Builder dialogo=new androidx.appcompat.app.AlertDialog.Builder(getContext());
        dialogo.setTitle(R.string.permissdes);
        dialogo.setMessage(R.string.messajepermisor);

        dialogo.setPositiveButton(R.string.acepted, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION},100);
            }
        });
        dialogo.show();
    }
    //recibe los resultados de el escanner o en la camara o en la galeria
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(getContext(), R.string.cancelated, Toast.LENGTH_LONG).show();
            }else {
                codeview.setVisibility(View.VISIBLE);
                btnopenweb.setVisibility(View.VISIBLE);
                codetext.setText(result.getContents().toString());
                this.resultado = result.getContents().toString();
                String id_history = String.valueOf(System.currentTimeMillis());
                int time= 0;
                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "administracion", null, 1);
                SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
                ContentValues registro = new ContentValues();
                registro.put("id_history", id_history);
                registro.put("save_string", resultado);
                registro.put("date", time);
                BaseDeDatos.insert("history", null, registro);// este para insertar
                BaseDeDatos.close();
                /*AdminSQLiteOpenHelper admin4 = new AdminSQLiteOpenHelper(getContext(), "administracion", null, 1);
                SQLiteDatabase BaseDeDatos4 = admin4.getReadableDatabase();
                Cursor fila4=BaseDeDatos4.rawQuery("SELECT save_string FROM history", null);
                String save_stringend;
                if (fila4.moveToFirst()) {
                    save_stringend = fila4.getString(0);
                    if (save_stringend.equals(resultado) ){
                        this.foundstring = 1;
                    }else{
                        this.foundstring = 0;
                    }
                }else{
                    Toast.makeText(getContext(), "sin datos", Toast.LENGTH_LONG).show();
                    this.foundstring = 0;
                }
                if(foundstring==0) {
                    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "administracion", null, 1);
                    SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
                    ContentValues registro = new ContentValues();
                    registro.put("id_history", id_history);
                    registro.put("save_string", resultado);
                    registro.put("date", time);
                    BaseDeDatos.insert("history", null, registro);// este para insertar
                    BaseDeDatos.close();
                }
                fila4.close();*/
            }
        }
    }
}
