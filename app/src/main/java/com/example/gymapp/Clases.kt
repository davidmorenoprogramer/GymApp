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

class Clases : AppCompatActivity() {

    var listView : ListView? = null
    private val list: MutableList<ClasesClass> = ArrayList()

    var adapterclases : adapterClases? = null
    var ip = "localhost"
    val urlMonitores = "http://$ip/login_gymApp/GetClases.php"
    var jsonObj: JSONArray? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clases)
        listView = findViewById(R.id.ClasesList)
        getclases()
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
                Toast.makeText(this, "Error $error", Toast.LENGTH_LONG).show();
            }) {

        }
        queue.add(resultadoPost);
    }
}