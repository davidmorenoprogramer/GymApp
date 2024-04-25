package com.example.gymapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class Monitores : AppCompatActivity() {
    var listView : ListView? = null
    private val list: MutableList<MonitoresClass> = ArrayList()
    //var list : ArrayList<HorarioClass>? = null
    var adaptermonitores : adapterMonitores? = null
    var ip = "localhost"
    var idUsuario: String? = "0";
    val urlMonitores = "http://$ip/login_gymApp/GetMonitores.php"
    val urlSetReserva = "http://$ip/login_gymApp/SetReservaMonitor.php"
    val urlCancel = "http://$ip/login_gymApp/CancelReservaMonitor.php"
    var jsonObj: JSONArray? = null

    //este apartado tiene el mismo funcionamiento que las clases.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitores)
        var id = intent.extras!!.getString("id")
        listView = findViewById(R.id.monitoresList)
        this.idUsuario = id
        getMonitores()
        this.listView!!.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->


            DialogReserva(position + 1);

        })

    }

    private fun DialogReserva(position: Int){

        var builder = AlertDialog.Builder(this);
        if (list[position - 1].idreserva !=0){
            builder.setMessage("¿Quiere cancelar la reserva?");
            builder.setPositiveButton(
                "cancelar",
                DialogInterface.OnClickListener { dialog, id -> cancelReserva(position) })
            builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, id -> })

        }
        else {
            builder.setMessage("¿Quiere reservar esta clase?");
            builder.setPositiveButton(
                "reserva",
                DialogInterface.OnClickListener { dialog, id -> setReserva(position) })
            builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, id -> })
        }

        builder.show()
    }



    fun setReserva(position: Int){
        val queue: RequestQueue = Volley.newRequestQueue(this)


        val resultadoPost = object : StringRequest(Request.Method.POST,urlSetReserva,
            Response.Listener<String> { response ->


                getMonitores(); //dibujo primero los monitores.
                Toast.makeText(this, response, Toast.LENGTH_LONG).show()
            }


            , Response.ErrorListener { error ->
                Toast.makeText(this, "Error $error", Toast.LENGTH_LONG).show()
            })
        {
            override fun getParams(): MutableMap<String, String>? {
                val parametros = HashMap<String,String>()
                //doy estos parametros para que la base de datos sepa que id del monitor es y  añadir
                parametros.put("id", position.toString())
                parametros.put("idUsuario", idUsuario.toString())

                return parametros
            }


        }
        queue.add(resultadoPost)
    }


    fun cancelReserva(position: Int) {

        val queue: RequestQueue = Volley.newRequestQueue(this)


        val resultadoPost = object : StringRequest(Request.Method.POST, urlCancel,
            Response.Listener<String> { response ->
                Toast.makeText(this, "$response", Toast.LENGTH_LONG).show()

                getMonitores();

            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error $error", Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val parametros = HashMap<String, String>()
                parametros.put("id", position.toString())
                parametros.put("idUsuario", idUsuario.toString())

                return parametros
            }


        }
        queue.add(resultadoPost)
    }
    fun getMonitores(){
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val resultadoPost = object : StringRequest(Request.Method.POST, urlMonitores,
            Response.Listener<String> { response ->
                //Toast.makeText(this, response, Toast.LENGTH_LONG).show()

                jsonObj = JSONArray(response)
                if (list.size > 0){list.clear()}
                //Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                for (i in 0 ..jsonObj!!!!.length() - 1){

                    val monitores= MonitoresClass(jsonObj!!.getJSONObject(i).get("nombre").toString(),jsonObj!!.getJSONObject(i).get("horariolibre").toString(),Integer.parseInt(jsonObj!!.getJSONObject(i).get("idUsuarioReserva").toString()));
                    this.list.add(i,monitores)

                }

                adaptermonitores = adapterMonitores(this,this.list);

                listView!!.adapter = adaptermonitores

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