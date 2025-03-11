package com.openmrs.android_sdk.library.api.responseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class StockInModel(

    @Expose @SerializedName("drugId")
    var drugId: Int? = null,

    @Expose @SerializedName("stockInDate")
    var stockInDate: String = "",

    @Expose @SerializedName("invoice")
    var invoice: String = "",

    @Expose @SerializedName("item")
    var item: String = "",

    @Expose @SerializedName("quantity")
    var quantity: Int = 0,

    @Expose @SerializedName("currentStock")
    var currentStock: Int = 0,

    @Expose @SerializedName("expire")
    var expire: String = "",

    @Expose @SerializedName("batchNo")
    var batchNo: String = "",

    @Expose @SerializedName("receivedFrom")
    var receivedFrom: String = ""

)