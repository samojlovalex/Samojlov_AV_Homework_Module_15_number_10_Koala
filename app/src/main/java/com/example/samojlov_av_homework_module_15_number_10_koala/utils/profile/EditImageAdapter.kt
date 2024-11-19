package com.example.samojlov_av_homework_module_15_number_10_koala.utils.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.samojlov_av_homework_module_15_number_10_koala.R

class EditImageAdapter(private val list: List<Int>) :
    RecyclerView.Adapter<EditImageAdapter.EditImageViewHolder>() {

    private var onEditImageClickListener: OnEditImageClickListener? = null

    interface OnEditImageClickListener {
        fun onEditImageClickListener(image: Int, position: Int)
    }

    class EditImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.edit_image_alert_dialogIV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditImageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.edit_image_alert_dialog_item, parent, false)
        return EditImageViewHolder(itemView)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: EditImageViewHolder, position: Int) {
        val icon = list[position]
        holder.image.setImageResource(icon)

        holder.itemView.setOnClickListener {
            if (onEditImageClickListener != null) {
                onEditImageClickListener!!.onEditImageClickListener(icon, position)
            }
        }
    }

    fun setIconClickListener(onEditImageClickListener: OnEditImageClickListener) {
        this.onEditImageClickListener = onEditImageClickListener
    }

}