package com.miguel.armas.developer.lectorqrsinpublicidad

import android.Manifest.permission
import android.app.SearchManager
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.google.zxing.integration.android.IntentIntegrator
import com.miguel.armas.developer.lectorqrsinpublicidad.FirstFragment
import com.miguel.armas.developer.lectorqrsinpublicidad.PublicMethodAndClass.cerrarteclado

class FirstFragment : Fragment() {
    var scanner = 0
    var resultado: String? = null
    var foundstring = 0
    lateinit var btnopenweb: MaterialButton
    lateinit var codetext: TextInputEditText
    lateinit var codeview: LinearLayout
    private val MIS_PERMISOS = 100
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        solicitaPermisosVersionesSuperiores()
        val coderss14: MaterialCardView = view.findViewById(R.id.coderss14)
        val btncopy: MaterialButton = view.findViewById(R.id.btncopy)
        btnopenweb = view.findViewById(R.id.btnopenweb)
        val btnviewhistory: MaterialButton = view.findViewById(R.id.btnviewhistory)
        codetext = view.findViewById(R.id.codetext)
        codeview = view.findViewById(R.id.codeview)
        val qrcode: MaterialCardView = view.findViewById(R.id.qrcode)
        val matrixcode: MaterialCardView = view.findViewById(R.id.matrixcode)
        val code128: MaterialCardView = view.findViewById(R.id.code128)
        val code93: MaterialCardView = view.findViewById(R.id.code93)
        val code39: MaterialCardView = view.findViewById(R.id.code39)
        val codeitf: MaterialCardView = view.findViewById(R.id.codeitf)
        val codeean13: MaterialCardView = view.findViewById(R.id.codeean13)
        val codeupca: MaterialCardView = view.findViewById(R.id.codeupca)
        val codeean8: MaterialCardView = view.findViewById(R.id.codeean8)
        val codeupce: MaterialCardView = view.findViewById(R.id.codeupce)
        val rssexpanded: MaterialCardView = view.findViewById(R.id.rssexpanded)
        val codepdf417: MaterialCardView = view.findViewById(R.id.codepdf417)
        btnopenweb.setOnClickListener {
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra(SearchManager.QUERY, resultado)
            requireContext().startActivity(intent)
        }

        btnviewhistory.setOnClickListener{
            val historyFragment = HistoryFragment()
            val manager = requireActivity().supportFragmentManager
            val fragmentTransaction = manager.beginTransaction()
            fragmentTransaction.replace(R.id.content_main, historyFragment, "historyFragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit()
            cerrarteclado(requireActivity())
        }
        btncopy.setOnClickListener{
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("text", resultado)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, R.string.copystring, Toast.LENGTH_LONG).show()
        }
        coderss14.setOnClickListener{ getscannercoderss14() }
        rssexpanded.setOnClickListener{ getscannerrssexpanded() }
        codepdf417.setOnClickListener{ getscannercodepdf417() }
        codeupce.setOnClickListener{ getscannercodeupce() }
        codeean8.setOnClickListener{ getscannercodeean8() }
        codeupca.setOnClickListener{ getscannercodeupca() }
        codeean13.setOnClickListener{ getscannercodeean13() }
        codeitf.setOnClickListener{ getscannercodeitf() }
        code39.setOnClickListener{ getscannercode39() }
        code93.setOnClickListener{ getscannercode93() }
        matrixcode.setOnClickListener{ getscannermatrixcode() }
        qrcode.setOnClickListener{ getscannerqrcode() }
        code128.setOnClickListener{ getscannercode128() }
        return view
    }

    private fun getscannercoderss14() {
        val intent = IntentIntegrator.forSupportFragment(this@FirstFragment)
        intent.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
        intent.setPrompt("SCANNER CODE RSS 14")
        intent.setCameraId(0)
        intent.setBeepEnabled(true)
        intent.setOrientationLocked(false)
        intent.setBarcodeImageEnabled(false)
        intent.initiateScan()
    }

    private fun getscannerrssexpanded() {
        val intent = IntentIntegrator.forSupportFragment(this@FirstFragment)
        intent.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
        intent.setPrompt("SCANNER CODE RSS EXPANDED")
        intent.setCameraId(0)
        intent.setBeepEnabled(true)
        intent.setOrientationLocked(false)
        intent.setBarcodeImageEnabled(false)
        intent.initiateScan()
    }

    private fun getscannercodepdf417() {
        val intent = IntentIntegrator.forSupportFragment(this@FirstFragment)
        intent.setDesiredBarcodeFormats(IntentIntegrator.PDF_417)
        intent.setPrompt("SCANNER CODE PDF 417")
        intent.setCameraId(0)
        intent.setBeepEnabled(true)
        intent.setOrientationLocked(false)
        intent.setBarcodeImageEnabled(false)
        intent.initiateScan()
    }

    private fun getscannercodeupce() {
        val intent = IntentIntegrator.forSupportFragment(this@FirstFragment)
        intent.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
        intent.setPrompt("SCANNER CODE UPC E")
        intent.setCameraId(0)
        intent.setBeepEnabled(true)
        intent.setOrientationLocked(false)
        intent.setBarcodeImageEnabled(false)
        intent.initiateScan()
    }

    private fun getscannercodeean8() {
        val intent = IntentIntegrator.forSupportFragment(this@FirstFragment)
        intent.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
        intent.setPrompt("SCANNER CODE EAN 8")
        intent.setCameraId(0)
        intent.setBeepEnabled(true)
        intent.setOrientationLocked(false)
        intent.setBarcodeImageEnabled(false)
        intent.initiateScan()
    }

    private fun getscannercodeupca() {
        val intent = IntentIntegrator.forSupportFragment(this@FirstFragment)
        intent.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
        intent.setPrompt("SCANNER CODE UPC A")
        intent.setCameraId(0)
        intent.setBeepEnabled(true)
        intent.setOrientationLocked(false)
        intent.setBarcodeImageEnabled(false)
        intent.initiateScan()
    }

    private fun getscannercodeean13() {
        val intent = IntentIntegrator.forSupportFragment(this@FirstFragment)
        intent.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
        intent.setPrompt("SCANNER CODE EAN 13")
        intent.setCameraId(0)
        intent.setBeepEnabled(true)
        intent.setOrientationLocked(false)
        intent.setBarcodeImageEnabled(false)
        intent.initiateScan()
    }

    private fun getscannercodeitf() {
        val intent = IntentIntegrator.forSupportFragment(this@FirstFragment)
        intent.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
        intent.setPrompt("SCANNER CODE ITF")
        intent.setCameraId(0)
        intent.setBeepEnabled(true)
        intent.setOrientationLocked(false)
        intent.setBarcodeImageEnabled(false)
        intent.initiateScan()
    }

    private fun getscannercode39() {
        val intent = IntentIntegrator.forSupportFragment(this@FirstFragment)
        intent.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
        intent.setPrompt("SCANNER CODE 39")
        intent.setCameraId(0)
        intent.setBeepEnabled(true)
        intent.setOrientationLocked(false)
        intent.setBarcodeImageEnabled(false)
        intent.initiateScan()
    }

    private fun getscannercode93() {
        val intent = IntentIntegrator.forSupportFragment(this@FirstFragment)
        intent.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
        intent.setPrompt("SCANNER CODE 93")
        intent.setCameraId(0)
        intent.setBeepEnabled(true)
        intent.setOrientationLocked(false)
        intent.setBarcodeImageEnabled(false)
        intent.initiateScan()
    }

    private fun getscannercode128() {
        val intent = IntentIntegrator.forSupportFragment(this@FirstFragment)
        intent.setDesiredBarcodeFormats(IntentIntegrator.CODE_128)
        intent.setPrompt("SCANNER CODE 128")
        intent.setCameraId(0)
        intent.setBeepEnabled(true)
        intent.setOrientationLocked(false)
        intent.setBarcodeImageEnabled(false)
        intent.initiateScan()
    }

    private fun getscannerqrcode() {
        val intent = IntentIntegrator.forSupportFragment(this@FirstFragment)
        intent.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        intent.setPrompt("SCANNER CODE QR")
        intent.setCameraId(0)
        intent.setBeepEnabled(true)
        intent.setOrientationLocked(false)
        intent.setBarcodeImageEnabled(false)
        intent.initiateScan()
    }

    private fun getscannermatrixcode() {
        val intent = IntentIntegrator.forSupportFragment(this@FirstFragment)
        intent.setDesiredBarcodeFormats(IntentIntegrator.DATA_MATRIX)
        intent.setPrompt("SCANNER CODE MATRIX")
        intent.setCameraId(0)
        intent.setBeepEnabled(true)
        intent.setOrientationLocked(false)
        intent.setBarcodeImageEnabled(false)
        intent.initiateScan()
    }

    //PARA PEDIR LOS PERMISOS NECESARIOS DE LA APLICACION
    private fun solicitaPermisosVersionesSuperiores(): Boolean {
        //validamos si los permisos ya fueron aceptados
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requireContext().checkSelfPermission(permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                return true
            }
        }
        if (shouldShowRequestPermissionRationale(permission.CAMERA)) {
            cargarDialogoRecomendacion()
        } else {
            requestPermissions(arrayOf(permission.CAMERA), MIS_PERMISOS)
        }
        return false //implementamos el que procesa el evento dependiendo de lo que se defina aqui
    }

    //SI NO ACEPTA LOS PERMISOS SE LE INFORMA AL USUARIO QUE LA APLICACION NO PODRÁ TENER UN BUEN FUNCIONAMIENTO
    private fun cargarDialogoRecomendacion() {
        val dialogo = AlertDialog.Builder(requireContext())
        dialogo.setTitle(R.string.permissdes)
        dialogo.setMessage(R.string.messajepermisor)
        dialogo.setPositiveButton(R.string.acepted) { dialogInterface, i -> requestPermissions(arrayOf(permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION), 100) }
        dialogo.show()
    }

    //recibe los resultados de el escanner o en la camara o en la galeria
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, R.string.cancelated, Toast.LENGTH_LONG).show()
            } else {
                codeview.visibility = View.VISIBLE
                btnopenweb.visibility = View.VISIBLE
                codetext.setText(result.contents.toString())
                resultado = result.contents.toString()
                val id_history = System.currentTimeMillis().toString()
                val time = 0
                val admin = AdminSQLiteOpenHelper(activity, "administracion", null, 1)
                val BaseDeDatos = admin.writableDatabase
                val registro = ContentValues()
                registro.put("id_history", id_history)
                registro.put("save_string", resultado)
                registro.put("date", time)
                BaseDeDatos.insert("history", null, registro) // este para insertar
                BaseDeDatos.close()
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

    companion object {
        private const val CARPETA_PRINCIPAL = "misImagenesApp/" //directorio principal
        private const val CARPETA_IMAGEN = "imagenes" //carpeta donde se guardan las fotos
        private const val DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN //ruta carpeta de directorios
        private const val COD_SELECCIONA = 10
        private const val COD_FOTO = 20
    }
}