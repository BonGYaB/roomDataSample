package com.seansen.roomdatasample.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.seansen.roomdatasample.model.User
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.google.common.truth.Truth.assertThat

@RunWith(JUnit4::class)
class UserDatabaseTest: TestCase() {
    private lateinit var db: UserDatabase
    private lateinit var dao: UserDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java).build()
        dao = db.userDao()
    }

    @After
    fun closeDB() {
        db.close()
    }

    // Suspend function 'addUser' should be called only from a coroutine or another suspend function
//    @Test
//    fun writeAndReadUser1() {
//        val user = User(3, "sen", "lasst", 18)
//        dao.addUser(user)
//    }

    @Test
    fun writeAndReadUser() = runBlocking {
        val user = User(1, "sen", "lasst", 18)
        assertThat(dao.addUser(user)).isEqualTo(true)
    }
}