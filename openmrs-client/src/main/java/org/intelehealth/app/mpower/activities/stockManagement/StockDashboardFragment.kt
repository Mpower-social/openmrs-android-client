package org.intelehealth.app.mpower.activities.stockManagement

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.openmrs.android_sdk.library.api.responseModel.StockListPostModel
import com.openmrs.android_sdk.library.databases.entities.ProductModelEntity
import com.openmrs.android_sdk.library.databases.entities.StockDashboardModelEntity
import com.openmrs.android_sdk.library.databases.entities.StockListModelEntity
import dagger.hilt.android.AndroidEntryPoint
import org.intelehealth.app.mpower.activities.BaseFragment
import org.intelehealth.app.mpower.activities.stockManagement.adapter.ProductItemAdapter
import org.intelehealth.app.mpower.activities.stockManagement.adapter.StockDashboardAdapter
import org.intelehealth.app.mpower.databinding.FragmentStockDashboardBinding
import org.intelehealth.app.mpower.utilities.CustomSearchAdapter
import org.intelehealth.app.mpower.utilities.CustomSpinnerAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class StockDashboardFragment : BaseFragment() {
    private var _binding: FragmentStockDashboardBinding? = null
    private val binding get() = _binding!!
    private val calendar: Calendar = Calendar.getInstance()
    private lateinit var stockListAdapter: StockDashboardAdapter
    private lateinit var productItemAdapter: ProductItemAdapter

    private val viewModel: StockInViewModel by viewModels()
    private var stockInList: ArrayList<StockDashboardModelEntity> = arrayListOf()
    private var productList = listOf<ProductModelEntity>()
    private var drugId = 0

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

            initItemAdapter()


            btnFromStockInDate.setOnClickListener { pickDate(1) }
            btnToStockInDate.setOnClickListener { pickDate(2) }

            btnSearch.setOnClickListener { setSearch() }
            btnClearSearch.setOnClickListener { clearField() }

            itemEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (text != null) {
                        if (text.isNotEmpty()) {
                            searchRecyclerView.visibility = View.VISIBLE
                            val searchList = getSearchList(text)
                            productItemAdapter.updateStockInList(searchList)
                            productItemAdapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            fetchStockDashboard()
            fetchProductList()
            setupObserver()
        }
    }

    private fun getSearchList(text: CharSequence): ArrayList<ProductModelEntity> {
        val list = ArrayList<ProductModelEntity>()

        productList.forEach { it ->
            if (it.name?.lowercase()?.contains(text.toString().lowercase()) == true) {
                list.add(it)
            }
        }

        return list
    }

    private fun initItemAdapter() {

        productItemAdapter = ProductItemAdapter(object : ProductItemAdapter.ItemClickListener {
            override fun onItemClick(item: ProductModelEntity, position: Int) {
                drugId = item.id!!
                binding.itemEditText.setText(item.name.toString())
                binding.searchRecyclerView.visibility = View.GONE
            }

        })

        binding.searchRecyclerView.adapter = productItemAdapter
    }

    private fun fetchProductList() {
        viewModel.fetchProductList()
    }

    private fun clearField() {
        binding.progressBar.visibility = View.VISIBLE
        binding.itemEditText.setText("")
        binding.fromStockInDateEditText.setText("")
        binding.toStockInDateEditText.setText("")
        drugId = 0
        fetchStockDashboard()
    }

    private fun setSearch() {
        binding.progressBar.visibility = View.VISIBLE
        val startDate = binding.fromStockInDateEditText.text.toString()
        val endDate = binding.toStockInDateEditText.text.toString()
        val name = binding.itemEditText.text.toString()
        viewModel.searchStockDashboard(StockListPostModel(drugId, startDate, endDate, ""), name)
    }

    private fun fetchStockDashboard() {
        viewModel.getStockDashboard(StockListPostModel())
    }

    private fun setupObserver() {

        viewModel.productList.observe(viewLifecycleOwner, Observer { result ->
            if (result.isNotEmpty()) {
                productList = result
            }
        })

        viewModel.stockDashboard.observe(viewLifecycleOwner, Observer { result ->
            binding.progressBar.visibility = View.GONE
            if (result.isNotEmpty()) {
                stockInList.clear()
                stockInList.addAll(result)
                updateAdapter(stockInList)

            }
        })

        viewModel.searchStockDashboard.observe(viewLifecycleOwner, Observer { result ->
            binding.progressBar.visibility = View.GONE
                stockInList.clear()
                stockInList.addAll(result)
                updateAdapter(stockInList)


        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateAdapter(stockInList: ArrayList<StockDashboardModelEntity>) {
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
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val formattedDate = sdf.format(calendar.time)
        if (type == 1) binding.fromStockInDateEditText.setText(formattedDate)
        else binding.toStockInDateEditText.setText(formattedDate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}