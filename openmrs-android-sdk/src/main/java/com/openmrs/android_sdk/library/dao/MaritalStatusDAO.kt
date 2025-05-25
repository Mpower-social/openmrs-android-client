
package com.openmrs.android_sdk.library.dao

import androidx.room.*
import com.openmrs.android_sdk.library.databases.entities.GlobalLocationEntity
import com.openmrs.android_sdk.library.databases.entities.MaritalStatusEntity
import com.openmrs.android_sdk.library.databases.entities.ProductModelEntity
import com.openmrs.android_sdk.library.databases.entities.ReligionEntity

@Dao
interface MaritalStatusDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMaritalStatus(task: MaritalStatusEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMaritalStatus(products: List<MaritalStatusEntity>)

    @Query("SELECT * FROM marital_status")
    fun getAllMaritalStatus(): List<MaritalStatusEntity>


    @Query("DELETE FROM marital_status")
    fun deleteAll()


}