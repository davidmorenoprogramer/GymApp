package com.example.gymapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class Noticias : AppCompatActivity() {
    var listView : ListView? = null
    private val list: MutableList<NoticiasClass> = ArrayList()
    //var list : ArrayList<HorarioClass>? = null
    var adapterNoticias : adapterNoticias? = null
    var idUsuario: String? = "0";
    var ip = "localhost"
    val urlnoticias = "http://$ip/gymApp/GetNoticias.php"
    var jsonObj: JSONArray? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)
        var id = intent.extras!!.getString("id")
        listView = findViewById(R.id.noticiasList)
        this.idUsuario = id
        getnoticias()
    }
    fun getnoticias(){
        //Toma una lista de las noticias.

        val queue: RequestQueue = Volley.newRequestQueue(this)
        val resultadoPost = object : StringRequest(Request.Method.POST, urlnoticias,
            Response.Listener<String> { response ->
                //Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                jsonObj = JSONArray(response) //lo transformo en json para poder inflarlo en un adapter de noticias.

                for (i in 0 ..jsonObj!!!!.length() - 1){ //menos 1 por que el id en la base de datos empieza por uno y los array en 0

                    val noticia= NoticiasClass(jsonObj!!.getJSONObject(i).get("noticia").toString());
                    this.list.add(i,noticia)

                    //creo cada clase y lo añado un Array de la clase noticias.
                }

                //inflo el adapter
                adapterNoticias = adapterNoticias(this,this.list);
                //lo añado a la vista.
                listView!!.adapter = adapterNoticias

            }
            , Response.ErrorListener { error ->
                Toast.makeText(this, "Error $error", Toast.LENGTH_LONG).show();
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val parametros = HashMap<String,String>()
                //envio el id de usuario para saber que horarios tiene dicho usuario.
                parametros.put("idUsuario", idUsuario.toString());

                return parametros
            }
        }
        queue.add(resultadoPost);
    }
}