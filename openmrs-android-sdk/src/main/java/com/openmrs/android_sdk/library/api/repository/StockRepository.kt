package com.openmrs.android_sdk.library.api.repository

import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.google.gson.Gson
import com.openmrs.android_sdk.library.OpenmrsAndroid
import com.openmrs.android_sdk.library.api.responseModel.CurrentStockModel
import com.openmrs.android_sdk.library.api.responseModel.StockInModel
import com.openmrs.android_sdk.library.api.responseModel.StockListPostModel
import com.openmrs.android_sdk.library.dao.ProductDAO
import com.openmrs.android_sdk.library.dao.StockDAO
import com.openmrs.android_sdk.library.dao.StockDashboardDAO
import com.openmrs.android_sdk.library.databases.AppDatabase
import com.openmrs.android_sdk.library.databases.AppDatabaseHelper
import com.openmrs.android_sdk.library.databases.AppDatabaseHelper.createObservableIO
import com.openmrs.android_sdk.library.databases.entities.ProductModelEntity
import com.openmrs.android_sdk.library.databases.entities.StockDashboardModelEntity
import com.openmrs.android_sdk.library.databases.entities.StockListModelEntity
import com.openmrs.android_sdk.library.models.*
import com.openmrs.android_sdk.utilities.ApplicationConstants.API.FULL
import com.openmrs.android_sdk.utilities.NetworkUtils
import com.openmrs.android_sdk.utilities.NetworkUtils.isOnline
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import rx.Observable
import java.util.concurrent.Callable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepository @Inject constructor(private val stockDAO: StockDAO,
                                          private val stockDashboardDAO: StockDashboardDAO) : BaseRepository() {

    /**
     * Gets system property.
     *
     * @param systemProperty the system property
     * @return the system property
     */

    private val productDAO: ProductDAO =
        AppDatabase.getDatabase(OpenmrsAndroid.getInstance()!!.applicationContext).productRoomDAO()

//    fun insertStock(stockList: List<StockInModel>): Observable<Boolean> {
//        return AppDatabaseHelper.createObservableIO(Callable {
//            val insertIds = stockDAO.addStock(stockList)
//            return@Callable true
//        })
//    }

    fun saveStock(stockList: List<StockInModel>): Observable<ResponseBody> {
        return AppDatabaseHelper.createObservableIO(Callable {
            restApi.saveStock(stockList).execute().run {
                if (isSuccessful && body() != null) {
                    return@Callable body()!!
                } else {
                    throw Exception("Error fetching concepts: ${message()}")
                }
            }
        })
    }

    fun getCurrentStock(id: String): Observable<CurrentStockModel> {
        return AppDatabaseHelper.createObservableIO(Callable {
            restApi.getCurrentStock(id).execute().run {
                if (isSuccessful && body() != null) return@Callable body()!!
                else throw Exception("Error fetching concepts: ${message()}")
            }
        })
    }

    private fun fetchStockList(): List<StockListModelEntity> {
        val stockList = stockDAO.getAllStock().sortedBy { it.sid }
        return stockList
    }

    private fun searchStockListDB(query: SupportSQLiteQuery): List<StockListModelEntity> {
        val stockList = stockDAO.searchStock(query).sortedBy { it.sid }
        return stockList
    }

    private fun fetchStockDashboard(): List<StockDashboardModelEntity> {
        val stockList = stockDashboardDAO.getAllStock()
        return stockList
    }

    private fun searchStockDashboardDB(query: SupportSQLiteQuery): List<StockDashboardModelEntity> {
        val stockList = stockDashboardDAO.searchStockDashboard(query)
        return stockList
    }

    fun fetchProductList(): Observable<List<ProductModelEntity>> {
        return AppDatabaseHelper.createObservableIO(Callable {
            return@Callable productDAO.getAllProduct()
        })
    }

    fun getStockDashboard(stockListPostModel: StockListPostModel): Observable<List<StockDashboardModelEntity>> {
        return createObservableIO<List<StockDashboardModelEntity>>(Callable<List<StockDashboardModelEntity>> {
            try {
                val response =
                    restApi.stockDashboard(stockListPostModel).execute()
                if (response.isSuccessful) {
                    val body = response.body()!!.string()
                    val jsonArray = JSONArray(body)
                    stockDashboardDAO.deleteAll()
                    for (i in 0 until jsonArray.length()) {
                        try {
                            val stockItem = jsonArray.getJSONObject(i)
                            val stockDashboard =
                                Gson().fromJson(
                                    stockItem.toString(),
                                    StockDashboardModelEntity::class.java
                                )
                            stockDashboardDAO.addStockDashboard(stockDashboard)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }

                    return@Callable fetchStockDashboard()
                } else {
                    return@Callable fetchStockDashboard()
                    //  throw java.lang.Exception("syncPatient error: " + response.message())
                }
            } catch (ex: java.lang.Exception) {
                throw java.lang.Exception("syncPatient error: $ex")
            }
        })
    }

    fun searchDashboard(stockListPostModel: StockListPostModel, name : String): Observable<List<StockDashboardModelEntity>> {
        return createObservableIO<List<StockDashboardModelEntity>>(Callable<List<StockDashboardModelEntity>> {
            try {
                if (NetworkUtils.isOnline()) {
                    val response =
                        restApi.stockDashboard(stockListPostModel).execute()
                    if (response.isSuccessful) {
                        val body = response.body()!!.string()
                        val jsonArray = JSONArray(body)
                        stockDashboardDAO.deleteAll()
                        for (i in 0 until jsonArray.length()) {
                            try {
                                val stockItem = jsonArray.getJSONObject(i)
                                val stockDashboard =
                                    Gson().fromJson(
                                        stockItem.toString(),
                                        StockDashboardModelEntity::class.java
                                    )
                                stockDashboardDAO.addStockDashboard(stockDashboard)
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }

                        return@Callable fetchStockDashboard()
                    } else {
                        return@Callable fetchStockDashboard()
                        //  throw java.lang.Exception("syncPatient error: " + response.message())
                    }
                }
                else{
                    return@Callable searchStockDashboardDB(setSearch(stockListPostModel, name))
                }

            } catch (ex: java.lang.Exception) {
                throw java.lang.Exception("syncPatient error: $ex")
            }
        })
    }

    fun searchList(stockListPostModel: StockListPostModel, name : String): Observable<List<StockListModelEntity>> {
        return createObservableIO<List<StockListModelEntity>>(Callable<List<StockListModelEntity>> {
            try {
                if (NetworkUtils.isOnline()) {
                    val response =
                        restApi.stockList(stockListPostModel).execute()
                    if (response.isSuccessful) {
                        val body = response.body()!!.string()
                        val jsonArray = JSONArray(body)
                        stockDAO.deleteAll()
                        for (i in 0 until jsonArray.length()) {
                            try {
                                val stockItem = jsonArray.getJSONObject(i)
                                val productModel =
                                    Gson().fromJson(
                                        stockItem.toString(),
                                        StockListModelEntity::class.java
                                    )
                                stockDAO.addStock(productModel)
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }

                        return@Callable fetchStockList()
                    } else {
                        return@Callable fetchStockList()
                        //  throw java.lang.Exception("syncPatient error: " + response.message())
                    }
                }
                else{
                    return@Callable searchStockListDB(setSearch(stockListPostModel, name))
                }

            } catch (ex: java.lang.Exception) {
                throw java.lang.Exception("syncPatient error: $ex")
            }
        })
    }

    private fun setSearch(stockListPostModel: StockListPostModel, name: String) : SimpleSQLiteQuery {

        val stringBuilder = StringBuilder()
        val queryMain = "SELECT * FROM stock_table WHERE "

        if ((stockListPostModel.startDate.isNotEmpty() && stockListPostModel.endDate.isNotEmpty()) || name.isNotEmpty()) {

            if (stockListPostModel.startDate.isNotEmpty() && stockListPostModel.endDate.isNotEmpty()) {
                if (stringBuilder.length > 1) {
                    stringBuilder.append(" AND (stock_in_date BETWEEN '${stockListPostModel.startDate}' AND '${stockListPostModel.endDate}')")
                } else {
                    stringBuilder.append("(stock_in_date BETWEEN '$${stockListPostModel.startDate}' AND '${stockListPostModel.endDate}')")
                }
            }

            if (stockListPostModel.invoice.isNotEmpty()) {
                if (stringBuilder.length > 1) {
                    stringBuilder.append(" AND invoice LIKE '%${stockListPostModel.invoice}%'")
                } else {
                    stringBuilder.append("invoice LIKE '%${stockListPostModel.invoice}%'")
                }
            }

            if (name.isNotEmpty()) {
                if (stringBuilder.length > 1) {
                    stringBuilder.append(" AND (name LIKE '%${name}%' OR provider LIKE '%${name}%')")
                } else {
                    stringBuilder.append("(name LIKE '%${name}%' OR provider LIKE '%${name}%')")
                }
            }
        }

        val query = SimpleSQLiteQuery("${queryMain}${stringBuilder}")
        return query
    }

    fun getStockList(stockListPostModel: StockListPostModel): Observable<List<StockListModelEntity>> {
        return createObservableIO<List<StockListModelEntity>>(Callable<List<StockListModelEntity>> {
            try {
                val response =
                    restApi.stockList(stockListPostModel).execute()
                if (response.isSuccessful) {
                    val body = response.body()!!.string()
                    val jsonArray = JSONArray(body)
                    stockDAO.deleteAll()
                    for (i in 0 until jsonArray.length()) {
                        try {
                            val stockItem = jsonArray.getJSONObject(i)
                            val productModel =
                                Gson().fromJson(
                                    stockItem.toString(),
                                    StockListModelEntity::class.java
                                )
                            stockDAO.addStock(productModel)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }

                    return@Callable fetchStockList()
                } else {
                    return@Callable fetchStockList()
                  //  throw java.lang.Exception("syncPatient error: " + response.message())
                }
            } catch (ex: java.lang.Exception) {
                throw java.lang.Exception("syncPatient error: $ex")
            }
        })
    }


    fun getSystemProperty(systemProperty: String): Observable<SystemProperty> {
        return AppDatabaseHelper.createObservableIO(Callable {
            restApi.getSystemProperty(systemProperty, FULL).execute().run {
                if (isSuccessful && body() != null) return@Callable body()!!.results[0]
                else throw Exception("Error fetching concepts: ${message()}")
            }
        })
    }

    /**
     * Get concept answers by UUID
     *
     * @param uuid UUID of the concept
     * @return Observable ConceptAnswers
     */
    fun getConceptByUuid(uuid: String): Observable<ConceptAnswers> {
        return AppDatabaseHelper.createObservableIO(Callable {
            restApi.getConceptFromUUID(uuid).execute().run {
                if (isSuccessful && body() != null) return@Callable body()!!
                else throw Exception("Error fetching concepts by uuid: ${message()}")
            }
        })
    }


}
