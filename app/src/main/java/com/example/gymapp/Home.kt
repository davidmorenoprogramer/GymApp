package com.example.gymapp

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
class Home : AppCompatActivity() {

    var nombreUsuario : TextView? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home);
        var nombre = intent.extras!!.getString("nombre")
        nombreUsuario = this.findViewById(R.id.nombreUsuario);
        nombreUsuario!!.setText(nombre)



    }


}