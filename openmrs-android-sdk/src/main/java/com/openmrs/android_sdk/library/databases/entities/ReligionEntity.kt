
package com.openmrs.android_sdk.library.databases.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "religion")
class ReligionEntity {

    @PrimaryKey
    @ColumnInfo(name = "uuid")
    var uuid: String = ""


    @ColumnInfo(name = "display")
    var display : String? = null


}