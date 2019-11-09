package com.example.noguiapp20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.noguiapp20.Objects.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_registro.*

class Registro : AppCompatActivity() {

    var aut = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        btnLogin.setOnClickListener {
            if(etPassReg.text.toString().isNotEmpty()&&etEmailReg.text.toString().isNotEmpty()&&etNombreReg.text.toString().isNotEmpty())
            {
                saveUsuario(etNombreReg.text.toString(),etEmailReg.text.toString(),etPassReg.text.toString())
                etNombreReg.setText("")
                etPassReg.setText("")
                etEmailReg.setText("")
            }else{
                Toast.makeText(this, "Faltan campos por completar", Toast.LENGTH_SHORT).show()
                etNombreReg.requestFocus()
            }

        }

    }

    private fun Login(Corr:String, password:String){
        aut.createUserWithEmailAndPassword(Corr,password).addOnCompleteListener(this) {task->
            if(task.isSuccessful){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(this, "El registro fallo.Intente Nuevamente", Toast.LENGTH_LONG
                ).show();
            }
        }
    }

    private fun saveUsuario(Nombre:String,Corr:String, password:String){

        //instanacia entre el nombre del objeto en la base de datos
        val myDatabase = FirebaseDatabase.getInstance().getReference("Usuerios")
        //identificador generado por la firebase a un objeto
        val UserId = myDatabase.push().key
        //genera un nuevo objeto con los datos ingresados
         val user = Usuario(Nombre,Corr,password)
        //ingresa el objeto con respecto de su identificador y retorna un toast cuando este termina
       myDatabase.child(UserId.toString()).setValue(user).addOnCompleteListener{
            Toast.makeText(this,"Usuario guardada",Toast.LENGTH_LONG).show()
        }

        Login(Corr, password)

        this.finish()
    }

}
