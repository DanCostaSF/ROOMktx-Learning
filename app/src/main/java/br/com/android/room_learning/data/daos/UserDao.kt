package br.com.android.room_learning.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.android.room_learning.data.models.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT COUNT(uid) FROM user")  //contando quantos itens tem na tabela
    suspend fun getTotalItems() : Long

}