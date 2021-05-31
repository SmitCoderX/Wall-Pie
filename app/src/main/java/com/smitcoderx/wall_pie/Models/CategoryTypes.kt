package com.smitcoderx.wall_pie.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryTypes(
    val id: Int?,
    val bg: Int?,
    val type: String?
) : Parcelable
