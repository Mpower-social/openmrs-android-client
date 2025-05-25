
package com.openmrs.android_sdk.library.dao

import androidx.room.*
import com.openmrs.android_sdk.library.databases.entities.BloodGroupEntity
import com.openmrs.android_sdk.library.databases.entities.GlobalLocationEntity
import com.openmrs.android_sdk.library.databases.entities.ProductModelEntity
import com.openmrs.android_sdk.library.databases.entities.ReligionEntity

@Dao
interface BloodGroupDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBloodGroup(task: BloodGroupEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBloodGroup(products: List<BloodGroupEntity>)

    @Query("SELECT * FROM blood_group")
    fun getAllBloodGroup(): List<BloodGroupEntity>


    @Query("DELETE FROM blood_group")
    fun deleteAll()


}