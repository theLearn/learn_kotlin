package com.example.hongcheng.learn_kotlin.ui.adapter.models

import android.databinding.BaseObservable
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by hongcheng on 17/6/1.
 */

class AnimationModel (): BaseObservable(), Parcelable {
    var name: String? = null
    var imageUrl: String? = null
    var description: String? = null
    var type: String? = null

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<AnimationModel> = object : Parcelable.Creator<AnimationModel> {
            override fun createFromParcel(source: Parcel): AnimationModel = AnimationModel(source)
            override fun newArray(size: Int): Array<AnimationModel?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this()
    {
        this.name = source.readString()
        this.description = source.readString()
        this.imageUrl = source.readString()
        this.type = source.readString()
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(description)
        dest.writeString(imageUrl)
        dest.writeString(type)
    }

    constructor(name: String, imageUrl: String, description: String, type: String) : this()
    {
        this.name = name
        this.imageUrl = imageUrl
        this.description = description
        this.type = type
    }
}