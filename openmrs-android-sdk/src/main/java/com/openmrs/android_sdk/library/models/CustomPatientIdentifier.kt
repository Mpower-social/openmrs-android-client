package com.openmrs.android_sdk.library.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.openmrs.android_sdk.library.databases.entities.LocationEntity

class CustomPatientIdentifier {
    @SerializedName("identifierType")
    @Expose
    var identifierType: String? = null

    @SerializedName("identifier")
    @Expose
    var identifier: String? = null

    @SerializedName("location")
    @Expose
    var location: String? = null
}