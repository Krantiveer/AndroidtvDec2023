package com.ott.tv.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ott.tv.adapter.MainFragmentListAdapter

import com.ott.tv.databinding.FragmentHomeBinding
import com.ott.tv.viewmodel.HomeViewModel


class HomeFragmentNewSideNav : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@HomeFragmentNewSideNav)[HomeViewModel::class.java]
            lifecycleOwner = this@HomeFragmentNewSideNav
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel?.setData()
        binding.viewModel?.data?.observe(requireActivity()) {
            it?.let {
                binding.listItems.apply {
                    adapter = MainFragmentListAdapter(requireActivity(),it)
                }
            }
        }
    }

}