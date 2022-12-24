package com.ott.tv.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ott.tv.R
import com.ott.tv.databinding.LayoutMenuBinding
import com.ott.tv.model.phando.CategoryType
import com.ott.tv.utils.ICallback

@Suppress("DEPRECATION")
class MenuListAdapter(
    val context: Context,
    val dataList: List<CategoryType> = ArrayList<CategoryType>(),

    var mClick: AdapterClick,
    val mCallback: ICallback
) :
    RecyclerView.Adapter<MenuListAdapter.ViewHolder>() {
    var hasFocusLocalVar: Boolean? = null
    var hasclicked: Boolean? = false
    var oldclick: Boolean? = false
    private var checkedPosition = 0
    private var oldpostion: Int = 1
    private var oldpostionSecond: Int = 1


    inner class ViewHolder(private val binding: LayoutMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun bindernew(dataModel: CategoryType) {
            Log.d("Clicking Event", "binder: clicked2--" +dataModel.icon +dataModel.displayName)

            binding.titleMenu.setText(dataModel.displayName)
            Glide.with(context)
                .load(dataModel.icon) /*.override(100,300)*/
                .error(R.drawable.commingsoon_sidenav)
                .placeholder(R.drawable.commingsoon_sidenav)
                .into(binding.icon)

            binding.menuLayout.setOnClickListener {
                oldclick=false
                Log.d("Clicking Event", "binder: clicked2--" + oldpostionSecond+oldpostion + position+oldclick)

                if (oldpostion == absoluteAdapterPosition) {

                } else {
                    if (absoluteAdapterPosition == 0) {
                        mClick.onItemClick(dataList[position])
                    } else {
                        oldclick = true
                        oldpostionSecond=oldpostion
                        Log.d("Clicking Event", "binder: clicked2--" +oldpostionSecond+ oldpostion + position+oldclick)

                        notifyItemChanged(oldpostionSecond)
                        redColor()
                    }
                }
            }


            binding.menuLayout.setOnFocusChangeListener { v, hasFocus ->
                mCallback.delegate(hasFocus)
                hasFocusLocalVar = hasFocus


                //   Toast.makeText(context, "$hasFocus", Toast.LENGTH_SHORT).show()

                if (hasFocus) {
                    if (adapterPosition == dataList.size - 1) {
                        binding.menuLayout.nextFocusDownId = R.id.menuRecycler
                    } else if (adapterPosition == 0) {
                        binding.menuLayout.nextFocusUpId = R.id.menuRecycler
                    }
                    dataModel.isFocused = true
                    binding.menuLayout.nextFocusRightId = R.id.list_items

                } else {
                    dataModel.isFocused = false
                }
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        fun colorwhite(dataModel: CategoryType) {
            Glide.with(context)
                .load(dataModel.icon) /*.override(100,300)*/
                .error(R.drawable.commingsoon_sidenav)
                .placeholder(R.drawable.commingsoon_sidenav)
                .into(binding.icon)
            Log.d("Clicking Event", "binder: clicked2--" +dataModel.icon +dataModel.displayName)
            binding.titleMenu.setText(dataModel.displayName)
            binding.icon.imageTintList =
                ColorStateList.valueOf(context.getColor(R.color.white))
            oldpostionSecond=oldpostion


        }

        fun redColor() {
            Log.d("Clicking Event", "binder: clicked2 Redjum--" )

            binding.icon.imageTintList =
                ColorStateList.valueOf(context.getColor(R.color.main_color))
            oldpostion = absoluteAdapterPosition
            mClick.onItemClick(dataList[position])

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(
            "Clicking Event",
            "binder: clicked new --"  + "old-" +oldpostionSecond+ oldpostion +position+ oldclick
        )


        if (oldclick == true) {
            oldclick = false
          //  holder.colorwhite(dataList[position])
          holder.bindernew(dataList[position])

        } else {
            Log.d(
                "Clicking Event",
                "binder: clicked new --else"  + "old-" +oldpostionSecond+ oldpostion +position+ oldclick
            )
            holder.bindernew(dataList[position])
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface AdapterClick {
        fun onItemClick(data: CategoryType)
    }

}