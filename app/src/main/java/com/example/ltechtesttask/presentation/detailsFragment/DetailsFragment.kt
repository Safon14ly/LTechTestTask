package com.example.ltechtesttask.presentation.detailsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.ltechtesttask.R
import com.example.ltechtesttask.databinding.DetailsFragmentBinding
import com.example.ltechtesttask.domain.models.Element
import com.example.ltechtesttask.presentation.app.AppConstants

class DetailsFragment: Fragment(R.layout.details_fragment) {

    private lateinit var binding: DetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем элемент для детального экрана
        val element = arguments?.getParcelable<Element>(AppConstants.ELEMENT) as Element

        with(binding) {
            Glide
                .with(requireActivity())
                .load(AppConstants.BASE_IMAGE_URL)
                .transform(CenterCrop())
                .placeholder(R.color.l_tech_light_blue)
                .into(vCover)

            vTitle.text = element.title
            vDescription.text = element.text
        }
    }
}