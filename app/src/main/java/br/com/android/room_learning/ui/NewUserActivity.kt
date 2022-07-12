package br.com.android.room_learning.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.android.room_learning.data.AppDatabase
import br.com.android.room_learning.data.daos.UserDao
import br.com.android.room_learning.data.models.User
import br.com.android.room_learning.databinding.ActivityNewUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewUserBinding
    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = AppDatabase.getInstance(this)
        userDao = database.userDao()
    }

    override fun onStart() {
        super.onStart()

        binding.btnSave.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {

                val result = saveUser(
                    binding.edtFirstName.text.toString(),
                    binding.edtLastName.text.toString()
                )

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@NewUserActivity,
                        if (result) "User saved!" else "Error trying to save user",
                        Toast.LENGTH_SHORT
                    ).show()

                    if (result)
                        finish()
                }
            }
        }
    }

    private suspend fun saveUser(firstName: String, lastName: String): Boolean {

        if (firstName.isBlank() || firstName.isEmpty())
            return false

        if (lastName.isBlank() || lastName.isEmpty())
            return false

        userDao.insertUser(User(firstName, lastName))
        print("alo")

        return true

    }
}