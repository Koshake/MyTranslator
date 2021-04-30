package com.koshake1.model.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Translation(
    @Expose val text : String?
) : Parcelable
