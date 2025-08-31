package com.example.boxcricinfo

import android.os.Parcel
import android.os.Parcelable

data class PlayerInfo(
    var name: String,
    var photoUri: String? = null // Use String for simplicity, can be Uri
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(photoUri)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<PlayerInfo> {
        override fun createFromParcel(parcel: Parcel): PlayerInfo {
            return PlayerInfo(parcel)
        }
        override fun newArray(size: Int): Array<PlayerInfo?> {
            return arrayOfNulls(size)
        }
    }
}
