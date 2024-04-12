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
    val urlHorarios = "http://$ip/login_gymApp/GetHorarios.php"
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
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val resultadoPost = object : StringRequest(Request.Method.POST, urlHorarios,
            Response.Listener<String> { response ->
                //Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                jsonObj = JSONArray(response)

                //Toast.makeText(this, jsonObj!!.getJSONObject(0).get("idUsuario").toString(), Toast.LENGTH_LONG).show()

               for (i in 0 ..jsonObj!!!!.length() - 1){
                    //val obj = jsonObj!!.getJSONArray(i);
                    //Toast.makeText(this, obj.toString(), Toast.LENGTH_LONG).show()

                  val horario= HorarioClass(jsonObj!!.getJSONObject(i).get("entrada").toString(),jsonObj!!.getJSONObject(i).get("salida").toString(), jsonObj!!.getJSONObject(i).get("dia").toString());

                   this.list.add(i,horario)


                }
                //Toast.makeText(this, this.list.toString(), Toast.LENGTH_LONG).show()
               adapterhorarios = adapterHorarios(this,this.list);

                listView!!.adapter = adapterhorarios

                                      }
            , Response.ErrorListener { error ->
                Toast.makeText(this, "Error $error", Toast.LENGTH_LONG).show();
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val parametros = HashMap<String,String>()
                parametros.put("idUsuario", idUsuario.toString());

                return parametros
            }
        }
        queue.add(resultadoPost);
    }

}