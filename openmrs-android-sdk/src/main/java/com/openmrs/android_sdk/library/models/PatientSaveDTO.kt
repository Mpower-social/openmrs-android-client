package com.openmrs.android_sdk.library.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PatientSaveDTO : Person() {

    @SerializedName("deathDate")
    @Expose
    var deathDate: String? = null

    @SerializedName("birthtime")
    @Expose
    var birthtime: String? = null

    @SerializedName("deathdateEstimated")
    @Expose
    var deathdateEstimated: Boolean? = false

    @SerializedName("preferredName")
    @Expose
    var preferredName: PreferredName? = null

    @SerializedName("resourceVersion")
    @Expose
    var resourceVersion: String? = null

    @SerializedName("personUuid")
    @Expose
    var personUUID: String? = null

    @SerializedName("identifier")
    @Expose
    var identifier: String? = null

    @SerializedName("firstName")
    @Expose
    var firstName: String? = null

    @SerializedName("lastName")
    @Expose
    var lastName: String? = null

    @SerializedName("birthPlace")
    @Expose
    var birthPlace: String? = null

    @SerializedName("mobile")
    @Expose
    var mobile: String? = null

    @SerializedName("nid")
    @Expose
    var nid: String? = null

    @SerializedName("motherName")
    @Expose
    var motherName: String? = null

    @SerializedName("motherNameBangla")
    @Expose
    var motherNameBangla: String? = null

    @SerializedName("fatherName")
    @Expose
    var fatherName: String? = null

    @SerializedName("fatherNameBangla")
    @Expose
    var fatherNameBangla: String? = null

    @SerializedName("nationality")
    @Expose
    var nationality: String? = null

    @SerializedName("occupation")
    @Expose
    var occupation: String? = null

//    @SerializedName("relegion")
//    @Expose
//    var relegion: String? = null

    @SerializedName("division")
    @Expose
    var division: String? = null

//    @SerializedName("bloodGroup")
//    @Expose
//    var bloodGroup: String? = null

    @SerializedName("eduQualification")
    @Expose
    var eduQualification: String? = null

//    @SerializedName("matritalStatus")
//    @Expose
//    var matritalStatus: String? = null

    @SerializedName("ethnicity")
    @Expose
    var ethnicity: String? = null

    @SerializedName("fullNameBangla")
    @Expose
    var fullNameBangla: String? = null

    @SerializedName("disabilityType")
    @Expose
    var disabilityType: String? = null

    @SerializedName("spouseNameBangla")
    @Expose
    var spouseNameBangla: String? = null

    @SerializedName("spouseNameEnglish")
    @Expose
    var spouseNameEnglish: String? = null

    @SerializedName("district")
    @Expose
    var district: String? = null

    @SerializedName("upazila")
    @Expose
    var upazila: String? = null

    @SerializedName("paurasava")
    @Expose
    var paurasava: String? = null

    @SerializedName("unionName")
    @Expose
    var unionName: String? = null

    @SerializedName("ward")
    @Expose
    var ward: String? = null

    @SerializedName("patientAddress")
    @Expose
    var patientAddress: String? = null

    @SerializedName("unionId")
    @Expose
    var unionID: String? = "0"

    @SerializedName("divisionId")
    @Expose
    var divisionID: String? = null

    @SerializedName("districtId")
    @Expose
    var districtID: String? = null

    @SerializedName("upazilaId")
    @Expose
    var upazilaID: String? = null

    @SerializedName("paurasavaId")
    @Expose
    var paurasavaID: String? = null

    @SerializedName("wardId")
    @Expose
    var wardID: String? = "0"

    @SerializedName("location")
    @Expose
    var location: Long? = 0

    @SerializedName("countryId")
    @Expose
    var countryID: Long? = 0

    @SerializedName("blockId")
    @Expose
    var blockID: Long? = 0

}

class PreferredName : Resource()