
package com.openmrs.android_sdk.library.databases.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.openmrs.android_sdk.library.models.OrderResource

@Entity(tableName = "global_location")
class GlobalLocationEntity {

    @PrimaryKey
    @ColumnInfo(name = "uuid")
    var uuid: String = ""

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "description")
    var description: String = ""

    @ColumnInfo(name = "locationId")
    var locationId: Int? = null

    @ColumnInfo(name = "parentLocationId")
    var parentLocationId: Int? = null

    @ColumnInfo(name = "dateChanged")
    var dateChanged: String = ""

}