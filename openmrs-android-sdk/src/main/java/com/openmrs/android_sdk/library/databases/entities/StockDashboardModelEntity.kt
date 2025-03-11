package com.openmrs.android_sdk.library.databases.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "stock_dashboard_table")
data class StockDashboardModelEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "sn")
    @Expose @SerializedName("sn")
    var sn: Int? = null,

    @ColumnInfo(name = "name")
    @Expose @SerializedName("name")
    var name: String? = null,

    @ColumnInfo(name = "currentstock")
    @Expose @SerializedName("currentstock")
    var currentstock: Int? = null,

    @ColumnInfo(name = "stockin")
    @Expose @SerializedName("stockin")
    var stockin: Int? = null,

    @ColumnInfo(name = "adjust_amount")
    @Expose @SerializedName("adjust_amount")
    var adjust_amount: Int? = null,

    @ColumnInfo(name = "stockout")
    @Expose @SerializedName("stockout")
    var stockout: Int? = null

    )