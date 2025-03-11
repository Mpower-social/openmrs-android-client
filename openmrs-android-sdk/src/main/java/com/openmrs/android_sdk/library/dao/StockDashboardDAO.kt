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
import androidx.sqlite.db.SupportSQLiteQuery
import com.openmrs.android_sdk.library.databases.entities.StockDashboardModelEntity
import com.openmrs.android_sdk.library.databases.entities.StockListModelEntity

/**
 * The interface Visit room dao.
 */
@Dao
interface StockDashboardDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addStockDashboard(task: StockDashboardModelEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addStockDashboard(products: List<StockDashboardModelEntity>): List<Long>

    @Query("SELECT * FROM stock_dashboard_table")
    fun getAllStock(): List<StockDashboardModelEntity>

    @RawQuery
    fun searchStockDashboard(query: SupportSQLiteQuery): List<StockDashboardModelEntity>

    @Query("DELETE FROM stock_dashboard_table")
    fun deleteAll()


}