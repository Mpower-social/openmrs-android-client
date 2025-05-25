
package com.openmrs.android_sdk.library.dao

import androidx.room.*
import com.openmrs.android_sdk.library.databases.entities.GlobalLocationEntity
import com.openmrs.android_sdk.library.databases.entities.ProductModelEntity
import com.openmrs.android_sdk.library.databases.entities.ReligionEntity

@Dao
interface ReligionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReligion(task: ReligionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReligion(products: List<ReligionEntity>)

    @Query("SELECT * FROM religion")
    fun getAllReligion(): List<ReligionEntity>


    @Query("DELETE FROM religion")
    fun deleteAll()


}