package com.openmrs.android_sdk.library.api.responseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class StockListPostModel(

    @Expose @SerializedName("drugId")
    var drugId: Int = 0,

    @Expose @SerializedName("startDate")
    var startDate: String = "",

    @Expose @SerializedName("endDate")
    var endDate: String = "",

    @Expose @SerializedName("invoice")
    var invoice: String = ""

)