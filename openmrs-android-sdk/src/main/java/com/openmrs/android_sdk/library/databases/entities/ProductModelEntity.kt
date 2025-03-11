package com.openmrs.android_sdk.library.databases.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "product_table")
data class ProductModelEntity(

    @PrimaryKey
    @Expose @SerializedName("id")
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "name")
    @Expose @SerializedName("name")
    var name: String? = null,

    @ColumnInfo(name = "strength")
    @Expose @SerializedName("strength")
    var strength: String? = null
)