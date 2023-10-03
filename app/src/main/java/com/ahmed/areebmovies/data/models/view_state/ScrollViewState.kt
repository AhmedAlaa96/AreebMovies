package com.ahmed.areebmovies.data.models.view_state

import android.os.Parcel
import android.os.Parcelable

class ScrollViewState(val scroll: Int) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(scroll)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScrollViewState> {
        override fun createFromParcel(parcel: Parcel): ScrollViewState {
            return ScrollViewState(parcel)
        }

        override fun newArray(size: Int): Array<ScrollViewState?> {
            return arrayOfNulls(size)
        }
    }
}
