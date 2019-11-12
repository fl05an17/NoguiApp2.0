package com.example.noguiapp20.RecylcerHome

class Cultivos {

    var name: String? = null
    var image: String? = null


    constructor():this("",""){

    }

    constructor(name: String?, image: String?) {
        this.name = name
        this.image = image
    }
}