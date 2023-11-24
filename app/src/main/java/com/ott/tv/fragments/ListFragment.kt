package com.ott.tv.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*
import com.ott.tv.model.BrowseData
import com.ott.tv.model.phando.LatestMovieList
import com.ott.tv.ui.ItemPresenter
import com.ott.tv.ui.presenter.HorizontalCardPresenter
import com.ott.tv.ui.presenter.HorizontalCardPresenterNew


class ListFragment : RowsSupportFragment() {

    private var itemSelectedListener: ((LatestMovieList) -> Unit)? = null
    private var itemClickListener: ((LatestMovieList) -> Unit)? = null
    private var rootAdapter: ArrayObjectAdapter =
        ArrayObjectAdapter(ListRowPresenter(FocusHighlight.ZOOM_FACTOR_MEDIUM))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = rootAdapter

        onItemViewSelectedListener = ItemViewSelectedListener()
        onItemViewClickedListener = ItemViewClickListener()
    }

    fun bindData(dashboardItems: List<BrowseData>) {
        dashboardItems.forEach { dashboardItem ->
            val arrayObjectAdapter = ArrayObjectAdapter(HorizontalCardPresenterNew("1"))



            dashboardItem.list.forEach {
                it.viewallTitle = dashboardItem.title
                arrayObjectAdapter.add(it)
            }


            if(dashboardItem.title.equals("Home Slider")){
                val headerItem = HeaderItem("Top Deals")
                val listRow = ListRow(headerItem, arrayObjectAdapter)
                rootAdapter.add(listRow)

            }else{
                val headerItem = HeaderItem(dashboardItem.title)
                val listRow = ListRow(headerItem, arrayObjectAdapter)
                rootAdapter.add(listRow)
            }


        }
    }

    fun setOnContentSelectedListener(listener: (LatestMovieList) -> Unit) {
        this.itemSelectedListener = listener
    }

    fun setOnItemClickListener(listener: (LatestMovieList) -> Unit) {
        this.itemClickListener = listener
    }

    inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            if (item is LatestMovieList) {
                itemSelectedListener?.invoke(item)
            }
        }
    }

    inner class ItemViewClickListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            if (item is LatestMovieList) {
                itemClickListener?.invoke(item)
            }
        }
    }
}
