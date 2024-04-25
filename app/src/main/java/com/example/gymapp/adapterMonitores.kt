package com.example.gymapp
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.view.isInvisible


class adapterMonitores : BaseAdapter {
    var context: Context? = null
    var arrayList = ArrayList<MonitoresClass>()

    constructor(context: Context?, arrayList: MutableList<MonitoresClass>?) : super() {
        this.context = context
        this.arrayList = (arrayList as ArrayList<MonitoresClass>?)!!
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
        var row = inflater.inflate(R.layout.monitores_list,null)
        var nombre = row.findViewById<TextView>(R.id.nombremonitor)
        var horariolibre = row.findViewById<TextView>(R.id.horariolibremonitor)
        var reservada = row.findViewById<TextView>(R.id.reservadomonitor)


        nombre.text = arrayList[position].nombre
        horariolibre.text = arrayList[position].horariolibre
        //si la id de reserva es == 0 significa que no hay ninguna reserva, si tiene la id de alguien, esta reservada por esa persona.
        if (arrayList[position].idreserva != 0) {
            reservada.isInvisible = false;

        }
        else{reservada.isInvisible = true;}

        return row
    }

}