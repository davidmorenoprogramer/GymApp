package com.example.gymapp

import java.util.Date

class HorarioClass {

    var entrada: String? = null;
    var salida: String? = null;
    var fecha: Date? = null;
    constructor(entrada:String, salida:String, fecha:Date){

        this.entrada = entrada
        this.salida = salida
        this.fecha = fecha
    }


}