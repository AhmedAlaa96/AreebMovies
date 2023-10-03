package com.ahmed.areebmovies.data.models.view_state

import android.os.Parcel
import android.os.Parcelable

class ViewPagerState(val currentItem: Int) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(currentItem)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ViewPagerState> {
        override fun createFromParcel(parcel: Parcel): ViewPagerState {
            return ViewPagerState(parcel)
        }

        override fun newArray(size: Int): Array<ViewPagerState?> {
            return arrayOfNulls(size)
        }
    }
}
