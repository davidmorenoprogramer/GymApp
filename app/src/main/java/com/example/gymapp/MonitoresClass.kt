package com.example.gymapp

class MonitoresClass {


    var nombre: String? = null;
    var horariolibre: String? = null;
    var idreserva: Int? = 0;

    constructor(nombre:String, horariolibre:String,idreserva:Int){


        this.nombre = nombre
        this.horariolibre = horariolibre
        this.idreserva = idreserva
    }
}