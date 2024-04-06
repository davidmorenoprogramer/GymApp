package com.example.gymapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class LoginActivity : AppCompatActivity() {
    var txtEmail : EditText? = null
    var txtPass : EditText? = null
    var ip = "localhost"
    val urlLogin = "http://$ip/login_gymApp/login.php"
    var textregister : TextView? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        txtEmail = findViewById(R.id.email)
        txtPass = findViewById(R.id.pass)
        textregister = findViewById(R.id.clicktoregisterview)
    }

    fun goToRegister(view: View){
        startActivity(Intent(this, RegisterActivity::class.java));

    }



    fun login (view: View){

        val email = txtEmail?.text.toString().trim();
        val pass = txtPass?.text.toString().trim();
        val queue: RequestQueue = Volley.newRequestQueue(this)

        if (pass.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Algunos campos están vacíos", Toast.LENGTH_LONG).show()};

        else {


            val resultadoPost = object : StringRequest(Request.Method.POST, urlLogin,
                Response.Listener<String> { response ->
                    if (response.equals("ingresaste correctamente")) {

                        Toast.makeText(this, "$response", Toast.LENGTH_LONG).show();
                        startActivity(Intent(this, Home::class.java));


                    } else {
                        Toast.makeText(this, "$response", Toast.LENGTH_LONG).show();
                    }
                }, Response.ErrorListener { error ->
                    Toast.makeText(this, "Error $error", Toast.LENGTH_LONG).show();
                }) {
                override fun getParams(): MutableMap<String, String>? {
                    val parametros = HashMap<String,String>()
                    parametros.put("email", txtEmail?.text.toString());
                    parametros.put("password", txtPass?.text.toString());
                    return parametros
                }
            }
            queue.add(resultadoPost);


        }

    }

}

