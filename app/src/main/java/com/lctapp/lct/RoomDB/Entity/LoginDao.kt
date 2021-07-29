package com.lctapp.lct.RoomDB.Entity

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface LoginDao {

    @Insert
    suspend fun insert(userEntity: UserEntity)

    @Query("SELECT * FROM Login_Credentials ORDER BY user DESC")
     fun getLoginCredentials():LiveData<List<UserEntity>>

    @Query("DELETE FROM LOGIN_CREDENTIALS")
    suspend fun deleteAll():Int

  @Query("SELECT * FROM LOGIN_CREDENTIALS WHERE user_name LIKE :userName")
suspend fun getUsername(userName:String): UserEntity?
}