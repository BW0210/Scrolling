package com.example.scrolling.customClass

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import timber.log.Timber

abstract class PaginationScrollListener constructor() :
    RecyclerView.OnScrollListener() {

    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    constructor(layoutManager: GridLayoutManager) : this() {
        this.mLayoutManager = layoutManager
    }

    constructor(layoutManager: StaggeredGridLayoutManager) : this() {
        this.mLayoutManager = layoutManager
    }

    constructor(layoutManager: LinearLayoutManager) : this() {
        this.mLayoutManager = layoutManager
    }

    /*
    Method gets callback when user scroll the search list
    */
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = mLayoutManager.childCount
        val totalItemCount = mLayoutManager.itemCount
        var firstVisibleItemPosition = 0

        when (mLayoutManager) {
            is StaggeredGridLayoutManager -> {
                val firstVisibleItemPositions =
                    (mLayoutManager as StaggeredGridLayoutManager).findFirstVisibleItemPositions(
                        null
                    )
                // get maximum element within the list
                firstVisibleItemPosition = firstVisibleItemPositions[0]
            }
            is GridLayoutManager -> {
                firstVisibleItemPosition =
                    (mLayoutManager as GridLayoutManager).findFirstVisibleItemPosition()
            }
            is LinearLayoutManager -> {
                firstVisibleItemPosition =
                    (mLayoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            }
        }
        if (!isLoading && !isLastPage) {
            /*if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= 7
            ) {
                Log.i(TAG, "Loading more items")
                loadMoreItems()
            }*/
            Timber.tag("MG").e("IFSTATE -> ${visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0}")
            Timber.tag("MG").e("visibleItemCount($visibleItemCount) + firstVisibleItemPosition($firstVisibleItemPosition) >=(${visibleItemCount + firstVisibleItemPosition >= totalItemCount}) totalItemCount($totalItemCount) && firstVisibleItemPosition($firstVisibleItemPosition) >=(${firstVisibleItemPosition >= 0 }) 0 && totalItemCount($totalItemCount) >=(${totalItemCount >= 15}) 15")

            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0/* && totalItemCount >= 15*/) {
                Timber.tag("MG").e("Load More Data *****************************************************")

                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()

    abstract val isLastPage: Boolean
    abstract val isLoading: Boolean

    companion object {
        private val TAG = PaginationScrollListener::class.java.simpleName
    }
}
