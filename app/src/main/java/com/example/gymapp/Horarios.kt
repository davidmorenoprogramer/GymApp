package com.example.gymapp

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray


class Horarios : AppCompatActivity() {

    var listView : ListView? = null
    private val list: MutableList<HorarioClass> = ArrayList()
    //var list : ArrayList<HorarioClass>? = null
    var adapterhorarios : adapterHorarios? = null
    var idUsuario: String? = "0";
    var ip = "localhost"
    val urlHorarios = "http://$ip/gymApp/GetHorarios.php"
    var jsonObj: JSONArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horarios)
        var id = intent.extras!!.getString("id")
        listView = findViewById(R.id.horariosList)

        this.idUsuario = id
        getHorarios()
    }

    fun getHorarios(){
        //Toma una lista de los horarios.

        val queue: RequestQueue = Volley.newRequestQueue(this)
        val resultadoPost = object : StringRequest(Request.Method.POST, urlHorarios,
            Response.Listener<String> { response ->
                //Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                jsonObj = JSONArray(response) //lo transformo en json para poder inflarlo en un adapter de horarios.

               for (i in 0 ..jsonObj!!!!.length() - 1){ //menos 1 por que el id en la base de datos empieza por uno y los array en 0

                  val horario= HorarioClass(jsonObj!!.getJSONObject(i).get("entrada").toString(),jsonObj!!.getJSONObject(i).get("salida").toString(), jsonObj!!.getJSONObject(i).get("dia").toString());
                   this.list.add(i,horario)

                   //creo cada clase y lo añado un Array de la clase horarios.
                }

                //inflo el adapter
               adapterhorarios = adapterHorarios(this,this.list);
                //lo añado a la vista.
                listView!!.adapter = adapterhorarios

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