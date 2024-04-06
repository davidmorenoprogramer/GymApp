package com.example.gymapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class RegisterActivity : AppCompatActivity() {
    var txtEmail : EditText? = null
    var txtPass : EditText? = null
    var txtName : EditText? = null
    var ip = "localhost"

    val urlLogin = "http://$ip/login_gymApp/login.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        txtEmail = findViewById(R.id.email)
        txtPass = findViewById(R.id.pass)
        txtName = findViewById(R.id.name)
    }

    fun register(view: View){
        val url = "http://$ip/login_gymApp/insert.php"
        val queue: RequestQueue = Volley.newRequestQueue(this)

        val resultadoPost = object : StringRequest(Request.Method.POST,url,
            Response.Listener<String> { response ->
                Toast.makeText(this, "Usuario insertado existosamente", Toast.LENGTH_LONG).show()}
            , Response.ErrorListener { error ->
                Toast.makeText(this, "Error $error", Toast.LENGTH_LONG).show()
            })
        {
            override fun getParams(): MutableMap<String, String>? {
                val parametros = HashMap<String,String>()
                parametros.put("name", txtName?.text.toString())
                parametros.put("email", txtEmail?.text.toString())
                parametros.put("password", txtPass?.text.toString())
                return parametros
            }
        }
        queue.add(resultadoPost)

    }




}
