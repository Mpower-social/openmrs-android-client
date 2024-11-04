package org.intelehealth.app.mpower.activities.videoCall

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.openmrs.android_sdk.library.api.repository.LocationRepository
import com.openmrs.android_sdk.library.api.repository.PatientRepository
import com.openmrs.android_sdk.library.dao.PatientDAO
import com.openmrs.android_sdk.library.models.CallTokenModel
import com.openmrs.android_sdk.library.models.OperationType
import com.openmrs.android_sdk.library.models.Patient
import com.openmrs.android_sdk.library.models.SearchRequest
import com.openmrs.android_sdk.library.models.SearchUser
import com.openmrs.android_sdk.utilities.NetworkUtils
import com.openmrs.android_sdk.utilities.ToastUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import org.intelehealth.app.mpower.activities.BaseViewModel
import org.intelehealth.app.mpower.listeners.ItemClickListener
import org.intelehealth.app.mpower.utilities.FilterUtil
import rx.android.schedulers.AndroidSchedulers
import java.io.Serializable

import javax.inject.Inject


@HiltViewModel
class VideoCallsViewModel @Inject constructor(private val patientDAO: PatientDAO, private val patientRepository: PatientRepository) : BaseViewModel<List<Patient>>(),
    ItemClickListener {

    private val _callToken = MutableLiveData<String>()
    val callToken: LiveData<String> get() = _callToken

    var roomId: String = ""
    var selectedPatient: Patient = Patient()
    private val patientUUIDList = listOf("82f18b44-6814-11e8-923f-e9a88dcb533f", "0a09425b-9cea-4f04-a90d-200cda5edb1f")
    private val patientNameList = listOf("Super User", "Admin2")

    private val _patientList = MutableLiveData<List<Patient>>()
    val patientList: LiveData<List<Patient>> get() = _patientList

    fun addMembers() {
        setLoading()
        val pList = arrayListOf<Patient>()
        for (index in patientUUIDList.indices) {
            val patient = Patient()
            patient.uuid = patientUUIDList[index]
            patient.display = patientNameList[index]
            pList.add(patient)
        }
        _patientList.value = pList
    }

    fun fetchMembers() {
        setLoading()
        addSubscription(patientDAO.allPatients
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { patients: List<Patient> -> _patientList.value = patients },
                { setError(it, OperationType.MemberFetching) }
            ))
    }

    fun fetchMembers(query: String) {
        setLoading()
        addSubscription(patientDAO.allPatients
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { patients: List<Patient> ->
                    val filteredPatients = FilterUtil.getPatientsFilteredByQuery(patients, query)
                    setContent(filteredPatients)
                },
                { setError(it, OperationType.MemberSearching) }
            ))
    }

    fun fetchMembersOnRefresh(query: String) {

    }

    fun insertServerMembers(patients: List<Patient>) {

    }

    fun onGenerateToken(ctm: CallTokenModel) {
        setLoading()
        addSubscription(
            patientRepository.getGeneratedToken(ctm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _callToken.value = it.token },
                    { setError(it, OperationType.FetchingCallToken) }
                )
        )
    }

    override fun onItemClicked(item: Any?) {

    }

}