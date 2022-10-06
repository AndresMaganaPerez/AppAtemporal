package com.example.appatemporal.framework.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.appatemporal.databinding.ActivityMainBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.MainViewModel
import com.google.firebase.auth.FirebaseAuth


class Main : AppCompatActivity() {
    private var auth = FirebaseAuth.getInstance()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val repository = Repository(this)

        val mainViewModel : MainViewModel by viewModels()

        val userUid = getSharedPreferences("user", Context.MODE_PRIVATE)
                        .getString("userUid", "").toString()

        val userRol = getSharedPreferences("user", Context.MODE_PRIVATE)
                        .getString("rol", "").toString()

        Log.d("User Auth Successfully", userUid)

        Log.d("User role", "Este es el rol del usuario: $userRol")

        mainViewModel.getUserLocalDB(userUid, repository)

        mainViewModel.userData.observe(this, Observer {
            binding.textView2.text = "${it.nombre} ${it.apellido}"
            binding.textView3.text = it.rol
            Log.d("LocalDB", it.toString())
        })

        binding.logoutTemp.setOnClickListener {
            auth.signOut()
            val userUidSharedPref = getSharedPreferences("userUid", Context.MODE_PRIVATE)
            var sharedPrefEdit = userUidSharedPref.edit()
            sharedPrefEdit.remove("userUid")
            val intent = Intent(this, CheckIfLogged::class.java)
            startActivity(intent)
        }
    }
}