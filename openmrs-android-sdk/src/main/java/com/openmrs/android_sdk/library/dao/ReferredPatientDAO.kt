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

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.openmrs.android_sdk.library.databases.entities.ReferredPatientEntity
import io.reactivex.Single

/**
 * The interface Patient room dao.
 */
@Dao
interface ReferredPatientDAO {
    /**
     * Add patient long.
     *
     * @param patientEntity the patient entity
     * @return the long
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPatient(patientEntity: ReferredPatientEntity): Long

    /**
     * Delete patient.
     *
     * @param id the id
     */
    @Query("DELETE FROM referred_patients WHERE id = :id")
    fun deletePatient(id: Long)

    /**
     * Update patient int.
     *
     * @param patientEntity the patient entity
     * @return the int
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePatient(patientEntity: ReferredPatientEntity): Int

    /**
     * Gets all patients.
     *
     * @return the all patients
     */
    @Query("SELECT * FROM referred_patients")
    fun getAllPatients(): Single<List<ReferredPatientEntity>>

    /**
     * Find patient by uuid single.
     *
     * @param uuid the uuid
     * @return the single
     */
    @Query("SELECT * FROM referred_patients WHERE uuid = :uuid")
    fun findPatientByUUID(uuid: String): Single<ReferredPatientEntity>

    /**
     * Find patient by query single.
     *
     * @param query the query
     * @return the single
     */
    @Query("SELECT * FROM referred_patients WHERE identifier like '%' || :query || '%' OR firstName like '%' || :query || '%' OR lastName like '%' || :query || '%'")
    fun findPatientByQuery(query: String): Single<List<ReferredPatientEntity>>

    /**
     * Find patient by id single.
     *
     * @param id the id
     * @return the single
     */
    @Query("SELECT * FROM referred_patients WHERE id = :id")
    fun findPatientByID(id: Long): Single<ReferredPatientEntity>
}