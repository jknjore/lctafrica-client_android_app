package com.lctapp.lct.RoomDB.Entity

class LoginRepository(private val dao:LoginDao) {
    val users = dao.getLoginCredentials()
    suspend fun insert(user:UserEntity){
        return dao.insert(user)
    }
    suspend fun getUserName(userName:String):UserEntity?{
        return dao.getUsername(userName)
    }
}