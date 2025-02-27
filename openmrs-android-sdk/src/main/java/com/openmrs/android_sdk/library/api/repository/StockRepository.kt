package com.openmrs.android_sdk.library.api.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.openmrs.android_sdk.library.dao.ConceptRoomDAO
import com.openmrs.android_sdk.library.dao.StockDAO
import com.openmrs.android_sdk.library.databases.AppDatabaseHelper
import com.openmrs.android_sdk.library.databases.entities.StockInModel
import com.openmrs.android_sdk.library.models.*
import com.openmrs.android_sdk.utilities.ApplicationConstants.API.FULL
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton
import java.util.concurrent.Callable

@Singleton
class StockRepository @Inject constructor(private val stockDAO: StockDAO) : BaseRepository() {

    /**
     * Gets system property.
     *
     * @param systemProperty the system property
     * @return the system property
     */

    fun insertStock(stockList: List<StockInModel>): Observable<Boolean> {
        return AppDatabaseHelper.createObservableIO(Callable {
            val insertIds = stockDAO.addStock(stockList)
            return@Callable true
//            stockDAO.addStock(stockList).execute().run {
//                if (isSuccessful && body() != null) return@Callable body()!!.results[0]
//                else throw Exception("Error fetching concepts: ${message()}")
//            }
        })
    }

    fun fetchStockList(): Observable<List<StockInModel>> {
        return AppDatabaseHelper.createObservableIO(Callable {
            return@Callable stockDAO.getAllStock()
        })
    }

    fun searchStockList(query: SupportSQLiteQuery): Observable<List<StockInModel>> {
        return AppDatabaseHelper.createObservableIO(Callable {
            return@Callable stockDAO.searchStock(query)
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
