package com.example.samojlov_av_homework_module_15_number_10_koala.utils.message

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.samojlov_av_homework_module_15_number_10_koala.R
import com.example.samojlov_av_homework_module_15_number_10_koala.models.Message

class MessageAdapter(private val context: Context) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val messages = ArrayList<Message>()

    private var onMessageClickListener: OnMessageClickListener? = null

    interface OnMessageClickListener {
        fun onMessageClick(message: Message, position: Int)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Message>) {
        messages.clear()
        messages.addAll(newList)
        notifyDataSetChanged()
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.message_icon_profileIV)
        private val data: TextView = itemView.findViewById(R.id.dataMessageTV)
        private val messageOut: TextView = itemView.findViewById(R.id.messageTV)

        fun bind(message: Message) {
            if (message.icon != null) {
                icon.setImageResource(message.icon!!)
            } else {
                icon.setImageResource(R.drawable.profile_icon_base)
            }

            messageOut.text = message.message

            data.text = message.time
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MessageViewHolder {
        val viewHolder = MessageViewHolder(
            LayoutInflater.from(context).inflate(R.layout.message_item, parent, false)
        )
        return viewHolder
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messages[position]
        holder.bind(currentMessage)

        holder.itemView.setOnClickListener {
            if (onMessageClickListener != null) {
                onMessageClickListener!!.onMessageClick(currentMessage, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun setOnMessageClickListener(onMessageClickListener: OnMessageClickListener) {
        this.onMessageClickListener = onMessageClickListener
    }

}