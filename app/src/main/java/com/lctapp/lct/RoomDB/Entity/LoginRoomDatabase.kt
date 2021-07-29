package com.lctapp.lct.RoomDB.Entity

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper

@Database(entities = [UserEntity::class],version = 1,exportSchema = false)
abstract class LoginRoomDatabase: RoomDatabase() {
    abstract val loginDao:LoginDao
    companion object{

        @Volatile
        private var INSTANCE:LoginRoomDatabase?=null
        fun getInstance(context: Context):LoginRoomDatabase {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance =Room.databaseBuilder(context.applicationContext,LoginRoomDatabase::class.java,"User_login_credentials")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}