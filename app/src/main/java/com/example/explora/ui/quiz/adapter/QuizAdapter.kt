package com.example.explora.ui.quiz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.explora.R
import com.example.explora.data.models.dummydata.QuizItem

class MyAdapter(private val quizItemList: List<QuizItem>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val numberTextView: TextView = view.findViewById(R.id.number)
        val checkmarkImageView: ImageView = view.findViewById(R.id.checkmark)
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
        holder.checkmarkImageView.visibility = if (quizItem.isChecked) View.VISIBLE else View.INVISIBLE
    }

    override fun getItemCount() = quizItemList.size
}
