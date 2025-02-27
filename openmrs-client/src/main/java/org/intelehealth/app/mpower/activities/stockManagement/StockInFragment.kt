package org.intelehealth.app.mpower.activities.stockManagement

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import org.intelehealth.app.mpower.activities.BaseFragment
import org.intelehealth.app.mpower.activities.dialog.AddStockInDialog
import org.intelehealth.app.mpower.activities.stockManagement.adapter.StockInAdapter
import com.openmrs.android_sdk.library.databases.entities.StockInModel
import org.intelehealth.app.mpower.databinding.FragmentStockInBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class StockInFragment : BaseFragment() {
    private var _binding: FragmentStockInBinding? = null
    private val binding get() = _binding!!
    private val calendar: Calendar = Calendar.getInstance()
    private lateinit var stockInAdapter: StockInAdapter

    private val viewModel: StockInViewModel by viewModels()
    private var stockInList: ArrayList<StockInModel> = arrayListOf()

    companion object {
        fun newInstance(): StockInFragment {
            return StockInFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            Log.d("xxx", "onViewCreated: ")
            stockInAdapter = StockInAdapter(object : StockInAdapter.ItemClickListener {
                override fun onEditClick(item: StockInModel, position: Int) {
                    Log.d("xxx", "onEditClick: " + item.itemName)
                    Log.d("xxx", "onEditClick: " + position)
                    openDialog(item, true, position)
                }

                override fun onDeleteClick(item: StockInModel, position: Int) {
                    stockInList.removeAt(position)
                    stockInAdapter.updateStockInList(stockInList)
                    stockInAdapter.notifyDataSetChanged()
                    Log.d("xxx", "onDeleteClick: " + item.itemName)
                    Log.d("xxx", "onDeleteClick: " + position)
                }

            })
            stockInRecyclerView.adapter = stockInAdapter

            btnStockAdd.setOnClickListener {
                openDialog(StockInModel(), false, 0)
            }

            btnStockSave.setOnClickListener {
                if (isValid()) {
                    val stockInDate = stockInDateEditText.text.toString()
                    val invoice = invoiceEditText.text.toString()

                    stockInList.forEach { item ->
                        item.stockInDate = stockInDate
                        item.invoice = invoice
                    }

                    insertStock(stockInList)
                }

            }

            btnStockInDate.setOnClickListener {
                pickDate()
            }

            setupObserver()


        }
    }

    private fun insertStock(stockInList: ArrayList<StockInModel>) {
        viewModel.insertStock(stockInList)
    }

    private fun openDialog(stockInModel: StockInModel, isEdit: Boolean, position: Int) {
        val dialog = AddStockInDialog(requireContext(), stockInModel,
            onConfirm = { result ->

                if (isEdit) {
                    updateAdapter(result, isEdit, position)

                } else {
                    updateAdapter(result, isEdit, position)
                }
            })
        dialog.show(childFragmentManager, "AddStockInDialog")
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateAdapter(result: StockInModel, isEdit: Boolean, position: Int) {
        if (isEdit) {
            stockInList[position] = result
            stockInAdapter.updateStockInList(stockInList)
            stockInAdapter.notifyDataSetChanged()

        } else {
            stockInList.add(result)
            stockInAdapter.updateStockInList(stockInList)
            stockInAdapter.notifyDataSetChanged()
        }

    }

    private fun pickDate() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                formatDateTime()
//                pickTime()
//                val selectedDate = "${selectedMonth + 1}/$selectedDay/$selectedYear"
//                //edtExpire.setText(selectedDate)
//                binding.stockInDateEditText.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun pickTime() {
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                formatDateTime()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false // `false` for 12-hour format with AM/PM
        )
        timePickerDialog.show()
    }

    private fun formatDateTime() {
        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        val formattedDate = sdf.format(calendar.time)
        binding.stockInDateEditText.setText(formattedDate)
    }


    private fun setupObserver() {

        viewModel.stockInsertStatus.observe(viewLifecycleOwner, Observer { result ->
            if (result) {
                Toast.makeText(requireContext(), "Save Successfully", Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            }
        })
    }

    private fun isValid(): Boolean {
        if (binding.stockInDateEditText.text.toString().isEmpty()) {
            Toast.makeText(requireContext(), "Please enter stock in date", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (binding.invoiceEditText.text.toString().isEmpty()) {
            Toast.makeText(requireContext(), "Please enter invoice number", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (stockInList.isEmpty()) {
            Toast.makeText(requireContext(), "Please add stock item", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        return true
    }

//    fun fetchReferredMembersOnRefresh(query: String) {
//        viewModel.fetchReferredMembersOnRefresh(query)
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}