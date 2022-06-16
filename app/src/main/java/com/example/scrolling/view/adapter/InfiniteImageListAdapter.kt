package com.example.scrolling.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.scrolling.R
import com.example.scrolling.core.base.BaseViewHolder
import kotlinx.android.synthetic.main.row_item_main_image_list.view.*
import timber.log.Timber


class InfiniteImageListAdapter(
        val mContext: Context,
        val dataItem: ArrayList<String>
) :
        RecyclerView.Adapter<BaseViewHolder>() {


    var initMarginFlag = false

    init {
        initMarginFlag = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = BaseViewHolder(
                LayoutInflater.from(mContext).inflate(
                        R.layout.row_item_main_image_list,
                        parent,
                        false
                )
        )
        return view
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        dataItem[position % dataItem.size].apply {
            holder.itemView.let { view ->


                Glide.with(mContext)
                        .load(this)
                        .placeholder(R.drawable.ic_default_image)
                        .into(view.img_row_item_main_image_list)



                    view.post {
                        setMarginToStart(
                            view,
                            if (position == 1 && !initMarginFlag) {
                                initMarginFlag = true
                                view.width / 2
                            } else 0
                        )
                    }


            }
        }


    }


    private fun setMarginToStart(view: View, marginRight: Int) {
        val layoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        layoutParams.marginStart = marginRight
        view.layoutParams = layoutParams
    }

}
