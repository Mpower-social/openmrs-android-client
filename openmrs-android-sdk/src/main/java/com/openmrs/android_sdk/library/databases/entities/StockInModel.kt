package com.openmrs.android_sdk.library.databases.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_table")
data class StockInModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "stockInDate")
    var stockInDate: String = "",

    @ColumnInfo(name = "invoice")
    var invoice: String = "",

    @ColumnInfo(name = "itemName")
    var itemName: String = "",

    @ColumnInfo(name = "currentStock")
    var currentStock: Int = 0,

    @ColumnInfo(name = "stockIn")
    var stockIn: Int = 0,

    @ColumnInfo(name = "expireDate")
    var expireDate: String = "",

    @ColumnInfo(name = "batchNo")
    var batchNo: String = "",

    @ColumnInfo(name = "receiverFrom")
    var receiverFrom: String = "",

    @ColumnInfo(name = "provider")
    var provider: String = "provider",

    @ColumnInfo(name = "syncStatus")
    var syncStatus: Int = 0,

)