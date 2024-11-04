package com.openmrs.android_sdk.library.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class PatientCreateDTO : Resource() {
    @SerializedName("identifiers")
    @Expose
    var identifiers: List<CustomPatientIdentifier> = ArrayList()

    @SerializedName("person")
    @Expose
    var person: CustomPerson? = null
}