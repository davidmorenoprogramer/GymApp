package com.example.gymapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
class Home : AppCompatActivity() {

    var nombreUsuario : TextView? = null;
    var idUsuario: String? = "0";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home);
        var nombre = intent.extras!!.getString("nombre")
        var id = intent.extras!!.getString("id")
        this.idUsuario = id
        nombreUsuario = this.findViewById(R.id.nombreUsuario);
        nombreUsuario!!.setText(nombre)



    }
    fun horariosActivity(view: View){
        val intent = Intent(this, Horarios::class.java);
        intent.putExtra("id",idUsuario);

        startActivity(intent);

    }

}