package com.ott.tv.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.media.Image
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ott.tv.BuildConfig
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
    private var iconSelectPosition: Int = 1
    private var oldpostionSecond: Int = 1
    val TAG = "MenuListAdapter"

    var row_index = -1


    inner class ViewHolder(private val binding: LayoutMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.M)
        @SuppressLint("NotifyDataSetChanged")
        fun bindernew(dataModel: CategoryType) {
            Log.d("Clicking Event", "binder: clicked2--" + dataModel.icon + dataModel.displayName)
            binding.titleMenu.setText(dataModel.displayName)

            if (BuildConfig.FLAVOR.equals("nano_media", ignoreCase = true)) {

                if (dataModel.displayName.contentEquals("UV Bharat", ignoreCase = true)) {
                    binding.titleMenu.setText("News Channel")

                }
                if (dataModel.displayName.contentEquals("UV Live", ignoreCase = true)) {
                    binding.titleMenu.setText("Live")

                }


            } else {
                binding.titleMenu.setText(dataModel.displayName)

            }

            Glide.with(context)
                .load(dataModel.icon) /*.override(100,300)*/
                .error(R.drawable.commingsoon_sidenav)
                .placeholder(R.drawable.commingsoon_sidenav)
                .into(binding.icon)




            binding.menuLayout.setOnClickListener {
                mCallback.delegate(hasFocusLocalVar ?: false)
                mClick.onItemClick(dataList[position])
                Log.d("Clickingkranti", "binder: clicked2--" + dataModel.icon + dataModel.displayName)

                  row_index=position

        /*          if (row_index==position) {
                      binding.icon.imageTintList =
                          ColorStateList.valueOf(context.getColor(R.color.red_highlight_color))
                  } else {
                      binding.icon.imageTintList =
                          ColorStateList.valueOf(context.getColor(R.color.lb_browse_title_color))
                  }*/

              //   notifyItemChanged(oldpostionSecond)
                binding.icon.imageTintList =
                    ColorStateList.valueOf(context.getColor(R.color.red_highlight_color))
                //notifyDataSetChanged();


                //   redColor()
            }


            /* binding.menuLayout.setOnClickListener {
                 oldclick = false
                 Log.e("@@", oldpostion.toString())
                 Log.e("@@", oldpostion.toString())

                 if (oldpostion == absoluteAdapterPosition) {

                 } else {
                     if (absoluteAdapterPosition == 0) {
                         mClick.onItemClick(dataList[position])
                     } else {
                         oldclick = true
                         oldpostionSecond = oldpostion
                         Log.d(
                             "Clicking Event",
                             "binder: clicked2--" + oldpostionSecond + oldpostion + position + oldclick
                         )
                         notifyItemChanged(oldpostionSecond)
                         redColor()
                     }
                 }
             }*/


            binding.menuLayout.setOnFocusChangeListener { v, hasFocus ->
                Log.i(TAG, "delegate: menu2")

                mCallback.delegate(hasFocus)
                hasFocusLocalVar = hasFocus

                Log.d(
                    "Clicking Event focus",
                    "binder: clicked2--" + hasFocus + absoluteAdapterPosition + dataList.size
                )

                if (hasFocus) {

                    if (absoluteAdapterPosition == dataList.size - 1) {
                        binding.menuLayout.nextFocusDownId = R.id.menuRecycler
                    } else if (absoluteAdapterPosition == 0) {
                        binding.menuLayout.nextFocusUpId = R.id.menuRecycler
                    }
                    dataModel.isFocused = true
                    binding.menuLayout.nextFocusRightId = R.id.list_items
  /*                  binding.icon.imageTintList =
                        ColorStateList.valueOf(context.getColor(R.color.red_highlight_color))
  */              } else {

                    dataModel.isFocused = false
    /*                binding.icon.imageTintList =
                        ColorStateList.valueOf(context.getColor(R.color.white))
    */            }
            }
        }


        @SuppressLint("NotifyDataSetChanged")
        fun colorwhite(dataModel: CategoryType) {
            Glide.with(context)
                .load(dataModel.icon) /*.override(100,300)*/
                .error(R.drawable.commingsoon_sidenav)
                .placeholder(R.drawable.commingsoon_sidenav)
                .into(binding.icon)
            Log.d(
                "Clicking Eventkranti",
                "binder: clicked2--" + dataModel.icon + dataModel.displayName + BuildConfig.FLAVOR
            )
            binding.titleMenu.setText(dataModel.displayName)


            binding.icon.imageTintList =
                ColorStateList.valueOf(context.getColor(R.color.white))
            oldpostionSecond = oldpostion


        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun redColor() {
            Log.d("Clicking Event", "binder: clicked2 Redjum--")
            if (BuildConfig.FLAVOR.equals("uvtv", ignoreCase = true)) {
                binding.icon.imageTintList =
                    ColorStateList.valueOf(context.getColor(R.color.red_highlight_color))
                oldpostion = absoluteAdapterPosition
            } else {
                binding.icon.imageTintList =
                    ColorStateList.valueOf(context.getColor(R.color.side_nav_selected))
                oldpostion = absoluteAdapterPosition
            }


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
            "binder: clicked new --" + "old-" + oldpostionSecond + oldpostion + position + oldclick
        )
        val icon = ContextCompat.getDrawable(holder.itemView.context, R.drawable.default_video)
     //   holder.iconView.setImageDrawable(icon)
        holder.bindernew(dataList[position])

        /*if (oldclick == true) {
            oldclick = false
            holder.colorwhite(dataList[position])
            holder.bindernew(dataList[position])

        } else {
            Log.d(
                "Clicking Event",
                "binder: clicked new --else" + "old-" + oldpostionSecond + oldpostion + position + oldclick
            )
            holder.bindernew(dataList[position])
        }*/

        /*   if(row_index==position){
              // holder.itemView.findViewById<ImageView>(R.id.icon).setBackgroundColor((Color.parseColor("#00ff00")))
               holder.colorwhite(dataList[position])


           }
           else
           {
               holder.itemView.findViewById<ImageView>(R.id.icon).setBackgroundColor((Color.parseColor("#ff00ff")))
      *//*         holder.row_linearlayout.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.tv1.setTextColor(Color.parseColor("#000000"));
   *//*     }
*/

    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    interface AdapterClick {
        fun onItemClick(data: CategoryType)
    }

}