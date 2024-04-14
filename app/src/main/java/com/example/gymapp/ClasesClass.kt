package com.example.gymapp

class ClasesClass {

    var nombre: String? = null;
    var lugar: String? = null;
    var monitor: String? = null;
    var horario: String? = null;
    var aforo: String? = null;
    var aforoMaximo: String? = null;

    constructor(nombre:String, lugar:String, monitor:String, horario:String, aforo:String, aforoMaximo:String){

        this.nombre = nombre
        this.lugar = lugar
        this.monitor = monitor
        this.horario = horario
        this.aforo = aforo
        this.aforoMaximo = aforoMaximo
    }

}