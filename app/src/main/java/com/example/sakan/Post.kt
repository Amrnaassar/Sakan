package com.example.sakan

import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng

class Post{
    var desc: String? =null
    var image :String?=null
    var lat:Double?=null
    var log:Double?=null

    constructor(desc: String?,image: String?,lat: Double?,log:Double?)
    {
        this.desc=desc
        this.image=image
        this.lat=lat
        this.log=log

    }
    constructor(){}
}