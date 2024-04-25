package com.example.gymapp
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class adapterNoticias : BaseAdapter {
    var context: Context? = null
    var arrayList = ArrayList<NoticiasClass>()

    constructor(context: Context?, arrayList: MutableList<NoticiasClass>?) : super() {
        this.context = context
        this.arrayList = (arrayList as ArrayList<NoticiasClass>?)!!
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
        var row = inflater.inflate(R.layout.noticias_list,null)
        var noticia = row.findViewById<TextView>(R.id.noticia)

        noticia.text = arrayList[position].noticia

        return row
    }

}