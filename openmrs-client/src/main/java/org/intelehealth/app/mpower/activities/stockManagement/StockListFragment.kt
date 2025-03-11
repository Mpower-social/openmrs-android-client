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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.openmrs.android_sdk.library.api.responseModel.StockListPostModel
import com.openmrs.android_sdk.library.databases.entities.ProductModelEntity
import com.openmrs.android_sdk.library.databases.entities.StockListModelEntity
import dagger.hilt.android.AndroidEntryPoint
import org.intelehealth.app.mpower.activities.BaseFragment
import org.intelehealth.app.mpower.activities.stockManagement.adapter.ProductItemAdapter
import org.intelehealth.app.mpower.activities.stockManagement.adapter.StockListAdapter
import org.intelehealth.app.mpower.databinding.FragmentStockListBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class StockListFragment : BaseFragment() {
    private var _binding: FragmentStockListBinding? = null
    private val binding get() = _binding!!
    private val calendar: Calendar = Calendar.getInstance()
    private lateinit var stockListAdapter: StockListAdapter

    private val viewModel: StockInViewModel by viewModels()
    private var stockList: ArrayList<StockListModelEntity> = arrayListOf()
    private var productList = listOf<ProductModelEntity>()
    private var drugId = 0
    private lateinit var productItemAdapter: ProductItemAdapter


    companion object {
        fun newInstance(): StockListFragment {
            return StockListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            stockListAdapter = StockListAdapter()
            stockListRecyclerView.adapter = stockListAdapter

            initItemAdapter()


            btnStockAdd.setOnClickListener { gotoStockIn() }

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

            fetchStockList()
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

    private fun clearField() {
        binding.progressBar.visibility = View.VISIBLE
        binding.itemEditText.setText("")
        binding.invoiceEditText.setText("")
        binding.fromStockInDateEditText.setText("")
        binding.toStockInDateEditText.setText("")
        drugId = 0
        fetchStockList()
    }

    private fun setSearch() {
        binding.progressBar.visibility = View.VISIBLE
        val startDate = binding.fromStockInDateEditText.text.toString()
        val endDate = binding.toStockInDateEditText.text.toString()
        val invoice = binding.invoiceEditText.text.toString()
        val name = binding.itemEditText.text.toString()

        viewModel.searchStockList(StockListPostModel(drugId, startDate, endDate, invoice), name)
    }

    private fun gotoStockIn() {
        val intent = Intent(requireActivity(), StockInActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun fetchStockList() {
        viewModel.getStockList(StockListPostModel())
    }

    private fun fetchProductList() {
        viewModel.fetchProductList()
    }

    private fun setupObserver() {

        viewModel.productList.observe(viewLifecycleOwner, Observer { result ->
            if (result.isNotEmpty()) {
                productList = result
            }
        })

        viewModel.stockList.observe(viewLifecycleOwner, Observer { result ->
            binding.progressBar.visibility = View.GONE

            if (result.isNotEmpty()) {
                stockList.clear()
                stockList.addAll(result)
                updateAdapter(stockList)

            }
        })

        viewModel.searchStockList.observe(viewLifecycleOwner, Observer { result ->
            binding.progressBar.visibility = View.GONE
            if (result.isNotEmpty()) {
                stockList.clear()
                stockList.addAll(result)
                updateAdapter(stockList)
            }
        })
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun updateAdapter(stockInList: ArrayList<StockListModelEntity>) {
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