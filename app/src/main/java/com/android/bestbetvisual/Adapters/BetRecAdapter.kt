package com.android.bestbetvisual.Adapters

//import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.bestbetvisual.Models.BetModel
import com.android.bestbetvisual.R
import kotlinx.android.synthetic.main.item_row.view.*

class BetRecAdapter(var item: MutableList<BetModel>, var context: Context, val fragment_position: Int = 1): RecyclerView.Adapter<BetRecAdapter.Holder>() {
    var counter = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v:View = LayoutInflater.from(context).inflate(R.layout.item_row,parent,false)
        return Holder(v)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun addItems(items: MutableList<BetModel>) {
        val lastPos = item.size - 1
        item.addAll(items)
        notifyItemRangeInserted(lastPos, items.size)
    }
    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(item[position].teams, item[position].league, item[position].dated, item[position].time, item[position].odds, item[position].predition, item[position].score, item[position].success)
    }

    inner class Holder(itemView: View? ): RecyclerView.ViewHolder(itemView!!){

        fun bind(teams:  String, league: String, dated: String, time: String, odds: String, predition: String, score: String, success: String){
            itemView.odds?.text = odds
            itemView.teams?.text = teams
            itemView.league?.text = league
            itemView.date?.text = dated
            itemView.predition?.text = "Pred: $predition"
            itemView.time?.text = time

            when(score){
                "null"->itemView.score?.visibility = View.GONE
                else->itemView.score?.text = score
            }
            when(success.toLowerCase()){
                "true"->itemView.success?.setImageResource(R.drawable.success)
                "false"->itemView.success?.setImageResource(R.drawable.failure)
                else->itemView.success?.setImageResource(R.drawable.unknown)
            }

            if(counter%2!=0){//odd
                itemView.wrapper?.setBackgroundResource(R.drawable.it_bg)
            }else{//even
                itemView.wrapper?.setBackgroundResource(R.drawable.it_bg2)
            }
            counter++
        }
    }
}