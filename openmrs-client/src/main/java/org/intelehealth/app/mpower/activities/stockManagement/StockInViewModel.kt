package org.intelehealth.app.mpower.activities.stockManagement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.openmrs.android_sdk.library.api.repository.StockRepository
import com.openmrs.android_sdk.library.dao.StockDAO
import com.openmrs.android_sdk.library.databases.entities.StockInModel
import com.openmrs.android_sdk.library.models.Patient
import dagger.hilt.android.lifecycle.HiltViewModel
import org.intelehealth.app.mpower.activities.BaseViewModel
import rx.android.schedulers.AndroidSchedulers

import javax.inject.Inject


@HiltViewModel
class StockInViewModel @Inject constructor(
    private val stockDAO: StockDAO,
    private val stockRepository: StockRepository
) : BaseViewModel<List<Patient>>() {


    private val _stockInsertStatus = MutableLiveData<Boolean>()
    val stockInsertStatus: LiveData<Boolean> get() = _stockInsertStatus

    private val _stockList = MutableLiveData<List<StockInModel>>()
    val stockList: LiveData<List<StockInModel>> get() = _stockList

    private val _searchStockList = MutableLiveData<List<StockInModel>>()
    val searchStockList: LiveData<List<StockInModel>> get() = _searchStockList

    fun insertStock(stockList: List<StockInModel>) {
        setLoading()
        addSubscription(stockRepository.insertStock(stockList)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _stockInsertStatus.value = it!!
            }
        )
    }

    fun fetchStockList() {
        setLoading()
        addSubscription(stockRepository.fetchStockList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _stockList.value = it
            }
        )
    }

    fun setSearch(fromDate: String, toDate: String, invoice: String, itemProvider: String) {

        val stringBuilder = StringBuilder()
        val queryMain = "SELECT * FROM stock_table WHERE "

        if ((fromDate.isNotEmpty() && toDate.isNotEmpty()) || itemProvider.isNotEmpty()) {

            if (fromDate.isNotEmpty() && toDate.isNotEmpty()) {
                if (stringBuilder.length > 1) {
                    stringBuilder.append(" AND (stockInDate BETWEEN '$fromDate' AND '${toDate}')")
                } else {
                    stringBuilder.append("(stockInDate BETWEEN '$fromDate' AND '${toDate}')")
                }
            }

            if (invoice.isNotEmpty()) {
                if (stringBuilder.length > 1) {
                    stringBuilder.append(" AND invoice LIKE '%$invoice%'")
                } else {
                    stringBuilder.append("invoice LIKE '%$invoice%'")
                }
            }

            if (itemProvider.isNotEmpty()) {
                if (stringBuilder.length > 1) {
                    stringBuilder.append(" AND (itemName LIKE '%${itemProvider}%' OR provider LIKE '%${itemProvider}%')")
                } else {
                    stringBuilder.append("(itemName LIKE '%${itemProvider}%' OR provider LIKE '%${itemProvider}%')")
                }
            }
        }

        val query = SimpleSQLiteQuery("${queryMain}${stringBuilder}")
        searchStockList(query)
    }

    private fun searchStockList(query: SupportSQLiteQuery) {
        setLoading()
        addSubscription(stockRepository.searchStockList(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _searchStockList.value = it
            }
        )
    }


    fun fetchReferredMembersOnRefresh(query: String) {
//        if (NetworkUtils.isOnline()) {
//            setLoading()
//            addSubscription(patientRepository.findReferredPatients(query)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    { patients: List<ReferredPatient> ->
////                        insertServerMembers(patients)
////                        setContent(patients)
//                    },
//                    { setError(it, OperationType.MemberFetching) }
//                )
//            )
//        }
    }


}