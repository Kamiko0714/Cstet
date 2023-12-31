package com.bangkit.catatmak.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.ListTransactionAdapter
import com.bangkit.catatmak.data.ResultState
import com.bangkit.catatmak.databinding.FragmentHomeBinding
import com.bangkit.catatmak.model.Transaction
import com.bangkit.catatmak.ui.ViewModelFactory
import com.bangkit.catatmak.ui.main.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    var isSheetShown = false

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        getTotalOutcomeToday()
        getAllFinancialsToday()
    }

    override fun onResume() {
        super.onResume()
        setupRecyclerView()
        getTotalOutcomeToday()
        getAllFinancialsToday()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding?.rvTransactions?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun getTotalOutcomeToday() {
       viewModel.getTotalOutcomeToday().observe(requireActivity()) { result ->
           if (result != null) {
               when (result) {
                   is ResultState.Loading -> {
                       showLoading(true, "total-outcome-today")
                   }

                   is ResultState.Success -> {
                       showLoading(false, "total-outcome-today")
                       val totalOutcome = result.data.summaryOutcomeIncomeData.totalToday
                       if (totalOutcome.isNotEmpty()) {
                         binding?.tvTotalOutcomeToday?.text = totalOutcome
                       } else {
                           binding?.tvTotalOutcomeToday?.text = getString(R.string.total_zero)
                       }
                   }

                   is ResultState.Error -> {
                       showLoading(false, "total-outcome-today")
                       showToast(result.error.toString())
                   }
               }
           }
       }
    }


    private fun getAllFinancialsToday() {
        viewModel.getAllFinancialsToday().observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true, "financials-today")
                    }

                    is ResultState.Success -> {
                        showLoading(false, "financials-today")
                        val financialsToday = result.data.data
                        if (financialsToday.isNotEmpty()) {
                            binding?.tvNoTransactionData?.visibility = View.GONE
                            val adapter = ListTransactionAdapter()
                            adapter.submitList(financialsToday)
                            binding?.rvTransactions?.adapter = adapter
                        } else {
                            binding?.tvNoTransactionData?.visibility = View.VISIBLE
                        }
                    }

                    is ResultState.Error -> {
                        showLoading(false, "financials-today")
                        showToast(result.error.toString())
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean, type: String) {
        if (type == "financials-today") {
            binding?.progressIndicatorTodaysFinancials?.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        } else {
            binding?.progressIndicatorFinancialsTotal?.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireActivity(), message, Toast.LENGTH_SHORT
        ).show()
    }

//    private fun setTransactionData() {
//        val listTransactionAdapter = ListTransactionAdapter()
//        binding?.rvTransactions?.adapter = listTransactionAdapter
//
//        listTransactionAdapter.setOnItemClickCallback(object :
//            ListTransactionAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: Transaction) {
//                if (!isSheetShown) {
//                    val modalBottomSheet = UpdateTransactionSheetFragment()
//                    modalBottomSheet.show(childFragmentManager, UpdateTransactionSheetFragment.TAG)
//                    isSheetShown = true
//                }
//            }
//        })
//    }

}