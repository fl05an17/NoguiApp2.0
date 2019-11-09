package com.example.noguiapp20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import com.example.noguiapp20.BD.AppDB
import com.example.noguiapp20.BD.Users_Entity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var EtCorreo: EditText? = null
    var EtPass: EditText? = null
    var btnLogin: FloatingActionButton? = null
    var myAuth = FirebaseAuth.getInstance()
    var usuario:String? = null
    var bd: AppDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EtCorreo = findViewById(R.id.etEmailLog)
        EtPass = findViewById(R.id.EtpassLog)
        btnLogin = findViewById(R.id.btnLogin)

        bd = Room.databaseBuilder(applicationContext,AppDB :: class.java, "NoguiDB").build()

        Thread{

            usuario = ""
            bd?.UsuariosDao()?.readUser()?.forEach{
                if(it.email.isNotEmpty())
                    usuario = it.email
                Log.i("@DATOS",""""Email is : ${it.email} """")
                Log.i("@DATOS",""""Password is : ${it.pass} """")

            }
            //Log.i("@DATOS",""""usuario is : ${usuario} """")

            if(usuario?.isNotEmpty()!!){
                var intent = Intent(this, inicio::class.java)
                intent.putExtra("id", getUsernameFromEmail(usuario))
                startActivity(intent)
                this.finish()
            }

        }.start()



        btnLogin?.setOnClickListener {view->
            signIn(view, EtCorreo?.text.toString(),EtPass?.text.toString().trim())
        }

        EtRegistro.setOnClickListener {
            var intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }


    }


    private fun signIn(view: View, correo:String, contrasena:String){
        if(correo.equals("") && contrasena.equals("")){
            Toast.makeText(this, "Ha dejado campos vacios", Toast.LENGTH_LONG).show()
        }else {

                myAuth.signInWithEmailAndPassword(correo, contrasena)
                    .addOnCompleteListener(this, OnCompleteListener { task ->
                        if (task.isSuccessful) {
                              Thread{
                                  var User = Users_Entity()
                                  User.email = correo
                                  User.pass = contrasena
                                  bd?.UsuariosDao()?.savaUser(User)
                                 bd?.UsuariosDao()?.readUser()?.forEach{
                                      Log.i("@DATOS",""""Email is : ${it.email} """")
                                      Log.i("@DATOS",""""Password is : ${it.pass} """")
                                  }
                              }.start()
                            var intent = Intent(this, inicio::class.java)
                            intent.putExtra("id", getUsernameFromEmail(myAuth.currentUser?.email.toString()))
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Error: " + task.exception?.message, Toast.LENGTH_LONG).show()
                        }
                    })
            }
        }

    private fun getUsernameFromEmail(email: String?): String {
        return if (email!!.contains("@")) {
            email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        } else {
            email
        }
    }
    }





