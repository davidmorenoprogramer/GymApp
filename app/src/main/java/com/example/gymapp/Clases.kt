package com.example.gymapp

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AlertDialog.Builder
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray


class Clases : AppCompatActivity() {

    var listView : ListView? = null
    private val list: MutableList<ClasesClass> = ArrayList()
    var idUsuario: String? = "0"

    var adapterclases : adapterClases? = null
    var ip = "localhost"
    val urlMonitores = "http://$ip/login_gymApp/GetClases.php"
    val urlSetReserva = "http://$ip/login_gymApp/SetReserva.php"


    var jsonObj: JSONArray? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clases)
        listView = findViewById(R.id.ClasesList)
        idUsuario = intent.extras!!.getString("id")
        getclases()

        this.listView!!.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->


            DialogReserva(position + 1);

        })
    }

    private fun DialogReserva(position: Int){

        var builder = AlertDialog.Builder(this);
        builder.setMessage("¿Quiere reservar esta clase?");
        builder.setPositiveButton( "reserva" , DialogInterface.OnClickListener { dialog, id -> setReserva(position) })
        builder.setNegativeButton( "No" , DialogInterface.OnClickListener { dialog, id ->  })

        builder.show()
    }





    fun setReserva(position: Int){
        val queue: RequestQueue = Volley.newRequestQueue(this)


        val resultadoPost = object : StringRequest(Request.Method.POST,urlSetReserva,
            Response.Listener<String> { response ->
                Toast.makeText(this, "$response", Toast.LENGTH_LONG).show()

                getclases();
             }


            , Response.ErrorListener { error ->
                Toast.makeText(this, "Error $error", Toast.LENGTH_LONG).show()
            })
        {
            override fun getParams(): MutableMap<String, String>? {
                val parametros = HashMap<String,String>()
                parametros.put("id", position.toString())
                parametros.put("idUsuario", idUsuario.toString())

                return parametros
            }


        }
        queue.add(resultadoPost)
    }

    fun getclases(){
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val resultadoPost = object : StringRequest(Request.Method.POST, urlMonitores,
            Response.Listener<String> { response ->
                //Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                jsonObj = JSONArray(response)

                for (i in 0 ..jsonObj!!!!.length() - 1){

                    val clases= ClasesClass(jsonObj!!.getJSONObject(i).get("nombre").toString(),jsonObj!!.getJSONObject(i).get("lugar").toString(),jsonObj!!.getJSONObject(i).get("monitor").toString(),jsonObj!!.getJSONObject(i).get("horario").toString(),jsonObj!!.getJSONObject(i).get("aforo").toString(),jsonObj!!.getJSONObject(i).get("aforoMaximo").toString());
                    this.list.add(i,clases)

                }

                adapterclases = adapterClases(this,this.list);

                listView!!.adapter = adapterclases

            }
            , Response.ErrorListener { error ->
                makeText(this, "Error $error", Toast.LENGTH_LONG).show();
            }) {

        }
        queue.add(resultadoPost);
    }
}