package com.ott.tv.network.api

import com.ott.tv.model.Video
import java.io.Serializable

class StateChannelResponse : Serializable {
    var id: Int = 0
    var title: String? = null
    var type: String? = null
    var description: String? = null
    var url: String? = null
    var thumbnail: String? = null
    var states: String? = null


    override fun toString(): String {
        return ("PlaybackModel{"
                + "id="
                + id
                + ", title='"
                + title
                + '\''
                + ", description='"
                + description
                + '\''
                + ", type='"
                + type
                + '\''
                + ", url='"
                + url
                + '\''
                + ", thumbnail='"
                + thumbnail
                + '\''
                + ", states='"
                + states
               )
    }
}
