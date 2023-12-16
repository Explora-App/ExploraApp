package com.example.explora.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.explora.R
import com.example.explora.data.models.dummydata.QuizItem

class MyAdapter(private val quizItemList: List<QuizItem>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val numberTextView: TextView = view.findViewById(R.id.number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // inflate item layout di sini
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.quiz_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val quizItem = quizItemList[position]
        holder.numberTextView.text = quizItem.number.toString()

        // Tambahkan ini
//        holder.view.setOnClickListener {
//            val intent = Intent(holder.view.context, DetailQuizActivity::class.java)
//            holder.view.context.startActivity(intent)
//
//            Toast.makeText(holder.view.context, "Item ${quizItem.number} diklik", Toast.LENGTH_SHORT).show()
//        }

    }

    override fun getItemCount() = quizItemList.size
}
