package org.intelehealth.app.mpower.activities.syncedpatients

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.openmrs.android_sdk.library.api.repository.PatientRepository
import com.openmrs.android_sdk.library.dao.PatientDAO
import com.openmrs.android_sdk.library.dao.VisitDAO
import com.openmrs.android_sdk.library.models.OperationType
import com.openmrs.android_sdk.library.models.Patient
import com.openmrs.android_sdk.library.models.PatientIdentifier
import com.openmrs.android_sdk.library.models.Person
import com.openmrs.android_sdk.library.models.ReferredPatient
import com.openmrs.android_sdk.utilities.NetworkUtils
import com.openmrs.android_sdk.utilities.ToastUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.ResponseBody
import org.intelehealth.app.mpower.activities.BaseViewModel
import org.intelehealth.app.mpower.listeners.ItemClickListener
import org.intelehealth.app.mpower.models.NavDrawerItem
import org.intelehealth.app.mpower.resources.Constants
import org.intelehealth.app.mpower.utilities.FilterUtil
import rx.android.schedulers.AndroidSchedulers
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SyncedPatientsViewModel @Inject constructor(
    private val patientDAO: PatientDAO,
    private val visitDAO: VisitDAO,
    private val patientRepository: PatientRepository
) : BaseViewModel<List<Patient>>(),
    ItemClickListener {
    var drawerItems = arrayListOf<NavDrawerItem>()
    val drawerItemListAdapter: NavigationDrawerAdapter =
        NavigationDrawerAdapter(drawerItems, onItemClick = { navDrawer ->
            if (navDrawer.id == Constants.ITEM_FIND_PATIENT) {
                if (loadFindPatient.value == null)
                    loadFindPatient.value = true
                else {
                    loadFindPatient.value = loadFindPatient.value != true
                }
            } else if (navDrawer.id == Constants.ITEM_ADD_PATIENT) {
                if (loadAddPatient.value == null)
                    loadAddPatient.value = true
                else {
                    loadAddPatient.value = loadAddPatient.value != true
                }
            } else if (navDrawer.id == Constants.ITEM_ACTIVE_VISITS) {
                if (loadActiveVisits.value == null)
                    loadActiveVisits.value = true
                else {
                    loadActiveVisits.value = loadActiveVisits.value != true
                }
            } else if (navDrawer.id == Constants.ITEM_FORM_ENTRY) {
                if (loadFormEntry.value == null)
                    loadFormEntry.value = true
                else {
                    loadFormEntry.value = loadFormEntry.value != true
                }
            } else if (navDrawer.id == Constants.ITEM_MANAGE_PROVIDERS) {
                if (loadManageProviders.value == null)
                    loadManageProviders.value = true
                else {
                    loadManageProviders.value = loadManageProviders.value != true
                }
            } else if (navDrawer.id == Constants.ITEM_FIND_MEMBER) {
                if (loadMemberList.value == null)
                    loadMemberList.value = true
                else {
                    loadMemberList.value = loadMemberList.value != true
                }
            } else if (navDrawer.id == Constants.ITEM_REFERRED_MEMBER_LIST) {
                if (loadReferredMemberList.value == null)
                    loadReferredMemberList.value = true
                else {
                    loadReferredMemberList.value = loadReferredMemberList.value != true
                }
            } else if (navDrawer.id == Constants.ITEM_ADD_MEMBER) {
                if (loadAddMember.value == null)
                    loadAddMember.value = true
                else {
                    loadAddMember.value = loadAddMember.value != true
                }
            } else if (navDrawer.id == Constants.ITEM_VIDEO_CALL) {
                if (loadVideoCalls.value == null)
                    loadVideoCalls.value = true
                else {
                    loadVideoCalls.value = loadVideoCalls.value != true
                }
            } else if (navDrawer.id == Constants.ITEM_STOCK_IN) {
                if (loadStockIn.value == null)
                    loadStockIn.value = true
                else {
                    loadStockIn.value = loadStockIn.value != true
                }
            } else if (navDrawer.id == Constants.ITEM_STOCK_LIST) {
                if (loadStockList.value == null)
                    loadStockList.value = true
                else {
                    loadStockList.value = loadStockList.value != true
                }
            } else if (navDrawer.id == Constants.ITEM_STOCK_DASHBOARD) {
                if (loadStockDashboard.value == null)
                    loadStockDashboard.value = true
                else {
                    loadStockDashboard.value = loadStockDashboard.value != true
                }
            }
        })

    var loadFindPatient: MutableLiveData<Boolean> = MutableLiveData()
    var loadAddPatient: MutableLiveData<Boolean> = MutableLiveData()
    var loadActiveVisits: MutableLiveData<Boolean> = MutableLiveData()
    var loadFormEntry: MutableLiveData<Boolean> = MutableLiveData()
    var loadManageProviders: MutableLiveData<Boolean> = MutableLiveData()
    var loadMemberList: MutableLiveData<Boolean> = MutableLiveData()
    var loadReferredMemberList: MutableLiveData<Boolean> = MutableLiveData()
    var loadAddMember: MutableLiveData<Boolean> = MutableLiveData()
    var loadVideoCalls: MutableLiveData<Boolean> = MutableLiveData()
    var loadStockIn: MutableLiveData<Boolean> = MutableLiveData()
    var loadStockList: MutableLiveData<Boolean> = MutableLiveData()
    var loadStockDashboard: MutableLiveData<Boolean> = MutableLiveData()

    fun loadDrawerItems(context: Context) {
        drawerItemListAdapter.updateModuleItems(NavDrawerItem.getNavDrawerItems(context))
    }

    fun fetchSyncedPatients() {
        setLoading()
        addSubscription(patientDAO.allPatients
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { patients: List<Patient> -> setContent(patients) },
                { setError(it, OperationType.PatientFetching) }
            ))
    }

    fun fetchSyncedPatients(query: String) {
        setLoading()
        addSubscription(patientDAO.allPatients
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { patients: List<Patient> ->
                    val filteredPatients = FilterUtil.getPatientsFilteredByQuery(patients, query)
                    setContent(filteredPatients)
                },
                { setError(it, OperationType.PatientSearching) }
            ))
    }

    fun getProductList() {
        if (NetworkUtils.isOnline()) {
            setLoading()
            addSubscription(patientRepository.getProductList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {},
                    { }
                )
            )
        }
    }

    fun getAllLocation() {
        if (NetworkUtils.isOnline()) {
            if (patientRepository.globalLocationDAO.getAllLocation().isEmpty()) {
                setLoading()
                addSubscription(patientRepository.allLocation
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {},
                        { }
                    )
                )
            }
        }
    }

    fun getAllBloodGroup() {
        if (NetworkUtils.isOnline()) {
            if (patientRepository.bloodGroupDAO.getAllBloodGroup().isEmpty()) {
                setLoading()
                addSubscription(patientRepository.bloodGroupList
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {},
                        { }
                    )
                )
            }
        }
    }

    fun getAllMaritalStatus() {
        if (NetworkUtils.isOnline()) {
            if (patientRepository.maritalStatusDAO.getAllMaritalStatus().isEmpty()) {
                setLoading()
                addSubscription(patientRepository.maritalStatusList
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {},
                        { }
                    )
                )
            }
        }
    }
    fun getAllReligion() {
        if (NetworkUtils.isOnline()) {
            if (patientRepository.religionDAO.getAllReligion().isEmpty()) {
                setLoading()
                addSubscription(patientRepository.religionList
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {},
                        { }
                    )
                )
            }
        }
    }


    fun fetchSyncedPatientsOnRefresh(query: String) {
        if (NetworkUtils.isOnline()) {
            setLoading()
            addSubscription(patientRepository.findPatients(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { mPatients: List<ReferredPatient> ->
                        convertToPatient(mPatients).run {
                            insertServerPatients(this)
                        }
                    },
                    { setError(it, OperationType.PatientFetching) }
                )
            )
        }
    }

    private fun insertServerPatients(patients: List<Patient>) {
        val mPatients: MutableList<Patient> = mutableListOf()
        for (sPatient in patients) {
            //  Log.d("xxx", "bloog group: "+sPatient.person)
            if (sPatient.uuid != null && sPatient.uuid!!.isNotEmpty()) {
                val isSaved = patientDAO.isUserAlreadySaved(sPatient.uuid!!)
                if (!isSaved) {
                    patientRepository.findPatientDetails(sPatient.uuid)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { person: Person ->
                                sPatient.person = person
                                sPatient.display = person.display
                                sPatient.age = person.age
                                sPatient.birthdate = person.birthdate
                                sPatient.attributes = person.attributes
                                sPatient.gender = person.gender
                                sPatient.names = person.names
                                try {
                                    patientDAO.savePatient(sPatient).single().toBlocking().first()
                                } catch (e: Error) {
                                    ToastUtil.error(e.toString())
                                }
                            }, {
                                setError(it, OperationType.FetchProfileDetail)
                            }
                        )
                }
                mPatients.add(sPatient)
            }
        }
        setContent(patients)
    }

    private fun convertToPatient(rPatientList: List<ReferredPatient>): List<Patient> {
        val finalList: MutableList<Patient> = mutableListOf()
        for (rPerson in rPatientList) {
            finalList.add(rPerson.fromReferredPatient())
        }
        return finalList
    }

    private fun ReferredPatient.fromReferredPatient(): Patient {
        return Patient().apply {
            this.uuid = personUuid
            this.person.uuid = personUuid
            this.person.gender = gender
            this.bloodGroup = getBloodGroupValue(this@fromReferredPatient.bloodGroup!!)
            this.relegion = getReligionValue(this@fromReferredPatient.relegion!!)
            this.matritalStatus = getMaritalStatusValue(this@fromReferredPatient.matritalStatus!!)
            this.person.birthdate = parseDateTime(this@fromReferredPatient.birthdate!!)
            this.identifiers = mutableListOf(
                PatientIdentifier().apply {
                    identifier = this@fromReferredPatient.identifier
                }
            )
            this.person.display = this@fromReferredPatient.run {
                "${this.firstName ?: ""} ${this.lastName ?: ""}"
            }
        }
    }

    private fun getMaritalStatusValue(key: String): String {
        var value = ""

        when (key) {
            "6e4a97f3-e6ab-4bf0-8fbd-8bf8b0361500" -> value = "Married"
            "58605e6c-7043-4896-888c-564fef1c23e3" -> value = "Single"
            "875b5d4b-1498-4734-b14f-adc10d46fbd3" -> value = "Divorced"
            "413a4a02-573b-4dba-a875-afa43dbab3e4" -> value = "Conjugal Separation"
            "08076fad-b454-4f74-9a4c-765672e97afb" -> value = "Unmarried"
            "85b0846f-3723-45ae-a840-60e12c273a7e" -> value = "Widow"
            "d13f1522-7a28-4dff-8d7d-dd32f43f4a63" -> value = "Widower"
        }

        return value
    }

    private fun getBloodGroupValue(key: String): String {
        var value = ""
        when (key) {
            "1a9aa408-e1c0-437a-8082-b882b07d5243" -> value = "A+"
            "bd527e15-8515-4ee0-8d14-4260f3813866" -> value = "A-"
            "19bd03b0-2c94-4d90-9728-d5ba41ac47b5" -> value = "B+"
            "21bd05a5-5816-4d28-ab11-4d18c121540c" -> value = "B-"
            "8d974903-3cc9-4471-be64-49551923d8b3" -> value = "AB+"
            "a30d9efc-6381-4885-a74b-d4e03955ec03" -> value = "AB-"
            "9566b37e-3db5-49f0-ac3e-d620a2835758" -> value = "O+"
            "243bd990-ba5b-418a-ba89-ceeb80947340" -> value = "O-"
        }
        return value
    }

    private fun getReligionValue(key: String): String {
        var value = ""

        when (key) {
            "e52294c1-1f7f-49ae-adea-3574611fa464" -> value = "Islam"
            "bfce7390-4954-4a95-aa3d-b525d20527a0" -> value = "Hinduism"
            "810cb734-25bf-4604-9088-6035bf963799" -> value = "Christianity"
            "53dd5fe1-790b-4f63-a927-c93e66358f09" -> value = "Buddhism"
            "5622AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" -> value = "Other"
        }

        return value
    }


    private fun parseDateTime(value: Long): String {
        val date = Date(value)
        val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return format.format(date)
    }

    fun deleteSyncedPatient(patient: Patient) {
        setLoading()
        patientDAO.deletePatient(patient.id!!)
        addSubscription(
            visitDAO.deleteVisitsByPatientId(patient.id!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    fun gotoRegisterPatient(view: View) {
        if (loadAddPatient.value == null)
            loadAddPatient.value = true
    }

    override fun onItemClicked(item: Any?) {
        val navDrawer = item as NavDrawerItem
//        if (navDrawer.id == Constants.ITEM_FIND_PATIENT) {
//            if (loadFindPatient.value == null)
//                loadFindPatient.value = true
//            else {
//                loadFindPatient.value = loadFindPatient.value != true
//            }
//        } else if (navDrawer.id == Constants.ITEM_ADD_PATIENT) {
//            if (loadAddPatient.value == null)
//                loadAddPatient.value = true
//            else {
//                loadAddPatient.value = loadAddPatient.value != true
//            }
//        } else if (navDrawer.id == Constants.ITEM_ACTIVE_VISITS) {
//            if (loadActiveVisits.value == null)
//                loadActiveVisits.value = true
//            else {
//                loadActiveVisits.value = loadActiveVisits.value != true
//            }
//        } else if (navDrawer.id == Constants.ITEM_FORM_ENTRY) {
//            if (loadFormEntry.value == null)
//                loadFormEntry.value = true
//            else {
//                loadFormEntry.value = loadFormEntry.value != true
//            }
//        } else if (navDrawer.id == Constants.ITEM_MANAGE_PROVIDERS) {
//            if (loadManageProviders.value == null)
//                loadManageProviders.value = true
//            else {
//                loadManageProviders.value = loadManageProviders.value != true
//            }
//        } else if (navDrawer.id == Constants.ITEM_FIND_MEMBER) {
//            if (loadMemberList.value == null)
//                loadMemberList.value = true
//            else {
//                loadMemberList.value = loadMemberList.value != true
//            }
//        } else if (navDrawer.id == Constants.ITEM_REFERRED_MEMBER_LIST) {
//            if (loadReferredMemberList.value == null)
//                loadReferredMemberList.value = true
//            else {
//                loadReferredMemberList.value = loadReferredMemberList.value != true
//            }
//        } else if (navDrawer.id == Constants.ITEM_ADD_MEMBER) {
//            if (loadAddMember.value == null)
//                loadAddMember.value = true
//            else {
//                loadAddMember.value = loadAddMember.value != true
//            }
//        } else if (navDrawer.id == Constants.ITEM_VIDEO_CALL) {
//            if (loadVideoCalls.value == null)
//                loadVideoCalls.value = true
//            else {
//                loadVideoCalls.value = loadVideoCalls.value != true
//            }
//        } else if (navDrawer.id == Constants.ITEM_STOCK_IN) {
//            if (loadStockIn.value == null)
//                loadStockIn.value = true
//            else {
//                loadStockIn.value = loadStockIn.value != true
//            }
//        } else if (navDrawer.id == Constants.ITEM_STOCK_LIST) {
//            if (loadStockList.value == null)
//                loadStockList.value = true
//            else {
//                loadStockList.value = loadStockList.value != true
//            }
//        } else if (navDrawer.id == Constants.ITEM_STOCK_DASHBOARD) {
//            if (loadStockDashboard.value == null)
//                loadStockDashboard.value = true
//            else {
//                loadStockDashboard.value = loadStockDashboard.value != true
//            }
//        }

    }

}
