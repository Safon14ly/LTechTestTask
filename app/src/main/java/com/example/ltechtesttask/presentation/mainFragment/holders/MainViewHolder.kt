package com.example.ltechtesttask.presentation.mainFragment.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.ltechtesttask.R
import com.example.ltechtesttask.domain.models.Element
import com.example.ltechtesttask.presentation.core.toSimpleDateFormatted

class MainViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    // Ссылка на получение изображения с Pinterest
    private val imageUrl = "https://i.pinimg.com/originals/ad/55/85/ad5585a69ad4a2c7b20c00573b0b7d6a.png"

    private var imageView: ImageView? = null
    private var titleTextView: TextView? = null
    private var descTextView: TextView? = null
    private var dateTextView: TextView? = null

    init {
        imageView = view.findViewById(R.id.vCover)
        titleTextView = view.findViewById(R.id.vTitle)
        descTextView = view.findViewById(R.id.vDescription)
        dateTextView = view.findViewById(R.id.vDate)
    }

    fun bind(element: Element) {
        titleTextView?.text = element.title
        descTextView?.text = element.text
        dateTextView?.text = element.date?.toSimpleDateFormatted()

        imageView?.let { imageView ->
            Glide
                .with(view)
                .load(imageUrl)
                .transform(CenterCrop())
                .placeholder(R.color.l_tech_light_blue)
                .into(imageView)
        }
    }
}