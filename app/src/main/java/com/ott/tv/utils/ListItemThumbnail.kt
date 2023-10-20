package com.ott.tv.utils

import com.ott.tv.network.ThumbnailResizer

class ListItemThumbnail(imageOrientation: Int? = 0) : ThumbnailResizer {
    val WIDTH_SCALE_FACTOR = 0.40
    var HEIGHT_SCALE_FACTOR = 0.56

    init {
        imageOrientation?.let {
            if (it == 1) {
                HEIGHT_SCALE_FACTOR = 1.33
            }
        }
    }





    private var screenWidth: Int = 150

    override fun getHeight(): Int {
        return (getWidth() * HEIGHT_SCALE_FACTOR).toInt()
    }

    override fun getWidth(): Int {
        return (screenWidth * WIDTH_SCALE_FACTOR).toInt()
    }



}