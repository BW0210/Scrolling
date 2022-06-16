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
import com.example.scrolling.core.utility.calculatorData
import kotlinx.android.synthetic.main.row_item_main_image_list.view.*

class PagingImageListAdapter(
    val mContext: Context,
    val dataItem: ArrayList<String>
) :
    RecyclerView.Adapter<BaseViewHolder>() {

    lateinit var mInterface : PagingImageListInterface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.row_item_main_image_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataItem.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        dataItem[position % dataItem.size].apply {
            holder.itemView.let { view ->

                mInterface = mContext as PagingImageListInterface

                Glide.with(mContext)
                    .load(this)
                    .placeholder(R.drawable.ic_default_image)
                    .into(view.img_row_item_main_image_list)


                view.post {
                    setMarginToStart(
                        view,
                        if (position == 1) view.width / 2 else 0
                    )
                }

                view.setOnClickListener {
                    mInterface.onItemClick(position)
                }


            }
        }


    }


    private fun setMarginToStart(view: View, marginRight: Int) {
        val layoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        layoutParams.marginStart = marginRight
        view.layoutParams = layoutParams
    }

    fun addNewItems(dataItems: ArrayList<String>) {
        calculatorData(dataItems) {
            dataItem.add(it)
            notifyItemInserted(dataItem.size - 1)
        }
    }

    interface PagingImageListInterface{
        fun onItemClick(position: Int)
    }

}
