package com.example.gitapp

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Repository(
    val id: Int,
    val name: String,
    val description: String?,
    @SerializedName("stargazers_count") val stars: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(stars)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Repository> {
        override fun createFromParcel(parcel: Parcel): Repository = Repository(parcel)
        override fun newArray(size: Int): Array<Repository?> = arrayOfNulls(size)
    }
}