package org.intelehealth.app.mpower.activities.referedMemberList

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.openmrs.android_sdk.library.OpenmrsAndroid
import com.openmrs.android_sdk.library.api.repository.ReferredPatientRepository
import com.openmrs.android_sdk.library.models.OperationType
import com.openmrs.android_sdk.library.models.ReferredPatient
import com.openmrs.android_sdk.library.models.UserLocation
import com.openmrs.android_sdk.utilities.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.intelehealth.app.mpower.activities.BaseViewModel
import org.intelehealth.app.mpower.listeners.ItemClickListener
import rx.android.schedulers.AndroidSchedulers

import javax.inject.Inject


@HiltViewModel
class ReferedMembersViewModel @Inject constructor(private val patientRepository: ReferredPatientRepository) : BaseViewModel<List<ReferredPatient>>(),
    ItemClickListener {

    private val subscriptions = CompositeDisposable()

    fun fetchMembers() {
        setLoading()
        addSubscription(patientRepository.getAllPatients()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { setContent(it) },
                { setError(it, OperationType.MemberFetching) }
            ))
    }

    fun fetchMembers(query: String) {
        setLoading()
        addSubscription(patientRepository.findPatientByQuery(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { setContent(it) },
                { setError(it, OperationType.MemberSearching) }
            ))
    }

    fun fetchReferredMembersOnRefresh() {
        if (NetworkUtils.isOnline()) {
            setLoading()
            subscriptions.add( io.reactivex.Observable.fromIterable(getUserLocations())
                .flatMap { location ->
                    return@flatMap io.reactivex.Observable.just(patientRepository.fetchPatientsFromServerAndGetAll(wardId = location.wardId!!, 0))
                }
                .toList()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { setContent(it.flatten()) },
                    { setError(it, OperationType.MemberFetching) },
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    override fun onItemClicked(item: Any?) {

    }

    private fun getUserLocations(): List<UserLocation> {
        return listOf(UserLocation(wardId = 52640))
        /*return OpenmrsAndroid.getUserLocationInformation().let { locations ->
            Gson().fromJson(locations, object : TypeToken<List<UserLocation>>() {}.type)
        }*/
    }
}