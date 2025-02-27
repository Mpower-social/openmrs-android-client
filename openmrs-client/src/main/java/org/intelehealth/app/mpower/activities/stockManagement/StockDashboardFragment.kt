package org.intelehealth.app.mpower.activities.stockManagement

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.sqlite.db.SimpleSQLiteQuery
import dagger.hilt.android.AndroidEntryPoint
import org.intelehealth.app.mpower.activities.BaseFragment
import org.intelehealth.app.mpower.activities.dialog.AddStockInDialog
import org.intelehealth.app.mpower.activities.stockManagement.adapter.StockInAdapter
import com.openmrs.android_sdk.library.databases.entities.StockInModel
import org.intelehealth.app.mpower.activities.stockManagement.adapter.StockDashboardAdapter
import org.intelehealth.app.mpower.activities.stockManagement.adapter.StockListAdapter
import org.intelehealth.app.mpower.databinding.FragmentStockDashboardBinding
import org.intelehealth.app.mpower.databinding.FragmentStockInBinding
import org.intelehealth.app.mpower.databinding.FragmentStockListBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class StockDashboardFragment : BaseFragment() {
    private var _binding: FragmentStockDashboardBinding? = null
    private val binding get() = _binding!!
    private val calendar: Calendar = Calendar.getInstance()
    private lateinit var stockListAdapter: StockDashboardAdapter

    private val viewModel: StockInViewModel by viewModels()
    private var stockInList: ArrayList<StockInModel> = arrayListOf()

    companion object {
        fun newInstance(): StockDashboardFragment {
            return StockDashboardFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            stockListAdapter = StockDashboardAdapter()
            stockListRecyclerView.adapter = stockListAdapter

            btnFromStockInDate.setOnClickListener { pickDate(1) }
            btnToStockInDate.setOnClickListener { pickDate(2) }

            btnSearch.setOnClickListener { setSearch() }
            btnClearSearch.setOnClickListener { clearField() }

            fetchStockList()
            setupObserver()
        }
    }

    private fun clearField() {
        binding.itemProviderEditText.setText("")
        binding.fromStockInDateEditText.setText("")
        binding.toStockInDateEditText.setText("")
        fetchStockList()
    }

    private fun setSearch() {
        val fromDate = binding.fromStockInDateEditText.text.toString()
        val toDate = binding.toStockInDateEditText.text.toString()
        val itemProvider = binding.itemProviderEditText.text.toString()
        viewModel.setSearch(fromDate, toDate, "", itemProvider)
    }

    private fun gotoStockIn() {
        val intent = Intent(requireActivity(), StockInActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun fetchStockList() {
        viewModel.fetchStockList()
    }

    private fun setupObserver() {

        viewModel.stockList.observe(viewLifecycleOwner, Observer { result ->
            if (result.isNotEmpty()) {
                stockInList.clear()
                stockInList.addAll(result)
                updateAdapter(stockInList)

            }
        })

        viewModel.searchStockList.observe(viewLifecycleOwner, Observer { result ->
            if (result.isNotEmpty()) {
                stockInList.clear()
                stockInList.addAll(result)
                updateAdapter(stockInList)

            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateAdapter(stockInList: ArrayList<StockInModel>) {
        stockListAdapter.updateStockInList(stockInList)
        stockListAdapter.notifyDataSetChanged()
    }

    private fun pickDate(type: Int) {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                formatDateTime(type)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun formatDateTime(type: Int) {
        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        val formattedDate = sdf.format(calendar.time)
        if (type == 1) binding.fromStockInDateEditText.setText(formattedDate)
        else binding.toStockInDateEditText.setText(formattedDate)
    }


//    fun fetchReferredMembersOnRefresh(query: String) {
//        viewModel.fetchReferredMembersOnRefresh(query)
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}