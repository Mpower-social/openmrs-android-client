package org.intelehealth.app.mpower.activities.stockManagement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.openmrs.android_sdk.library.api.repository.StockRepository
import com.openmrs.android_sdk.library.api.responseModel.CurrentStockModel
import com.openmrs.android_sdk.library.api.responseModel.StockInModel
import com.openmrs.android_sdk.library.api.responseModel.StockListPostModel
import com.openmrs.android_sdk.library.dao.StockDAO
import com.openmrs.android_sdk.library.databases.entities.ProductModelEntity
import com.openmrs.android_sdk.library.databases.entities.StockDashboardModelEntity
import com.openmrs.android_sdk.library.databases.entities.StockListModelEntity
import com.openmrs.android_sdk.library.models.Patient
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.ResponseBody
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

    private val _stockList = MutableLiveData<List<StockListModelEntity>>()
    val stockList: LiveData<List<StockListModelEntity>> get() = _stockList

    private val _stockDashboard = MutableLiveData<List<StockDashboardModelEntity>>()
    val stockDashboard: LiveData<List<StockDashboardModelEntity>> get() = _stockDashboard

    private val _productList = MutableLiveData<List<ProductModelEntity>>()
    val productList: LiveData<List<ProductModelEntity>> get() = _productList

    private val _currentStock = MutableLiveData<CurrentStockModel>()
    val currentStock: LiveData<CurrentStockModel> get() = _currentStock

    private val _saveStock = MutableLiveData<ResponseBody>()
    val saveStock: LiveData<ResponseBody> get() = _saveStock

    private val _searchStockList = MutableLiveData<List<StockListModelEntity>>()
    val searchStockList: LiveData<List<StockListModelEntity>> get() = _searchStockList

    private val _searchStockDashboard = MutableLiveData<List<StockDashboardModelEntity>>()
    val searchStockDashboard: LiveData<List<StockDashboardModelEntity>> get() = _searchStockDashboard

//    fun insertStock(stockList: List<StockInModel>) {
//        setLoading()
//        addSubscription(stockRepository.insertStock(stockList)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                _stockInsertStatus.value = it!!
//            }
//        )
//    }

    fun getStockDashboard(stockListPostModel: StockListPostModel) {
        setLoading()
        addSubscription(stockRepository.getStockDashboard(stockListPostModel)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _stockDashboard.value = it
            }
        )
    }

    fun getStockList(stockListPostModel: StockListPostModel) {
        setLoading()
        addSubscription(stockRepository.getStockList(stockListPostModel)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _stockList.value = it
            }
        )
    }

    fun fetchProductList() {
        setLoading()
        addSubscription(stockRepository.fetchProductList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _productList.value = it
            }
        )
    }

    fun saveStock(stockList: List<StockInModel>) {
        setLoading()
        addSubscription(stockRepository.saveStock(stockList)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _saveStock.value = it
            }
        )
    }

    fun getCurrentStock(id: String) {
        setLoading()
        addSubscription(stockRepository.getCurrentStock(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _currentStock.value = it
            }
        )
    }

     fun searchStockList(stockListPostModel: StockListPostModel, name:String) {
        setLoading()
        addSubscription(stockRepository.searchList(stockListPostModel, name)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _searchStockList.value = it
            }
        )
    }

    fun searchStockDashboard(stockListPostModel: StockListPostModel, name:String) {
        setLoading()
        addSubscription(stockRepository.searchDashboard(stockListPostModel, name)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _searchStockDashboard.value = it
            }
        )
    }


}