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

class Monitores : AppCompatActivity() {
    var listView : ListView? = null
    private val list: MutableList<MonitoresClass> = ArrayList()
    //var list : ArrayList<HorarioClass>? = null
    var adaptermonitores : adapterMonitores? = null
    var ip = "localhost"
    val urlMonitores = "http://$ip/login_gymApp/GetMonitores.php"
    var jsonObj: JSONArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitores)

        listView = findViewById(R.id.monitoresList)


        getMonitores()
    }

    fun getMonitores(){
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val resultadoPost = object : StringRequest(Request.Method.POST, urlMonitores,
            Response.Listener<String> { response ->
                //Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                jsonObj = JSONArray(response)

                for (i in 0 ..jsonObj!!!!.length() - 1){

                    val monitores= MonitoresClass(jsonObj!!.getJSONObject(i).get("nombre").toString(),jsonObj!!.getJSONObject(i).get("horariolibre").toString());
                    this.list.add(i,monitores)

                }

                adaptermonitores = adapterMonitores(this,this.list);

                listView!!.adapter = adaptermonitores

            }
            , Response.ErrorListener { error ->
                Toast.makeText(this, "Error $error", Toast.LENGTH_LONG).show();
            }) {

        }
        queue.add(resultadoPost);
    }
}