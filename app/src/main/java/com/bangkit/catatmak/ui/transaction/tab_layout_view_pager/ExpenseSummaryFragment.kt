package com.bangkit.catatmak.ui.transaction.tab_layout_view_pager

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.catatmak.databinding.FragmentExpenseSummaryBinding
import com.bangkit.catatmak.ui.transaction.outcome.OutcomeActivity

class ExpenseSummaryFragment : Fragment() {

    private var _binding: FragmentExpenseSummaryBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExpenseSummaryBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnDetailSummary?.setOnClickListener {
            startActivity(Intent(requireActivity(), OutcomeActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}