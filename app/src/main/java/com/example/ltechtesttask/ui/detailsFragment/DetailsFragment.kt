package com.example.ltechtesttask.ui.detailsFragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.ltechtesttask.R
import com.example.ltechtesttask.api.models.Element

class DetailsFragment: Fragment(R.layout.details_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем элемент для детального экрана
        val element = arguments?.getParcelable<Element>(ELEMENT) as Element

        val imageView = view.findViewById<ImageView>(R.id.vCover)
        val titleTextView = view.findViewById<TextView>(R.id.vTitle)
        val descriptionTextView = view.findViewById<TextView>(R.id.vDescription)

        Glide
            .with(requireActivity())
            .load(IMAGE_URL)

            // В ответ на получение изображения с сервера приходит ошибка
            // "Remote server closed the connection before sending response header"
            // .load(Api.instance.getImageElement(element.image?.substring(1)))
            .transform(CenterCrop())
            .placeholder(R.color.l_tech_light_blue)
            .into(imageView)

        titleTextView.text = element.title
        descriptionTextView.text = element.text
    }

    companion object {
        const val ELEMENT = "element"

        // Ссылка на получение изображения с Pinterest
        const val IMAGE_URL = "https://i.pinimg.com/originals/ad/55/85/ad5585a69ad4a2c7b20c00573b0b7d6a.png"
    }
}