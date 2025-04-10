package com.openmrs.android_sdk.library.models

import com.google.gson.annotations.Expose

data class UserLocation(
  @Expose var id: Long? = null,
  @Expose var userId: Long? = null,
  @Expose var username: String? = null,
  @Expose var divisionId: Long? = null,
  @Expose var districtId: Long? = null,
  @Expose var upazilaId: Long? = null,
  @Expose var paurasavaId: Long? = null,
  @Expose var unionId: Long? = null,
  @Expose var wardId: Long? = null,
  @Expose var divisionCode: String? = null,
  @Expose var districtCode: String? = null,
  @Expose var upazilaCode: String? = null,
  @Expose var paurasavaCode: String? = null,
  @Expose var unionCode: String? = null,
  @Expose var wardCode: String? = null,
  @Expose var facilityId: Int? = null,
  @Expose var hrmId: Long? = null,
)
