package com.example.ltechtesttask.ui.mainFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.ltechtesttask.R
import com.example.ltechtesttask.onTabSelected
import com.example.ltechtesttask.ui.mainFragment.adapters.MainAdapter
import com.example.ltechtesttask.viewModels.MainViewModel
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment: Fragment(R.layout.main_fragment) {

    private val viewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sortTabs: TabLayout = view.findViewById(R.id.vSortTabs)
        val swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.vSwipeRefresh)
        val mainRV: RecyclerView = view.findViewById(R.id.vElementsRV)

        if (savedInstanceState == null) {
            swipeRefresh.isRefreshing = true
        }

        val adapter = MainAdapter {
            viewModel.onItemClick(it)
        }

        sortTabs.getTabAt(0)?.select()

        viewModel.getElementsContainer()
            .observe(viewLifecycleOwner) { elementsContainer ->
                swipeRefresh.isRefreshing = true
                elementsContainer?.let { elements ->
                    adapter.submitList(elements)

                    // Сортировка полученных элементов по дате
                    sortTabs.onTabSelected { index ->
                        when(index) {
                            0 -> adapter.submitList(elements)
                            else -> adapter.submitList(elements.sortedBy { it.date })
                        }
                    }
                }
                swipeRefresh.isRefreshing = false
            }

        mainRV.adapter = adapter

        // Обновление элементов через SwipeRefreshLayout
        swipeRefresh.setOnRefreshListener {
            viewModel.getElements()
        }
    }
}