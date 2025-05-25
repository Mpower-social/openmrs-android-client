/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package com.openmrs.android_sdk.library.dao

import androidx.room.*
import com.openmrs.android_sdk.library.databases.entities.GlobalLocationEntity
import com.openmrs.android_sdk.library.databases.entities.ProductModelEntity

/**
 * The interface Visit room dao.
 */
@Dao
interface GlobalLocationDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLocation(task: GlobalLocationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLocation(products: List<GlobalLocationEntity>)

    @Query("SELECT * FROM global_location")
    fun getAllLocation(): List<GlobalLocationEntity>

    @Query("SELECT * FROM global_location WHERE locationId = :id")
    fun getAllLocationByLocationId(id: Int): List<GlobalLocationEntity>

    @Query("SELECT * FROM global_location WHERE parentLocationId = :id")
    fun getAllLocationByParentLocationId(id: Int): List<GlobalLocationEntity>

    @Query("DELETE FROM global_location")
    fun deleteAll()


}