/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package com.openmrs.android_sdk.utilities

import com.openmrs.android_sdk.library.models.EncounterType


object ApplicationConstants {
    const val UUID_LENGTH = 36
    const val EMPTY_STRING = ""
    const val SERVER_URL = "server_url"
    const val SESSION_TOKEN = "session_id"
    const val AUTHORIZATION_TOKEN = "authorisation"
    const val SECRET_KEY = "secretKey"
    const val LOCATION = "location"
    const val PROVIDER_ID = "provider_id"
    const val HW_NAME = "hw_name"
    const val FIRST = true
//    const val CHOOSE_ONE = "choose one"
    const val CHOOSE_ONE = "choose one"
    const val VISIT_TYPE_UUID = "visit_type_uuid"
    const val LAST_SESSION_TOKEN = "last_session_id"
    const val LAST_LOGIN_SERVER_URL = "last_login_server_url"
//    const val DEFAULT_OPEN_MRS_URL = "https://demo.openmrs.org/openmrs"
    const val DEFAULT_OPEN_MRS_URL = "https://openmrs-o3.mpower-social.com/openmrs"
//    const val DEFAULT_OPEN_MRS_URL = "https://openmrs-o3-dev.mpower-social.com/openmrs"
//    const val DEFAULT_OPEN_MRS_URL = "https://o3.openmrs.org/openmrs"

    const val LIVE_KIT_URL = "wss://demo.intelehealth.org:9090"
    const val SOCKET_URL = "wss://intelehealth-o3.mpower-social.com:3004/"
    const val RTC_SERVER_URL = "https://intelehealth-o3.mpower-social.com:3000"
    const val SOCKET_URL_NEW = "https://intelehealth-o3.mpower-social.com:3004"
//    const val SOCKET_URL_NEW = "https://dev.intelehealth.org:3004"

    const val FIREBASE_OAUTH_TOKEN = "ya29.c.c0ASRK0GaMsq09w9Y3E5s3bM_cEkdI3PhP3yYDAYw0RRvud1ccGSinyW_wVatFpLg_EbPS6ISuOVMXzT_eooIozYY5SyzV6ThXwj2H1PmnnqL29Czpz7F8fePRSv01ZbLReBvYMrGELXgZZtIts7tcPIFXeZ5Mej6rIP9yiy8JHqmHKnKyWCH4teFgw7q73MYfDshATHc0TuEdjowIMr-gfg72CY_KYS9RGDUgjtN-6wYY2l3mwD8I4ou8J9rDQ2Ey8lq_Bvs2AYb_aLxYnzHo35fJqFvrioU03ILC2cJ9X-Fo3AuWJGNZj1rQfXk5gCGe6ZVJJEGtsXCduphdzIafkgw8QIvNxX5wwyKlCIYgDnWFEhpeXhfG9YoH384Pvt1wi13yjtQenoqo2s-1qf1FSmb1O66hW5JXr3j96v5wrdYeJtzQXq2JyRl8p8OxrkFw3gFw9r_3jVRuvgxz5znrao3OtvYb_soe9euIqc3RX97l1Q2-kQ8133rww_2OlghiatpxuJYkF9QYurg65a8fI7FhIxSOgn_hX7wJI78fM5dhJqfseploqS5J0abmOSt_vb_gh48s4pzyIa7X4yiv5_c8J3jeZiaXjxuybaXucdXcdMFlrR9pd7z2ZkZYIO-a9MmeBRab42Syy4sIbrfh9dxVOWzo27yhcY0WOOJJRoBR-sg8h50k3OhjQa-nYg20t-n_nIVFrJ4vBBZWJ9SXosa2f8znY7rQO5ky_hY51zoJaWe_26uX5W919azog7XkbXM2e2rW3nplB2cioVzSgxwpf-YBenZrY5fOuYp1fonBvx5WQSb-nRaXf7iyz-r9UQqfv_kiVB7ipIxp-5F2rrX6aj2wMr9RSQ91qtJcUjo24nZsqbQwtnr4mUx1-MXIVB1X_kUokbMOr_29U0rcSRqImXwnVea0pp3UMvxBZBamibb_wi6IMgsow1wfXVrtIpRr_21-F6OhjiJI3_IJ1Ryy4Mzbb0ilz5oO_zeB6znhmcpkjt51mVI"


    const val DB_NAME = "openmrs.db"
    const val DB_PASSWORD_BCRYPT_PEPPER = "$2a$08\$iUp3M1VapYpjcAXQBNX6uu"
    const val DB_PASSWORD_LITERAL_PEPPER = "Open Sesame"
    const val DEFAULT_VISIT_TYPE_UUID = "7b0f5697-27e3-40c4-8bae-f4049abfb4ed"
    const val DEFAULT_BCRYPT_ROUND = 8
    const val SPLASH_TIMER = 3500
    const val PACKAGE_NAME = "org.openmrs.mobile"
    const val USER_GUIDE = "https://openmrs.github.io/openmrs-android-client-user-guide/getting-started.html"
    const val MESSAGE_RFC_822 = "message/rfc822"
    const val FLAG = "flag"
    const val ERROR = "error"
    const val URI_FILE = "file://"
    const val URI_IMAGE = "image/*"
    const val IMAGE_JPEG = "image/jpeg"
    const val INTENT_KEY_PHOTO = "photo"
    const val INTENT_KEY_NAME = "name"
    const val READ_MODE = "r"
    const val MIME_TYPE_MAILTO = "mailto:"
    const val OPENMRS_PREF_FILE = "OpenMRSPrefFile"
    const val VITAL_NAME = "vitalName"
    const val BUNDLE = "bundle"
    const val URI_CONTENT = "content://"
    const val MIME_TYPE_VND = "vnd"
    const val ASPECT_RATIO_FOR_CROPPING = 5f
    const val CAUSE_OF_DEATH = "concept.causeOfDeath"
    const val MALE = "M";
    const val EMPTY_DASH_REPRESENTATION = "---"
    const val COMMA_WITH_SPACE = ", "
    const val PRIMARY_KEY_ID = "_id"
    const val MIN_NUMBER_OF_PATIENTS_TO_SHOW = 7;
    const val ABOUT_OPENMRS_URL = "https://openmrs.org/about/"

    const val COUNTRY_CODE_BD = 24525

    const val SEARCH_TYPE = "type"
    const val SEARCH_TEXT = "text"
    const val SEARCH_DOB = "dob"

    const val PATIENTS_MOBILE_UUID = "14d4f066-15f5-102d-96e4-000c29c2a5d7"
    const val PATIENTS_MARITAL_STATUS_UUID = "9c161cbb-9508-435c-b256-23af971dff87"
    const val PATIENTS_BLOOD_GROUP_UUID = "2cdc9da4-4d56-40e8-bb5f-972f61ab89d7"
    const val PATIENTS_RELIGION_UUID = "90d733c2-c50c-4f80-9d62-da402af62ea1"
    const val PATIENTS_FULL_NAME_BANGLA_UUID = "c9aaba5b-9227-4e30-8067-a6c1f15b0174"
    const val PATIENTS_MOTHER_NAME_UUID = "87a689e1-41cc-41ce-a25e-d3c584673dce"
    const val PATIENTS_MOTHER_NAME_BANGLA_UUID = "20bdcfd2-c58f-4552-a002-56ceb605c288"
    const val PATIENTS_FATHER_NAME_UUID = "2b8051e6-3c2d-45b5-ac92-2db429a97edb"
    const val PATIENTS_FATHER_NAME_BANGLA_UUID = "25819ba8-6126-48f3-b8f0-d31960c31e65"
    const val PATIENTS_BIRTH_PLACE_UUID = "8d8718c2-c2cc-11de-8d13-0010c6dffd0f"
    const val PATIENTS_NATIONALITY_UUID = "26ad0d0b-d6c3-4c78-a4b7-4d4e04bc9e86"
    const val PATIENTS_OCCUPATION_UUID = "5d9e809a-d9c8-4b41-8c0d-27e6c92c20b4"
    const val PATIENTS_EDUCATION_UUID = "e613034f-a9e4-4740-a915-7a50c8c31f77"
    const val PATIENTS_DISABILITY_TYPE_UUID = "8263c039-e758-4d2f-9064-e57a660a3705"
    const val PATIENTS_ETHNICITY_UUID = "e5f3c903-ef1e-4f6a-a5b5-e4e6676875fe"
    const val PATIENTS_MARITAL_STATUS_OPTIONS_UUID = "c955a699-5f57-4a92-a7e4-30c6215430e9"
    const val PATIENTS_BLOOD_GROUP_OPTIONS_UUID = "4c2a94c6-5a14-485e-b0f5-01921750b9b6"
    const val PATIENTS_RELIGION_OPTIONS_UUID = "43eee45a-082e-4216-8107-9f9e1977330b"
    const val PATIENTS_DIVISION_UUID = "f737da5f-d684-4eb8-884e-984e4c62cc0d"
    const val PATIENTS_DISTRICT_UUID = "836bbd5c-7794-4716-97d7-a3a61ebed982"
    const val PATIENTS_UPAZILA_UUID = "29aeb2b3-5b6d-45b1-a8c1-307ca7e6167a"
    const val PATIENTS_PAURASAVA_UUID = "a1f2cd6f-f76b-460f-b1a2-e5fc4b759387"
    const val PATIENTS_UNION_UUID = "5d6ac3dd-1ac9-41b6-ae0a-e8a85a97a21c"
    const val PATIENTS_WARD_UUID = "59daac4e-635b-43f1-a3c2-4000a70ec75e"
    const val PATIENTS_BLOCK_UUID = "aeaf2d54-a3a5-4dc1-8a14-92bbba9e0344"
    const val PATIENTS_ADDRESS_UUID = "b7a0bb2e-74b9-4236-8b3d-f403cd53b32b"
    const val PATIENTS_DIVISION_ID_UUID = "88f38dba-248f-428b-bbde-64096346bc65"
    const val PATIENTS_DISTRICT_ID_UUID = "88bee94d-3b21-4412-a3f0-a638b5d50134"
    const val PATIENTS_UPAZILA_ID_UUID = "35f6cd31-02e2-4d7e-a55d-9a0e70239c18"
    const val PATIENTS_PAURASAVA_ID_UUID = "3af2cba5-1495-4629-97cd-7546014107cf"
    const val PATIENTS_UNION_ID_UUID = "d04dc189-5826-4f0d-9df5-838f35dbd0f6"
    const val PATIENTS_WARD_ID_UUID = "e51c7415-0206-464e-9c79-b00fa847bc46"
    const val PATIENTS_BLOCK_ID_UUID = "afbade4d-f62c-441b-9f40-91de43fdf9b3"
    const val PATIENTS_GENDER_UUID = "cecdbd30-8860-4cfd-a640-b5edc507051d"
    const val PATIENTS_NID_UUID = "041dcab7-ac07-41aa-928a-1f13e7c65c34"
    const val PATIENTS_BRID_UUID = "0c198a6f-4a16-4398-92b6-57e5beee695f"
    const val PATIENTS_BIRTH_DATE_UUID = "0b0cc1c8-5658-4877-b81c-04fafd050f30"




// opensrp table UUIDS
    const val OPENSRP_PATIENTS_MOBILE_UUID = "8ebcc91c-0fe4-4220-8aec-1830fe4d5041"
    const val OPENSRP_PATIENTS_MARITAL_STATUS_UUID = "d14f103a-e5ea-4475-97a3-874d9c8fe328"
    const val OPENSRP_PATIENTS_BLOOD_GROUP_UUID = "33ee79ac-4806-472d-9b24-d7d9f0622646"
    const val OPENSRP_PATIENTS_RELIGION_UUID = "4aa53384-8bf2-43e4-936f-4737b1b44e01"
    const val OPENSRP_PATIENTS_FULL_NAME_BANGLA_UUID = "d534a372-1640-4e34-8eb5-f41c36e38dff"
    const val OPENSRP_PATIENTS_MOTHER_NAME_UUID = "206c1b18-d3e8-49a2-bbbe-5872bff705f7"
    const val OPENSRP_PATIENTS_MOTHER_NAME_BANGLA_UUID = "8fa6ad0e-ee0e-400b-80f8-25babab0b120"
    const val OPENSRP_PATIENTS_FATHER_NAME_UUID = "5eb2243d-f837-4613-ba80-c7801a2bd314"
    const val OPENSRP_PATIENTS_FATHER_NAME_BANGLA_UUID = "c8aa2ae3-8408-47e9-a8a4-b97022d7d932"
    const val OPENSRP_PATIENTS_BIRTH_PLACE_UUID = "90c58894-214b-4b74-8822-75b0028cf4a9"
    const val OPENSRP_PATIENTS_NATIONALITY_UUID = "23a7e5f7-d721-4a6f-825f-1f7d86945a3f"
    const val OPENSRP_PATIENTS_OCCUPATION_UUID = "1b2b8381-c2d5-4bf9-b984-7e9c1d56547f"
    const val OPENSRP_PATIENTS_EDUCATION_UUID = "fcab1a27-6962-4eb7-8079-f41fbe24298c"
    const val OPENSRP_PATIENTS_DISABILITY_TYPE_UUID = "138eb092-512b-4d15-9c3e-7d82b60be590"
    const val OPENSRP_PATIENTS_ETHNICITY_UUID = "6ddd8f80-3d62-4c0c-ac7a-4c7d0caa4577"
    const val OPENSRP_PATIENTS_MARITAL_STATUS_OPTIONS_UUID = ""
    const val OPENSRP_PATIENTS_BLOOD_GROUP_OPTIONS_UUID = ""
    const val OPENSRP_PATIENTS_RELIGION_OPTIONS_UUID = ""
    const val OPENSRP_PATIENTS_DIVISION_UUID = "891bbb4b-fb8a-441d-8d49-d2e23858439d"
    const val OPENSRP_PATIENTS_DISTRICT_UUID = "a868ea1a-a53d-4f65-aace-de829db1391e"
    const val OPENSRP_PATIENTS_UPAZILA_UUID = "9b47661d-9a36-42b3-8235-03d483a34610"
    const val OPENSRP_PATIENTS_PAURASAVA_UUID = "f00d9256-a665-470b-b71f-21856cea7814"
    const val OPENSRP_PATIENTS_UNION_UUID = "df59e355-86cb-4add-8f94-5b4049719e75"
    const val OPENSRP_PATIENTS_WARD_UUID = "b6e64a4c-1eda-4e81-94f6-ae8b1854989f"
    const val OPENSRP_PATIENTS_BLOCK_UUID = ""
    const val OPENSRP_PATIENTS_ADDRESS_UUID = "e5ffb495-eee7-485b-aaff-358ae8b476db"
    const val OPENSRP_PATIENTS_DIVISION_ID_UUID = "ef2b437c-47ee-425e-9703-378ec736d9fe"
    const val OPENSRP_PATIENTS_DISTRICT_ID_UUID = "f2868e75-2063-4971-b5ff-61a1705779f0"
    const val OPENSRP_PATIENTS_UPAZILA_ID_UUID = "bc2b3bb0-c6a7-4d53-b414-98cff2270ade"
    const val OPENSRP_PATIENTS_PAURASAVA_ID_UUID = "716b46a4-ca7f-4317-96b5-00254b70cea3"
    const val OPENSRP_PATIENTS_UNION_ID_UUID = "015f58d9-d854-497b-8bec-b20555a5530b"
    const val OPENSRP_PATIENTS_WARD_ID_UUID = "af1ff502-1845-4e9d-ad89-8bc0d3d827cd"
    const val OPENSRP_PATIENTS_BLOCK_ID_UUID = ""
    const val OPENSRP_PATIENTS_GENDER_UUID = ""
    const val OPENSRP_PATIENTS_NID_UUID = "e4aab635-e39b-4cff-bf94-7bea68cd5f78"
    const val OPENSRP_PATIENTS_BRID_UUID = ""
    const val OPENSRP_PATIENTS_BIRTH_DATE_UUID = ""
    const val OPENSRP_PATIENTS_SPOUSE_BANGLA_NAME_UUID = ""
    const val OPENSRP_PATIENTS_SPOUSE_ENGLISH_NAME_UUID = ""




    const val PATIENTS_MOBILE_KEY = "mobile"
    const val PATIENTS_MARITAL_STATUS_KEY = "matritalStatus"
    const val PATIENTS_BLOOD_GROUP_KEY = "bloodGroup"
    const val PATIENTS_RELIGION_KEY = "relegion"
    const val PATIENTS_FULL_NAME_BANGLA_KEY = "fullNameBangla"
    const val PATIENTS_SPOUSE_NAME_BANGLA_KEY = "spouseNameBangla"
    const val PATIENTS_SPOUSE_NAME_ENGLISH_KEY = "spouseNameEnglish"
    const val PATIENTS_MOTHER_NAME_KEY = "motherName"
    const val PATIENTS_MOTHER_NAME_BANGLA_KEY = "motherNameBangla"
    const val PATIENTS_FATHER_NAME_KEY = "fatherName"
    const val PATIENTS_FATHER_NAME_BANGLA_KEY = "fatherNameBangla"
    const val PATIENTS_BIRTH_PLACE_KEY = "birthPlace"
    const val PATIENTS_NATIONALITY_KEY = "nationality"
    const val PATIENTS_OCCUPATION_KEY = "occupation"
    const val PATIENTS_EDUCATION_KEY = "eduQualification"
    const val PATIENTS_DISABILITY_TYPE_KEY = "disabilityType"
    const val PATIENTS_ETHNICITY_KEY = "ethnicity"
    const val PATIENTS_MARITAL_STATUS_OPTIONS_KEY = ""
    const val PATIENTS_BLOOD_GROUP_OPTIONS_KEY = ""
    const val PATIENTS_RELIGION_OPTIONS_KEY = ""
    const val PATIENTS_DIVISION_KEY = "division"
    const val PATIENTS_DISTRICT_KEY = "district"
    const val PATIENTS_UPAZILA_KEY = "upazila"
    const val PATIENTS_PAURASAVA_KEY = "paurasava"
    const val PATIENTS_UNION_KEY = "unionName"
    const val PATIENTS_WARD_KEY = "ward"
    const val PATIENTS_BLOCK_KEY = "block"
    const val PATIENTS_ADDRESS_KEY = "patientAddress"
    const val PATIENTS_DIVISION_ID_KEY = "divisionId"
    const val PATIENTS_DISTRICT_ID_KEY = "districtId"
    const val PATIENTS_UPAZILA_ID_KEY = "upazilaId"
    const val PATIENTS_PAURASAVA_ID_KEY = "paurasavaId"
    const val PATIENTS_UNION_ID_KEY = "unionId"
    const val PATIENTS_WARD_ID_KEY = "wardId"
    const val PATIENTS_BLOCK_ID_KEY = "blockId"
    const val PATIENTS_GENDER_KEY = "gender"
    const val PATIENTS_NID_KEY = "nid"
    const val PATIENTS_BRID_KEY = ""
    const val PATIENTS_BIRTH_DATE_KEY = "birthdate"


    object OpenMRSSharedPreferenceNames {
        const val SHARED_PREFERENCES_NAME = "shared_preferences"
    }

    object API {
        const val REST_ENDPOINT = "/ws/rest/v1/"
        const val FULL = "full"
        const val TAG_ADMISSION_LOCATION = "Admission Location"
    }

    object GENDER {
        const val FEMALE = "F"
        const val MALE = "M"
    }

    object MemberProfileKeys {
        const val MEMBER_PROFILE_NID = "nid"
        const val MEMBER_PROFILE_RELIGION = "relegion"
        const val MEMBER_PROFILE_MARITAL_STATUS = "matritalStatus"
        const val MEMBER_PROFILE_MOBILE = "mobile"
        const val MEMBER_PROFILE_BLOOD_GROUP = "bloodGroup"
        const val MEMBER_PROFILE_BIRTH_PLACE = "birthPlace"
        const val MEMBER_PROFILE_NATIONALITY = "nationality"
        const val MEMBER_PROFILE_OCCUPATION = "occupation"
        const val MEMBER_PROFILE_DIVISION = "division"
        const val MEMBER_PROFILE_DISTRICT = "district"
        const val MEMBER_PROFILE_UPAZILA = "upazila"
        const val MEMBER_PROFILE_PAURASAVA = "paurasava"
        const val MEMBER_PROFILE_UNION = "unionName"
        const val MEMBER_PROFILE_WARD = "ward"
        const val MEMBER_PROFILE_ADDRESS = "patientAddress"
        const val MEMBER_PROFILE_BLOCK = "block"
    }

    object WidgetTypes {
        const val TEXTVIEW = "textview"
        const val EDITTEXT = "edittext"
        const val DROPDOWN = "dropdown"
        const val RADIOBUTTON = "radiobutton"
    }

    object AttributeValues {
        const val MARRIED = "Married"
    }

    object FormListKeys {
        const val PRE_PREGNANCY = "প্রসব পূর্ব"
        const val POST_PREGNANCY = "প্রসবোত্তর"
        const val ABORTED = "গর্ভ নষ্ট হয়েছে"
        const val MRDONE = "MR করেছে"
        const val NO_PREGNANCY = "কোনোটিই নয়"
        const val PREGNANCY_SERVICE = "গর্ভাবস্থার তথ্য"
        const val FAMILY_PLANNING_SERVICE = "পরিবার পরিকল্পনা সেবা"
        const val PRE_PREGNANCY_SERVICE= "প্রসব পূর্ব সেবা"
        const val POST_PREGNANCY_SERVICE = "প্রসব পরবর্তী সেবা"
        const val GENERAL_PATIENT_SERVICE = "সাধারন রোগীর সেবা"
    }

    object FormTypeKeys {
        const val FORM_PREGNANCY_UUID = "9b6d25fb-c1d2-4635-ad2e-2af5277dd205"
        const val FORM_PREGNANCY_ANC_UUID = "85a9e937-bb18-473c-9d5e-2e7d6b6a6e4d"
        const val FORM_PREGNANCY_PNC_UUID = "8f0c2fdf-efa8-4341-8afb-02f3e69601c8"
        const val FORM_PREGNANCY_ABORTION_UUID = "c634c9ee-1edc-4a0d-83f5-12467d38ed17"
        const val FORM_PREGNANCY_MR_UUID = "122c3767-d053-4922-9cfc-28966bd603a1"
        const val FORM_PREGNANCY_NOTHING_UUID = "5be0fa04-94c3-48d1-85f0-01934c62b08b"
    }

    object FormQuestionKeys {
        const val PREGNANCY_INFORMATION = "গর্ভাবস্থা সম্পর্কিত তথ্য"
        const val PREGNANCY_RESULT = "প্রসবের ফলাফল"
        const val DONE_HEALTH_SERVICE = "স্বাস্থ্য সেবা দেওয়া হয়েছে?"
        const val DONE_HEALTH_SERVICE_WITH_SPACE = "স্বাস্থ্য সেবা দেওয়া হয়েছে ?"
        const val DONE_HEALTH_EDUCATION = "স্বাস্থ্য শিক্ষা দেওয়া হয়েছে?"
        const val DONE_HEALTH_EDUCATION_NO_SPACE = "স্বাস্থ্যশিক্ষা দেওয়া হয়েছে?"
        const val DONE_REFER = "রেফার করা হয়েছে"
        const val FAMILY_PLANNING_METHOD = "পরিবার পরিকল্পনা পদ্ধতি"
        const val REFERRED_INSTITUTION_NAME = "রেফারকৃত প্রতিষ্ঠানের নাম?"
        const val PREGNANCY_DANGER = "গর্ভকালীন বিপদচিহ্ন আছে"
        const val POST_PREGNANCY_DANGER = "প্রসব পরবর্তী বিপদ চিহ্ন আছে"
        const val HEIGHT_NOT_MEASURABLE = "উচ্চতা পরিমাপ করা সম্ভব হচ্ছেনা"
        const val WEIGHT_NOT_MEASURABLE = "ওজন পরিমাপ করা সম্ভব হচ্ছেনা"
        const val NOT_MEASURABLE = "পরিমাপ করা সম্ভব হচ্ছেনা"
        const val BLOOD_PRESSURE_SITUATION_NOT_MEASURABLE = "রক্তচাপের অবস্থা পরিমাপ করা সম্ভব হচ্ছেনা"
        const val BLOOD_PRESSURE_NOT_MEASURABLE = "রক্তচাপ পরিমাপ করা সম্ভব হচ্ছেনা"

    }

    object UserKeys {
        const val USER_NAME = "username"
        const val PASSWORD = "password"
        const val HASHED_PASSWORD = "hashedPassword"
        const val USER_PERSON_NAME = "userDisplay"
        const val USER_UUID = "userUUID"
        const val LOGIN = "login"
        const val FIRST_TIME = "firstTime"
    }

    object DialogTAG {
        const val LOGOUT_DIALOG_TAG = "logoutDialog"
        const val END_VISIT_DIALOG_TAG = "endVisitDialogTag"
        const val START_VISIT_DIALOG_TAG = "startVisitDialog"
        const val START_VISIT_IMPOSSIBLE_DIALOG_TAG = "startVisitImpossibleDialog"
        const val WARNING_LOST_DATA_DIALOG_TAG = "warningLostDataDialog"
        const val SIMILAR_PATIENTS_TAG = "similarPatientsDialogTag"
        const val DELETE_PATIENT_DIALOG_TAG = "deletePatientDialogTag"
        const val DELETE_PROVIDER_DIALOG_TAG = "deleteProviderDialogTag"
        const val LOCATION_DIALOG_TAG = "locationDialogTag"
        const val CREDENTIAL_CHANGED_DIALOG_TAG = "locationDialogTag"
        const val MULTI_DELETE_PATIENT_DIALOG_TAG = "multiDeletePatientDialogTag"
    }

    object RegisterPatientRequirements {
        const val MAX_PATIENT_AGE = 120
    }

    object BundleKeys {
        const val CUSTOM_DIALOG_BUNDLE = "customDialogBundle"
        const val PATIENT_ID_BUNDLE = "patientID"
        const val COUNTRIES_BUNDLE = "countries_list"
        const val VISIT_ID = "visitID"
        const val ENCOUNTER_UUID = "encounterUuid"
        const val ENCOUNTERTYPE = "encounterType"
        const val VALUEREFERENCE = "valueReference"
        const val FORM_NAME = "formName"
        const val CALCULATED_LOCALLY = "CALCULATED_LOCALLY"
        const val PATIENTS_AND_MATCHES = "PATIENTS_AND_MATCHES"
        const val FORM_FIELDS_BUNDLE = "formFieldsBundle"
        const val FORM_FIELDS_LIST_BUNDLE = "formFieldsListBundle"
        const val FORM_PAGE_BUNDLE = "formPageBundle"
        const val PATIENT_QUERY_BUNDLE = "patientQuery"
        const val PATIENTS_START_INDEX = "patientsStartIndex"
        const val PROVIDER_BUNDLE = "providerID"
        const val ALLERGY_UUID = "allergy_uuid"
        const val PATIENT_UUID = "patient_uuid"
        const val PATIENT_ENTITY = "patient_entity"
        const val ENCOUNTER_ENTITY = "encounter_entity"
        const val FORM_TYPE = "form_type"
    }

    object ServiceActions {
        const val START_CONCEPT_DOWNLOAD_ACTION = "com.openmrs.mobile.services.conceptdownloadservice.action.startforeground"
        const val STOP_CONCEPT_DOWNLOAD_ACTION = "com.openmrs.mobile.services.conceptdownloadservice.action.stopforeground"
    }

    object BroadcastActions {
        const val CONCEPT_DOWNLOAD_BROADCAST_INTENT_ID = "com.openmrs.mobile.services.conceptdownloadservice.action.broadcastintent"
        const val CONCEPT_DOWNLOAD_BROADCAST_INTENT_KEY_COUNT = "com.openmrs.mobile.services.conceptdownloadservice.broadcastintent.key.count"
        const val AUTHENTICATION_CHECK_BROADCAST_ACTION = "org.intelehealth.app.mpower.services.AuthenticateCheckService"
    }

    object ServiceNotificationId {
        const val CONCEPT_DOWNLOADFOREGROUND_SERVICE = 101
    }

    object SystemSettingKeys {
        const val WS_REST_MAX_RESULTS_ABSOLUTE = "webservices.rest.maxResultsAbsolute"
    }

    object EncounterTypes {
        const val VITALS = "67a71486-1a54-468f-ac3e-7091a9a79584"

        @JvmField
        var ENCOUNTER_TYPES_DISPLAYS = arrayOf(EncounterType.VITALS, EncounterType.ADMISSION, EncounterType.DISCHARGE, EncounterType.VISIT_NOTE)
    }

    object RequestCodes {
        const val START_SETTINGS_REQ_CODE = 102
        const val IMAGE_REQUEST = 1
        const val GALLERY_IMAGE_REQUEST = 2
        const val FORM_DISPLAY_LOCAL_SUCCESS_CODE = 11
    }

    object ResultKeys {
        const val FORM_DISPLAY_LOCAL_SUCCESS_RESULT_KEY = "result_key"
    }

    object OpenMRSThemes {
        const val KEY_DARK_MODE = "key_dark_mode"
    }

    object OpenMRSlanguage {
        const val KEY_LANGUAGE_MODE = "key_language_mode"
        val LANGUAGE_LIST = arrayOf("English", "हिन्दी")
        val LANGUAGE_CODE = arrayOf("en", "hi")
    }

    object ShowCaseViewConstants {
        const val SHOW_FIND_PATIENT = 1
        const val SHOW_ACTIVE_VISITS = 2
        const val SHOW_REGISTER_PATIENT = 3
        const val SHOW_FORM_ENTRY = 4
        const val SHOW_MANAGE_PROVIDERS = 5
    }

    object TypeFacePathConstants {
        const val MONTSERRAT = "fonts/Roboto/Montserrat.ttf"
        const val ROBOTO_LIGHT = "fonts/Roboto/Roboto-Light.ttf"
        const val ROBOTO_LIGHT_ITALIC = "fonts/Roboto/Roboto-LightItalic.ttf"
        const val ROBOTO_MEDIUM = "fonts/Roboto/Roboto-Medium.ttf"
        const val ROBOTO_MEDIUM_ITALIC = "fonts/Roboto/Roboto-MediumItalic.ttf"
        const val ROBOTO_REGULAR = "fonts/Roboto/Roboto-Regular.ttf"
    }

    object PatientDashboardTabs {
        const val DETAILS_TAB_POS = 0
        const val ALLERGY_TAB_POS = 1
        const val DIAGNOSIS_TAB_POS = 2
        const val VISITS_TAB_POS = 3
        const val VITALS_TAB_POS = 4
        const val CHARTS_TAB_POS = 5
        const val TAB_COUNT = 6
    }

    object MemberProfileTabs {
        const val SERVICE_FORM = 0
        const val SERVICE_HISTORY = 1
        const val TAB_COUNT = 2
    }

    object ConceptDownloadService {
        const val CHANNEL_ID = "conceptCount"
        const val CHANNEL_DESC = "This channel receives new concept count notifications"
        const val CHANNEL_NAME = "Concepts Channel"
    }

    object AllergyModule {
        const val CONCEPT_ALLERGEN_DRUG = "allergy.concept.allergen.drug"
        const val CONCEPT_ALLERGEN_ENVIRONMENT = "allergy.concept.allergen.environment"
        const val CONCEPT_ALLERGEN_FOOD = "allergy.concept.allergen.food"
        const val CONCEPT_REACTION = "allergy.concept.reactions"
        const val CONCEPT_SEVERITY_MILD = "allergy.concept.severity.mild"
        const val CONCEPT_SEVERITY_MODERATE = "allergy.concept.severity.moderate"
        const val CONCEPT_SEVERITY_SEVERE = "allergy.concept.severity.severe"
        const val PROPERTY_FOOD = "FOOD"
        const val PROPERTY_DRUG = "DRUG"
        const val PROPERTY_OTHER = "OTHER"
        const val PROPERTY_MILD = "Mild"
        const val PROPERTY_MODERATE = "Moderate"
        const val PROPERTY_SEVERE = "Severe"
        const val SELECT_ALLERGEN = "Select Allergen"
        const val SELECT_REACTION = "Reactions (you can select multiple)"
    }
}
