package com.example.appatemporal.framework.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.appatemporal.data.localdatabase.entities.Usuario
import com.example.appatemporal.databinding.ActivityRegisterBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.UserModel
import com.example.appatemporal.framework.viewModel.RegisterUserViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var repository: Repository
    private lateinit var gender: String
    private lateinit var role: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = Repository(this)
        val registerUserViewModel: RegisterUserViewModel by viewModels()

        val userUid = intent.getStringExtra("userUid").toString()

        val defaultRadioGenderBtn = binding.male.id
        val defaultRadioRoleBtn = binding.espectador.id

        val maleRadioBtn = binding.male.id
        val femaleRadioBtn = binding.female.id
        val otherRadioBtn = binding.other.id

        gender = binding.male.text.toString()

        binding.editGenderReg2.check(defaultRadioGenderBtn)

        binding.editGenderReg2.setOnCheckedChangeListener { radioGroup, i ->
            when(i) {
                maleRadioBtn -> gender = binding.male.text.toString()
                femaleRadioBtn -> gender = binding.female.text.toString()
                otherRadioBtn -> gender = binding.other.text.toString()
            }
        }

        val especRadioBtn = binding.espectador.id
        val orgRadioBtn = binding.organizador.id
        val ayudRadioBtn = binding.ayudante.id

        binding.registerBtn.isEnabled = false

        binding.editRoleReg2.check(defaultRadioRoleBtn)

        role = binding.espectador.text.toString()

        binding.editRoleReg2.setOnCheckedChangeListener { radioGroup, i ->
            when(i) {
                especRadioBtn -> role = binding.espectador.text.toString()
                orgRadioBtn -> role = binding.organizador.text.toString()
                ayudRadioBtn -> role = binding.ayudante.text.toString()
            }
        }

        binding.termsId.setOnClickListener {
            var intent = Intent(this@RegisterActivity, TermsCond::class.java)
            startActivity(intent)
        }
        binding.checkId.setOnCheckedChangeListener { compoundButton, b ->
            binding.registerBtn.isEnabled = b
        }

        binding.registerBtn.setOnClickListener {
            if (!binding.editnameReg2.text.isNullOrEmpty() && !binding.editlnameReg2.text.isNullOrEmpty()
                && !binding.editemailReg2.text.isNullOrEmpty() && !binding.editDateReg2.text.isNullOrEmpty()
            ) {
                val userUidSharedPref = getSharedPreferences("user", Context.MODE_PRIVATE)
                var userSharedPrefEdit = userUidSharedPref.edit()
                userSharedPrefEdit.putString("userUid", userUid)
                userSharedPrefEdit.putString("rol", role)
                userSharedPrefEdit.apply()

                Log.d("Role", role)

                val user = UserModel(binding.editnameReg2.text.toString(), binding.editlnameReg2.text.toString(),
                    binding.editemailReg2.text.toString(), binding.editDateReg2.text.toString(), gender)
                registerUserViewModel.addUser(userUid, user, role, repository)

                val localDbUser = Usuario(userUid, binding.editnameReg2.text.toString(), binding.editlnameReg2.text.toString(),
                    binding.editemailReg2.text.toString(), binding.editDateReg2.text.toString(), gender, role)
                registerUserViewModel.addUserLocalDB(localDbUser, repository)

                if(role == "Organizador"){
                    val intent = Intent(this, ActivityMainHomepageOrganizador::class.java)
                    startActivity(intent)
                }else{
                    val intent = Intent(this, ActivityMainHomepageEspectador::class.java)
                    startActivity(intent)
                }
            }else{
                Toast.makeText(this,"Faltan llenar campos", Toast.LENGTH_SHORT).show()
            }
//Comentario para pushear
        }
    }
}
