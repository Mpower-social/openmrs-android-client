package com.openmrs.android_sdk.library.api.responseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrentStockModel(

    @Expose @SerializedName("count")
    var count: Int? = null,

)