package com.example.mycv.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mycv.R
import com.example.mycv.extensions.newLineSeparatedString
import com.example.mycv.models.ExperienceInfo
import kotlinx.android.synthetic.main.cv_adapter_experiece_content.view.*

class ExperienceAdapter(
        var data: List<ExperienceInfo>
) : RecyclerView.Adapter<ExperienceAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
            MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cv_adapter_experiece_content, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.apply {
            data[position].let {
                Glide.with(context)
                        .load(it.imageUrl)
                        .into(logoImage)
                companyName.text = it.companyName
                roleName.text = it.role
                responsibilitiesTV.text = it.responsibilities.newLineSeparatedString
            }
        }
    }

}