package com.ahmed.areebmovies.data.models.view_state

import android.os.Parcel
import android.os.Parcelable

class BaseViewStateModel(var visibility: Int, var data: Parcelable?) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable(BaseViewStateModel::data::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(visibility)
        parcel.writeParcelable(data, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseViewStateModel> {
        override fun createFromParcel(parcel: Parcel): BaseViewStateModel {
            return BaseViewStateModel(parcel)
        }

        override fun newArray(size: Int): Array<BaseViewStateModel?> {
            return arrayOfNulls(size)
        }
    }
}
