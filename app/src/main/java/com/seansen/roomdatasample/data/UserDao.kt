package com.seansen.roomdatasample.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.seansen.roomdatasample.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM users_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM users_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>
}