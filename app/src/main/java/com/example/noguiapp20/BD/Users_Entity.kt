package com.example.noguiapp20.BD

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Users_Entity {

    @PrimaryKey(autoGenerate = true)
    var idUsr : Int? = null

    @ColumnInfo(name = "NOMBRE")
    var nombre : String = ""

    @ColumnInfo(name = "EMAIL")
    var email : String = ""

    @ColumnInfo(name = "PASS")
    var pass : String = ""

    @ColumnInfo(name = "DESCRIPTION")
    var description : String = ""

    @ColumnInfo(name = "ROL")
    var rol : String = ""
}