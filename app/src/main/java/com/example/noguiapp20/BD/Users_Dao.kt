package com.example.noguiapp20.BD


import androidx.room.*
import java.sql.RowId

@Dao
interface Users_Dao {

    @Insert
    fun savaUser(user : Users_Entity)

    @Query("select * from Users_Entity ")
    fun readUser() : List<Users_Entity>

    @Query("select *from Users_Entity WHERE idUsr = (:userId)")
    fun loadAllByIds(userId: Int): List<Users_Entity>

    @Delete
    fun delData (not : Users_Entity)

    @Update
    fun updateData(not : Users_Entity)


}