package org.intelehealth.app.mpower.activities.dialog

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.openmrs.android_sdk.library.api.responseModel.StockInModel
import com.openmrs.android_sdk.library.databases.entities.ProductModelEntity
import org.intelehealth.app.mpower.R
import dagger.hilt.android.AndroidEntryPoint
import org.intelehealth.app.mpower.activities.stockManagement.StockInViewModel
import org.intelehealth.app.mpower.utilities.CustomSpinnerAdapter
import java.util.Calendar

@SuppressLint("ResourceType")
@AndroidEntryPoint
class AddStockInDialog(
    private val context: Context,
    private val stockInModel: StockInModel,
    private val onConfirm: (StockInModel) -> Unit
) : DialogFragment() {

    private lateinit var itemSpinner: Spinner
    private lateinit var receivedFromSpinner: Spinner
    private lateinit var edtCurrentStock: EditText
    private lateinit var edtQuantity: EditText
    private lateinit var edtExpire: EditText
    private lateinit var edtBatchNo: EditText
    private lateinit var dateBtn: ImageButton
    private lateinit var addBtn: AppCompatButton
    private var receivedFrom = ""
    private var item = ""
    private var productList = listOf<ProductModelEntity>()

    private val viewModel: StockInViewModel by viewModels()


    @SuppressLint("UseGetLayoutInflater", "NotifyDataSetChanged")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_stock_in, null)

            itemSpinner = dialogView.findViewById<Spinner>(R.id.spinnerItem)
            receivedFromSpinner = dialogView.findViewById<Spinner>(R.id.spinnerReceivedFrom)
            edtCurrentStock = dialogView.findViewById<EditText>(R.id.etCurrentStock)
            edtQuantity = dialogView.findViewById<EditText>(R.id.etQuantity)
            edtExpire = dialogView.findViewById<EditText>(R.id.expireEditText)
            edtBatchNo = dialogView.findViewById<EditText>(R.id.etBatchNo)
            dateBtn = dialogView.findViewById<ImageButton>(R.id.btnExpireDate)
            addBtn = dialogView.findViewById<AppCompatButton>(R.id.btnStockAdd)

            setReceivedFromSpinners()

            // setData(stockInModel)

            dateBtn.setOnClickListener {
                pickDate()
            }

            addBtn.setOnClickListener {
                if (isValid()) {
                    stockInModel.item = item
                    stockInModel.currentStock = edtCurrentStock.text.toString().toInt()
                    stockInModel.quantity = edtQuantity.text.toString().toInt()
                    stockInModel.expire = edtExpire.text.toString()
                    stockInModel.batchNo = edtBatchNo.text.toString()
                    stockInModel.receivedFrom = receivedFrom

                    onConfirm(stockInModel).also { dismiss() }
                }
            }

            setupObserver()

            builder.setView(dialogView)
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")

    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewModel.fetchProductList()
//        setupObserver()
//
//    }

    private fun setupObserver() {

        viewModel.productList.observe(viewLifecycleOwner){ result ->
            if (result.isNotEmpty()) {
                productList = result
                setItemSpinner(result)
                setData(stockInModel)
            }
        }
    }

    private fun setItemSpinner(result: List<ProductModelEntity>) {
        val adapter = CustomSpinnerAdapter(context, result)
        itemSpinner.adapter = adapter

        itemSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = result[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setData(stockInModel: StockInModel) {
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

    private fun pickDate() {
        // Get current date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Show DatePickerDialog
        val datePickerDialog =
            DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
                // Display selected date
                val selectedDate = "${selectedMonth + 1}/$selectedDay/$selectedYear"
                edtExpire.setText(selectedDate)
            }, year, month, day)

        datePickerDialog.show()
    }

    private fun setReceivedFromSpinners() {

        // item spinner
//        val itemAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, medicineList)
//        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        itemSpinner.adapter = itemAdapter
//
//        itemSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                item = parent.getItemAtPosition(position).toString()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {}
//        }

        // received from spinner

        val receivedAdapter =
            ArrayAdapter(context, android.R.layout.simple_spinner_item, receivedFromList)
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

    private fun isValid(): Boolean {

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

    private val receivedFromList = listOf(
        "CBHC HQ",
        "Local Procurement",
        "NGO",
        "Other"
    )


}