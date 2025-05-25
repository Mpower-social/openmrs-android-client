package com.openmrs.android_sdk.library.models

import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.openmrs.android_sdk.library.models.typeConverters.PersonAddressConverter
import com.openmrs.android_sdk.library.models.typeConverters.PersonAttributeConverter
import com.openmrs.android_sdk.library.models.typeConverters.PersonNameConverter

open class CustomPerson {
    @TypeConverters(PersonNameConverter::class)
    @SerializedName("names")
    @Expose
    var names: MutableList<PersonName> = mutableListOf()

    @SerializedName("gender")
    @Expose
    var gender: String? = null

    @SerializedName("matritalStatus")
    @Expose
    private val matritalStatus: String? = null

    @SerializedName("relegion")
    @Expose
    private val relegion: String? = null

    @SerializedName("bloodGroup")
    @Expose
    private val bloodGroup: String? = null

    @SerializedName("uuid")
    @Expose
    var uuid : String? = null

    @SerializedName("birthdate")
    @Expose
    var birthdate: String? = null

    @SerializedName("birthdateEstimated")
    @Expose
    var birthdateEstimated = false

    @TypeConverters(PersonAddressConverter::class)
    @SerializedName("addresses")
    @Expose
    var addresses: MutableList<PersonAddress> = mutableListOf()

    @TypeConverters(PersonAttributeConverter::class)
    @SerializedName("attributes")
    @Expose
    var attributes: MutableList<PersonAttributeCustom> = mutableListOf()

    @SerializedName("dead")
    @Expose
    var dead: Boolean? = null

}