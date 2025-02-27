package org.intelehealth.app.mpower.activities.dialog

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
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
import org.intelehealth.app.mpower.R
import com.openmrs.android_sdk.library.databases.entities.StockInModel
import java.util.Calendar

@SuppressLint("ResourceType")
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


    @SuppressLint("UseGetLayoutInflater", "NotifyDataSetChanged")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_stock_in, null)

        itemSpinner = dialogView.findViewById<Spinner>(R.id.spinnerItem)
        receivedFromSpinner = dialogView.findViewById<Spinner>(R.id.spinnerReceivedFrom)
        edtCurrentStock = dialogView.findViewById<EditText>(R.id.etCurrentStock)
        edtQuantity = dialogView.findViewById<EditText>(R.id.etQuantity)
        edtExpire = dialogView.findViewById<EditText>(R.id.expireEditText)
        edtBatchNo = dialogView.findViewById<EditText>(R.id.etBatchNo)
        dateBtn = dialogView.findViewById<ImageButton>(R.id.btnStockInDate)
        addBtn = dialogView.findViewById<AppCompatButton>(R.id.btnStockAdd)
        setSpinners()

        setData(stockInModel)

        dateBtn.setOnClickListener {
            pickDate()
        }

        addBtn.setOnClickListener {
            if (isValid()) {
                stockInModel.itemName = item
                stockInModel.currentStock = edtCurrentStock.text.toString().toInt()
                stockInModel.stockIn = edtQuantity.text.toString().toInt()
                stockInModel.expireDate = edtExpire.text.toString()
                stockInModel.batchNo = edtBatchNo.text.toString()
                stockInModel.receiverFrom = receivedFrom

                onConfirm(stockInModel).also { dismiss() }
            }
        }


        builder.setView(dialogView)
        return builder.create()
    }

    private fun setData(stockInModel: StockInModel) {
        if (stockInModel.currentStock > 0) {
            edtCurrentStock.setText("${stockInModel.currentStock}")
        }
        if (stockInModel.stockIn > 0) {
            edtQuantity.setText("${stockInModel.stockIn}")
        }
        if (stockInModel.expireDate.isNotEmpty()) {
            edtExpire.setText(stockInModel.expireDate)
        }
        if (stockInModel.batchNo.isNotEmpty()) {
            edtBatchNo.setText(stockInModel.batchNo)
        }

        if (stockInModel.itemName.isNotEmpty()) {
            val preselectedIndex = medicineList.indexOf(stockInModel.itemName)
            if (preselectedIndex != -1) {
                itemSpinner.setSelection(preselectedIndex)
            }
        }
        if (stockInModel.receiverFrom.isNotEmpty()) {
            val preselectedIndex = receivedFromList.indexOf(stockInModel.receiverFrom)
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

    private fun setSpinners() {

        // item spinner
        val itemAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, medicineList)
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        itemSpinner.adapter = itemAdapter

        itemSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                item = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

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

    private val medicineList = listOf(
        "Zinc Dispersible Tablet 20 mg",
        "Vitamin A 2 Lac IU Capsule",
        "Vitamin-B-Complex Tablet (Thiamine (B1) 5 mg + Riboflavin 2 mg + Nicotinamide (B3) 20 mg + Pyridoxine (B6) 2 mg)",
        "Salbutamol Syrup (2 mg/5 ml) 60 ml",
        "Salbutamol Tablet 2 mg",
        "Penicillin-V Tablet 250 mg",
        "Paracetamol Tablet 500 mg",
        "Paracetamol Suspension (120 mg/5 ml) 60 ml",
        "Oral Rehydration Salt (for 0.5 litre solution)",
        "Neomycin & Bacitracin Skin Ointment 10 gm",
        "Metronidazole Tablet 400 mg",
        "Hyoscine Butyl-bromide Tablet 10 mg",
        "Gentian Violet 2% Topical Solution 10 ml",
        "Ferrous Fumarate & Folic Acid Tablet 200.40 mg (Ferrous Fumarate 400 mg + Folic Acid 0.40 mg)",
        "Calcium Lactate Tablet 300 mg",
        "Cotrimoxazole Tablet 960 mg (Sulfamethoxazole 800 mg + Trimethoprim 160 mg)",
        "Cotrimoxazole Tablet 120 mg (Sulfamethoxazole 100 mg + Trimethoprim 20 mg)",
        "Cotrimoxazole Tablet 120 mg (Sulfamethoxazole 100 mg + Trimethoprim 20 mg)",
        "Chlorpheniramine Maleate Tablet 4 mg",
        "Chlorpheniramine Maleate Syrup (2 mg/5 ml) 60 ml",
        "Chloramphenicol Eye Drop 0.5%, 10 ml",
        "Benzoic & Salicylic Acid Ointment 1 kg (Benzoic Acid 6% + Salicylic Acid 3%)",
        "Benzyl Benzoate Application 0.01% W/V 100 ml",
        "Antacid Chewable Tablet 650 mg (Aluminium Hydroxide 250 mg + Magnesium Hydroxide 400 mg)",
        "Amoxicillin 250 mg Capsule",
        "Amoxicillin 125 mg/5 ml Powder for Suspension 100 ml",
        "Amoxicillin 125 mg/1.25 ml Powder for Pediatric Drop 15 ml",
        "Albendazole 400 mg"
    )

}