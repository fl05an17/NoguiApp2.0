package com.example.noguiapp20.BD

import androidx.room.Database
import androidx.room.RoomDatabase

@Database (entities = [(Users_Entity::class)],version = 1)
abstract class AppDB : RoomDatabase() {

    abstract fun UsuariosDao() : Users_Dao

}