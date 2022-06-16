package com.example.scrolling.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.scrolling.R
import com.example.scrolling.core.utility.observe
import com.example.scrolling.customClass.*
import com.example.scrolling.view.adapter.InfiniteImageListAdapter
import com.example.scrolling.view.adapter.PagingImageListAdapter
import com.example.scrolling.viewModel.ImageViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity(), PagingImageListAdapter.PagingImageListInterface{


    private val imageViewModel by viewModel<ImageViewModel>()

    lateinit var infiniteListAdapter: InfiniteImageListAdapter
    lateinit var infiniteLayoutManager: StaggeredGridLayoutManager

    lateinit var pagingListAdapter: PagingImageListAdapter
    lateinit var pagingLayoutManager: StaggeredGridLayoutManager

    var pageNumber: Int = 0
    private var isLastPages = false
    private var isLoadings = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setImageListsObservers()
        setInitAndShowPagingListOfImages()
        imageViewModel.getImageListApiOrDB()
        imageViewModel.getImageListApiOrDB(pageNumber)
    }

    fun setImageListsObservers(){
        observe(imageViewModel.infinitieImageListLiveData){
           if (!it.isNullOrEmpty()){
               setInitAndShowInfiniteListOfImages(it)
           }
        }
        observe(imageViewModel.pagingImageListLiveData){
            if (!it.isNullOrEmpty()){
                pagingListAdapter.addNewItems(it)
            }
        }
    }


    private fun setInitAndShowInfiniteListOfImages(imageList: ArrayList<String>) {
        if (!::infiniteListAdapter.isInitialized) {
            infiniteListAdapter = InfiniteImageListAdapter(this, imageList)
        }
        infiniteLayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL)
        rec_infinite_scrolling.apply {
            layoutManager = infiniteLayoutManager
            adapter = infiniteListAdapter
            addOnItemTouchListener(
                CustomRecyclerViewTouchListener(
                    this@MainActivity,
                    rec_infinite_scrolling,
                    object : ClickListener {
                        override fun onClick(view: View?, position: Int) {
                            showAlerterPositionOfList(position + 1 )
                        }

                        override fun onLongClick(view: View?, position: Int) {
                            showAlerterPositionOfList(position + 1)
                        }
                    })
            )
        }

        Handler(Looper.getMainLooper()).postDelayed({ rec_infinite_scrolling.startAutoScroll() }, 1000)


    }



    private fun setInitAndShowPagingListOfImages() {
        if (!::pagingListAdapter.isInitialized) {
            pagingListAdapter = PagingImageListAdapter(this, arrayListOf())
        }
        pagingLayoutManager =  StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL)
        rec_paging_scrolling.apply {
            layoutManager = pagingLayoutManager
            adapter = pagingListAdapter
        }
        initMoreDataHandler()
    }

    private fun initMoreDataHandler() {
        rec_paging_scrolling.addOnScrollListener(object :
            PaginationScrollListener(pagingLayoutManager) {
            override fun loadMoreItems() {
                isLoadings = true
                pageNumber++
                imageViewModel.getImageListApiOrDB(pageNumber)
            }

            override val isLastPage: Boolean = isLastPages
            override val isLoading: Boolean = isLoadings

        })
    }

    fun showAlerterPositionOfList(position: Int) {
        Alerter("تبریک", "شما بر روی عکس شماره ${rec_infinite_scrolling.calculateCorrectPosition(position)} کلیک کرده اید" + " (ردیف ${rec_infinite_scrolling.calculateCorrectRowNumber(position)} آیتم ${rec_infinite_scrolling.calculateCorrectColumnNumber(position)})")
    }

    override fun onItemClick(position: Int) {
        showAlerterPositionOfList(position + 1)
    }


}

