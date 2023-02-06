package com.example.ltechtesttask.presentation.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ltechtesttask.R
import com.example.ltechtesttask.databinding.MainFragmentBinding
import com.example.ltechtesttask.presentation.mainFragment.adapters.MainAdapter
import com.example.ltechtesttask.presentation.core.onTabSelected
import org.koin.android.ext.android.inject

class MainFragment: Fragment(R.layout.main_fragment) {

    private val viewModel: MainViewModel by inject()

    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            if (savedInstanceState == null) {
                vSwipeRefresh.isRefreshing = true
            }

            val adapter = MainAdapter {
                viewModel.onItemClick(it)
            }

            vSortTabs.getTabAt(0)?.select()

            viewModel.elementsData.observe(viewLifecycleOwner) { elementsContainer ->
                vSwipeRefresh.isRefreshing = true
                elementsContainer?.let { elements ->
                    adapter.submitList(elements)

                    // Сортировка полученных элементов по дате
                    vSortTabs.onTabSelected { index ->
                        when(index) {
                            0 -> adapter.submitList(elements)
                            else -> adapter.submitList(elements.sortedBy { it.date })
                        }
                    }
                }
                vSwipeRefresh.isRefreshing = false
            }

            vElementsRV.adapter = adapter

            // Обновление элементов через SwipeRefreshLayout
            vSwipeRefresh.setOnRefreshListener {
                viewModel.getElements()
            }
        }
    }
}