package com.example.noguiapp20.RecylcerHome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noguiapp20.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class ActivityHome : AppCompatActivity() {

    lateinit var mRecyclerView : RecyclerView
    lateinit var  mDataBase : DatabaseReference

    lateinit var show_progress:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mDataBase = FirebaseDatabase.getInstance().getReference().child("Cultivos")

        mRecyclerView = findViewById(R.id.listView)

        mRecyclerView.layoutManager = LinearLayoutManager(this)

        show_progress = findViewById(R.id.progress_bar)

        val option = FirebaseRecyclerOptions.Builder<Cultivos>().setQuery(mDataBase,Cultivos::class.java).build()

        val FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Cultivos,MyViewHolder>(option){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(this@ActivityHome).inflate(R.layout.list_cardview,parent,false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Cultivos) {
                val refid = getRef(position).key.toString()

                mDataBase.child(refid).addValueEventListener(object: ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        show_progress.visibility = if(itemCount == 0) View.VISIBLE else View.GONE

                        holder.mTitle.setText(model.name)
                        Picasso.get().load(model.image).into(holder.mImgage)
                    }

                })
            }

        }

        mRecyclerView.adapter = FirebaseRecyclerAdapter
        FirebaseRecyclerAdapter.startListening()

        //logRecyclerView()

    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var mTitle: TextView = itemView!!.findViewById<TextView>(R.id.Display_title)
        var mImgage: ImageView = itemView!!.findViewById(R.id.Display_img)
    }

    //View Holder
    //class CultivosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    //}
}
