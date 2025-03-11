package org.intelehealth.app.mpower.activities.stockManagement

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.openmrs.android_sdk.library.api.responseModel.StockInModel
import com.openmrs.android_sdk.library.databases.entities.ProductModelEntity
import dagger.hilt.android.AndroidEntryPoint
import org.intelehealth.app.mpower.R
import org.intelehealth.app.mpower.activities.BaseFragment
import org.intelehealth.app.mpower.activities.stockManagement.adapter.StockInAdapter
import org.intelehealth.app.mpower.databinding.FragmentStockInBinding
import org.intelehealth.app.mpower.utilities.CustomSpinnerAdapter
import org.json.JSONObject
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
    private var productList = listOf<ProductModelEntity>()

    private lateinit var itemSpinner: Spinner
    private lateinit var receivedFromSpinner: Spinner
    private lateinit var edtCurrentStock: EditText
    private lateinit var edtQuantity: EditText
    private lateinit var edtExpire: EditText
    private lateinit var edtBatchNo: EditText

    private var receivedFrom = ""
    private var item = ""
    private var drugId = 0
    private var currentStock = -1

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

            initAdapter()

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

                    // insertStock(stockInList)
                    saveStock(stockInList)
                }

            }

            btnStockInDate.setOnClickListener {
                pickDate(true)
            }

            fetchProductList()
            setupObserver()
        }
    }

    private fun fetchProductList() {
        viewModel.fetchProductList()
    }

    private fun initAdapter() {
        stockInAdapter = StockInAdapter(object : StockInAdapter.ItemClickListener {
            override fun onEditClick(item: StockInModel, position: Int) {
                openDialog(item, true, position)
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onDeleteClick(item: StockInModel, position: Int) {
                stockInList.removeAt(position)
                stockInAdapter.updateStockInList(stockInList)
                stockInAdapter.notifyDataSetChanged()
            }

        })
        binding.stockInRecyclerView.adapter = stockInAdapter
    }

    private fun saveStock(stockInList: ArrayList<StockInModel>) {
        viewModel.saveStock(stockInList)
    }

    private fun openDialog(stockInModel: StockInModel, isEdit: Boolean, position: Int) {

        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_stock_in, null)
        builder.setView(dialogView)
        val dialog = builder.create()

        itemSpinner = dialogView.findViewById<Spinner>(R.id.spinnerItem)
        receivedFromSpinner = dialogView.findViewById<Spinner>(R.id.spinnerReceivedFrom)
        edtCurrentStock = dialogView.findViewById<EditText>(R.id.etCurrentStock)
        edtQuantity = dialogView.findViewById<EditText>(R.id.etQuantity)
        edtExpire = dialogView.findViewById<EditText>(R.id.expireEditText)
        edtBatchNo = dialogView.findViewById<EditText>(R.id.etBatchNo)
        val dateBtn = dialogView.findViewById<ImageButton>(R.id.btnExpireDate)
        val addBtn = dialogView.findViewById<AppCompatButton>(R.id.btnStockAdd)


        // set item spinner
        setItemSpinner()
        setReceivedFromSpinners()

        dateBtn.setOnClickListener {
            pickDate(false)
        }

        addBtn.setOnClickListener {
            if (isValidStockAdd()) {
                stockInModel.item = item
                stockInModel.drugId = drugId
                stockInModel.currentStock = edtCurrentStock.text.toString().toInt()
                stockInModel.quantity = edtQuantity.text.toString().toInt()
                stockInModel.expire = edtExpire.text.toString()
                stockInModel.batchNo = edtBatchNo.text.toString()
                stockInModel.receivedFrom = receivedFrom

                if (isEdit) {
                    updateAdapter(stockInModel, isEdit, position)

                } else {
                    updateAdapter(stockInModel, isEdit, position)
                }

                dialog.dismiss()

            }
        }

        if (isEdit) {
            setStockInData(stockInModel)
        }

        dialog.show()
    }

    private fun setStockInData(stockInModel: StockInModel) {
        if (stockInModel.currentStock > 0) {
            edtCurrentStock.setText("${stockInModel.currentStock}")
        }
        if (stockInModel.quantity > 0) {
            edtQuantity.setText("${stockInModel.quantity}")
        }
        if (stockInModel.expire.isNotEmpty()) {
            edtExpire.setText(stockInModel.expire)
        }
        if (stockInModel.batchNo.isNotEmpty()) {
            edtBatchNo.setText(stockInModel.batchNo)
        }

        if (stockInModel.item.isNotEmpty()) {
            val preselectedIndex = productList.indexOfFirst { it.name == stockInModel.item }
            if (preselectedIndex != -1) {
                itemSpinner.setSelection(preselectedIndex)
            }
        }
        if (stockInModel.receivedFrom.isNotEmpty()) {
            val preselectedIndex = receivedFromList.indexOf(stockInModel.receivedFrom)
            if (preselectedIndex != -1) {
                receivedFromSpinner.setSelection(preselectedIndex)
            }
        }
    }

    private fun setReceivedFromSpinners() {
        val receivedAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, receivedFromList)
        receivedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        receivedFromSpinner.adapter = receivedAdapter

        receivedFromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                receivedFrom = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setItemSpinner() {
        val adapter = CustomSpinnerAdapter(requireContext(), productList)
        itemSpinner.adapter = adapter

        itemSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                item = productList[position].name.toString()
                drugId = productList[position].id!!
                viewModel.getCurrentStock(productList[position].id.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
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

    private fun pickDate(isStockIn: Boolean) {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                formatDateTime(isStockIn)
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

//    private fun pickTime() {
//        val timePickerDialog = TimePickerDialog(
//            requireContext(),
//            { _, hourOfDay, minute ->
//                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
//                calendar.set(Calendar.MINUTE, minute)
//                formatDateTime()
//            },
//            calendar.get(Calendar.HOUR_OF_DAY),
//            calendar.get(Calendar.MINUTE),
//            false // `false` for 12-hour format with AM/PM
//        )
//        timePickerDialog.show()
//    }

    private fun formatDateTime(isStockIn: Boolean) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val formattedDate = sdf.format(calendar.time)

        if (isStockIn) binding.stockInDateEditText.setText(formattedDate)
        else edtExpire.setText(formattedDate)
    }


    private fun setupObserver() {

        viewModel.productList.observe(viewLifecycleOwner, Observer { result ->
            if (result.isNotEmpty()) {
                productList = result
            }
        })

        viewModel.currentStock.observe(viewLifecycleOwner, Observer { stock ->
            currentStock = stock.count!!

            if (currentStock != -1) {
                edtCurrentStock.setText(currentStock.toString())
            }
        })

        viewModel.saveStock.observe(viewLifecycleOwner, Observer { save ->

            val response = JSONObject(save.string())
            if (response.has("status")) {
                val status = response.get("status")
                val message = response.get("message")
                if (status == 200) {
                    Toast.makeText(requireContext(), message.toString(), Toast.LENGTH_SHORT).show()
                    requireActivity().finish()
                } else Toast.makeText(requireContext(), message.toString(), Toast.LENGTH_SHORT)
                    .show()
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

    private fun isValidStockAdd(): Boolean {

        if (item.isEmpty()) {
            Toast.makeText(context, "Please select an item", Toast.LENGTH_SHORT).show()
            return false
        }
        if (edtCurrentStock.text.toString().isEmpty()) {
            Toast.makeText(context, "Please enter current stock", Toast.LENGTH_SHORT).show()
            return false
        }

        if (edtQuantity.text.toString().isEmpty()) {
            Toast.makeText(context, "Please enter quantity", Toast.LENGTH_SHORT).show()
            return false
        }

        if (edtExpire.text.toString().isEmpty()) {
            Toast.makeText(context, "Please enter edtExpire date", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val receivedFromList = listOf(
        "CBHC HQ",
        "Local Procurement",
        "NGO",
        "Other"
    )
}