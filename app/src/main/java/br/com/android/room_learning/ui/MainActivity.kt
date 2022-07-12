package br.com.android.room_learning.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import br.com.android.room_learning.data.AppDatabase
import br.com.android.room_learning.data.daos.UserDao
import br.com.android.room_learning.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding  : ActivityMainBinding
    private lateinit var database : AppDatabase
    private lateinit var userDao  : UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)
        userDao = database.userDao()
    }

    override fun onStart() {
        super.onStart()

        loadTotalUser()

        binding.btnNewUser.setOnClickListener {

            openNewUserActivity()

        }
    }

    private fun openNewUserActivity() {

        startActivity(Intent(this, NewUserActivity::class.java))

    }

    private fun loadTotalUser() {

        binding.tvInfoTotalUsers.text = "Carregando..."

        CoroutineScope(Dispatchers.IO).launch {
            val total = userDao.getTotalItems()
            withContext(Dispatchers.Main) {
                binding.tvInfoTotalUsers.text = "Total de usuários: $total"
            }
        }

    }

}