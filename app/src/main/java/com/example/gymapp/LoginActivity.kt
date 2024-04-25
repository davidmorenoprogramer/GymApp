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
import org.json.JSONObject





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
        //hago un trim para eliminar los caracteres no deseados.
        val email = txtEmail?.text.toString().trim();
        val pass = txtPass?.text.toString().trim();
        val queue: RequestQueue = Volley.newRequestQueue(this)

        ///si la contraseña o el email estan vacios muestra un error.
        if (pass.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Algunos campos están vacíos", Toast.LENGTH_LONG).show()};

        else {


            val resultadoPost = object : StringRequest(Request.Method.POST, urlLogin,
                Response.Listener<String> { response ->

                    if (response.equals("no pudiste ingresar")) {
                        //Toast.makeText(this, "$response", Toast.LENGTH_LONG).show();

                    } else {
                       // Toast.makeText(this, response, Toast.LENGTH_LONG).show();
                        val intent = Intent(this, Home::class.java);
                        val jsonObj: JSONObject = JSONObject(response)
                        ///hago un intent al menu activity para que pueda saber el nombre y la id del usuario.
                        intent.putExtra("nombre",jsonObj["name"].toString());
                        intent.putExtra("id",jsonObj["id"].toString());
                        startActivity(intent);

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


