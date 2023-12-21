package com.example.explora.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.explora.R
import com.example.explora.data.model.DataItem
import com.example.explora.ui.DetailQuizActivity

class MyAdapter(private var quizItemList: List<DataItem?>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val numberTextView: TextView = view.findViewById(R.id.number)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.quiz_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val quizItem = quizItemList[position]
        holder.numberTextView.text = quizItem?.id.toString()

        holder.view.setOnClickListener {
            val intent = Intent(holder.view.context, DetailQuizActivity::class.java).apply {
                putExtra("pertanyaan", quizItem?.pertanyaan)
                putExtra("jawaban_benar", quizItem?.jawabanBenar)
            }
            holder.view.context.startActivity(intent)
        }
    }

    fun updateData(newData: List<DataItem?>) {
        this.quizItemList = newData
        notifyDataSetChanged()
    }

    override fun getItemCount() = quizItemList.size
}
