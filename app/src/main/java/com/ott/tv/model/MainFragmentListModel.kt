package com.ott.tv.model

data class MainFragmentListModel(
    var title: String? = null,
    var dataInnerList: MutableList<InnerListModel>?= null
)
data class InnerListModel(
    var thumbnail: Int? = null,
    var title: String? = null
)

