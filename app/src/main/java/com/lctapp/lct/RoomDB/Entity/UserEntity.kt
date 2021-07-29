package com.lctapp.lct.RoomDB.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity(tableName = "Login_Credentials")
    data class UserEntity(
        @PrimaryKey (autoGenerate = false)
        var userId : Int =0,
        @ColumnInfo(name = "user_name")
        val username : String? = null,
        @ColumnInfo(name = "password")
        val password : String? = null
    )