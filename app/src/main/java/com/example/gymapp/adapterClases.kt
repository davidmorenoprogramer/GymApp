package com.example.gymapp
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.view.isInvisible


class adapterClases : BaseAdapter {
    var context: Context? = null
    var arrayList = ArrayList<ClasesClass>()

    constructor(context: Context?, arrayList: MutableList<ClasesClass>?) : super() {
        this.context = context
        this.arrayList = (arrayList as ArrayList<ClasesClass>?)!!
    }


    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
        return arrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater;
        var row = inflater.inflate(R.layout.clases_list,null)
        var nombre = row.findViewById<TextView>(R.id.nombreclase)
        var lugar = row.findViewById<TextView>(R.id.lugar)
        var monitor = row.findViewById<TextView>(R.id.monitornombre)
        var horario = row.findViewById<TextView>(R.id.horarioclase)
        var aforo = row.findViewById<TextView>(R.id.aforo)
        var aforoMaximo = row.findViewById<TextView>(R.id.aforoMaximo)
        var reservada = row.findViewById<TextView>(R.id.reservado)


        nombre.text = arrayList[position].nombre
        lugar.text = arrayList[position].lugar
        monitor.text = arrayList[position].monitor
        horario.text = arrayList[position].horario
        aforo.text = arrayList[position].aforo
        aforoMaximo.text = arrayList[position].aforoMaximo
        if (arrayList[position].reservada == true) {
            reservada.isInvisible = false;

        }
        else{reservada.isInvisible = true;}

        return row
    }

}