package com.openmrs.android_sdk.library.databases.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "stock_table")
data class StockListModelEntity(

    @PrimaryKey
    @ColumnInfo(name = "sid")
    @Expose @SerializedName("sid")
    var sid: Int? = null,

    @ColumnInfo(name = "sn")
    @Expose @SerializedName("sn")
    var sn: Int? = null,

    @ColumnInfo(name = "name")
    @Expose @SerializedName("name")
    var name: String? = null,

    @ColumnInfo(name = "quantity")
    @Expose @SerializedName("quantity")
    var quantity: Int? = null,

    @ColumnInfo(name = "stock_in_date")
    @Expose @SerializedName("stock_in_date")
    var stock_in_date: String? = null,

    @ColumnInfo(name = "invoice")
    @Expose @SerializedName("invoice")
    var invoice: String? = null,

    @ColumnInfo(name = "creator")
    @Expose @SerializedName("creator")
    var creator: String? = null,

    @ColumnInfo(name = "username")
    @Expose @SerializedName("username")
    var username: String? = null,

    )