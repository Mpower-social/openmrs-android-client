package com.openmrs.android_sdk.library.api.repository

import com.google.gson.Gson
import com.openmrs.android_sdk.library.dao.ReferredPatientDAO
import com.openmrs.android_sdk.library.databases.AppDatabaseHelper.convert
import com.openmrs.android_sdk.library.databases.AppDatabaseHelper.createObservableIO
import com.openmrs.android_sdk.library.models.ReferredPatient
import com.openmrs.android_sdk.library.models.ReferredPatientResponse
import rx.Observable
import javax.inject.Inject

class ReferredPatientRepository @Inject constructor(private val referredPatientDAO: ReferredPatientDAO):
  BaseRepository() {
  fun savePatient(patient: ReferredPatient): Long {
    return referredPatientDAO.addPatient(convert(patient))
  }

  private fun savePatients(patients: List<ReferredPatient>): List<Long> {
    return patients.map { referredPatientDAO.addPatient(convert(it)) }.toList()
  }

  fun updatePatient(patient: ReferredPatient): Boolean {
    return convert(patient).run {
      referredPatientDAO.updatePatient(this) > 0
    }
  }

  fun deletePatient(patientId: Long) = referredPatientDAO.deletePatient(patientId)

  fun getAllPatients(): Observable<List<ReferredPatient>> {
    return referredPatientDAO.getAllPatients().blockingGet().map {
      convert(it)
    }.run {
      createObservableIO { this }
    }
  }

  fun findPatientByQuery(query: String): Observable<List<ReferredPatient>> {
    return referredPatientDAO.findPatientByQuery(query).blockingGet().map {
      convert(it)
    }.run {
      createObservableIO { this }
    }
  }

  fun fetchPatientsFromServerAndGetAll(wardId: Long, serverVersion: Long): List<ReferredPatient> {
    restApi.findPersonsByLocation(wardId, serverVersion).execute().let { response ->
      if(response.isSuccessful && response.body() != null) {
        val referredResponse = Gson().fromJson(response.body()!!.string(), ReferredPatientResponse::class.java)
        savePatients(referredResponse.persons)
      }

      return referredPatientDAO.getAllPatients().blockingGet().map {
        convert(it)
      }
    }
  }
}