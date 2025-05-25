
package com.openmrs.android_sdk.library.databases.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.openmrs.android_sdk.library.models.OrderResource

@Entity(tableName = "marital_status")
class MaritalStatusEntity {

    @PrimaryKey
    @ColumnInfo(name = "uuid")
    var uuid: String = ""


    @ColumnInfo(name = "display")
    var display : String? = null


}