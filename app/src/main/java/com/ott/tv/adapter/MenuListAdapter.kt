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
    private var checkedPosition = 0

    inner class ViewHolder(private val binding: LayoutMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun bindernew(dataModel: CategoryType) {
            binding.titleMenu.setText(dataModel.displayName)




            Glide.with(context)
                .load(dataModel.icon) /*.override(100,300)*/
                .error(R.drawable.commingsoon_sidenav)
                .placeholder(R.drawable.commingsoon_sidenav)
                .into(binding.icon)

            binding.menuLayout.setOnClickListener {

                //     Toast.makeText(context, "Clicked"+binding.titleMenu, Toast.LENGTH_LONG).show()
                Log.d("Clicking Event", "binder: clicked")
                mClick.onItemClick(dataList[position])

                //     mCallback.delegate(hasFocusLocalVar?:false)
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

                    //  binding.icon.setBackgroundResource(R.drawable.play_circle)
                    //    Toast.makeText(context, "Focused", Toast.LENGTH_SHORT).show()


                    /* binding.icon.imageTintList =
                         ColorStateList.valueOf(context.getColor(R.color.main_color))*/
                } else {
                    dataModel.isFocused = false
                    //   binding.icon.setBackgroundResource(R.drawable.ic_audiotrack_dark)
                    /*  binding.icon.imageTintList =
                          ColorStateList.valueOf(context.resources.getColor(android.R.color.white))*/
                }
            }
        }
        @SuppressLint("NotifyDataSetChanged")
        fun binder(dataModel: CategoryType) {
            binding.titleMenu.setText(dataModel.displayName)
            Glide.with(context)
                .load(dataModel.icon) /*.override(100,300)*/
                .error(R.drawable.commingsoon_sidenav)
                .placeholder(R.drawable.commingsoon_sidenav)
                .into(binding.icon)

            binding.menuLayout.setOnClickListener {

                //     Toast.makeText(context, "Clicked"+binding.titleMenu, Toast.LENGTH_LONG).show()

                Log.d("Clicking Event", "binder: clicked")
                mClick.onItemClick(dataList[position])

                //     mCallback.delegate(hasFocusLocalVar?:false)
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

                    //  binding.icon.setBackgroundResource(R.drawable.play_circle)
                    //    Toast.makeText(context, "Focused", Toast.LENGTH_SHORT).show()


                   /* binding.icon.imageTintList =
                        ColorStateList.valueOf(context.getColor(R.color.main_color))*/
                } else {
                    dataModel.isFocused = false
                    //   binding.icon.setBackgroundResource(R.drawable.ic_audiotrack_dark)
                  /*  binding.icon.imageTintList =
                        ColorStateList.valueOf(context.resources.getColor(android.R.color.white))*/
                }
            }
        }


        fun bind(dataModel: CategoryType) {
            binding.titleMenu.setText(dataModel.displayName)
            Glide.with(context)
                .load(dataModel.icon) /*.override(100,300)*/
                .error(R.drawable.commingsoon_sidenav)
                .placeholder(R.drawable.commingsoon_sidenav)
                .into(binding.icon)
          //  Log.d("Clicking Event", "binder: clicked"+dataList[position])
          //uncomment

            binding.menuLayout.setOnFocusChangeListener { v, hasFocus ->
                mCallback.delegate(hasFocus)
                hasFocusLocalVar = hasFocus

                if (hasFocus) {
                    //Select perticular menu
               //     binding.menuLayout.nextFocusDownId = R.id.menuRecycler

                    if (adapterPosition == dataList.size - 1) {
                        binding.menuLayout.nextFocusDownId = R.id.menuRecycler
                    } else if (adapterPosition == 0) {
                        binding.menuLayout.nextFocusUpId = R.id.menuRecycler
                    }else if(absoluteAdapterPosition==dataList.size){

                    }
                    dataModel.isFocused = true
                    binding.menuLayout.nextFocusRightId = R.id.list_items

                    Log.i("krantiloverboy", "focused "+adapterPosition +absoluteAdapterPosition)
                 //   binding.menuLayout.nextFocusRightId = R.id.list_items
                    //binding.icon.setBackgroundResource(R.drawable.play_circle)
                   // Toast.makeText(context, "Focused", Toast.LENGTH_SHORT).show()
                  //  binding.icon.imageTintList =
                    //    ColorStateList.valueOf(context.resources.getColor(android.R.color.white))
                } else {

                    Log.i("krantiloverboy", "nofocused ")
                    dataModel.isFocused = false
                    //binding.icon.setBackgroundResource(R.drawable.ic_audiotrack_dark)
                  //  binding.icon.imageTintList =
                //        ColorStateList.valueOf(context.resources.getColor(android.R.color.holo_red_dark))
                }
            }
            if (checkedPosition == 1) {
                binding.icon.imageTintList =
                    ColorStateList.valueOf(context.resources.getColor(android.R.color.white))
                //  imageView.setVisibility(View.GONE)
            } else {
                if (checkedPosition == adapterPosition) {
                    //  imageView.setVisibility(View.VISIBLE)
                    binding.icon.imageTintList =
                        ColorStateList.valueOf(context.getColor(R.color.main_color))
                } else {
                    //   imageView.setVisibility(View.GONE)
                    binding.icon.imageTintList =
                        ColorStateList.valueOf(context.resources.getColor(android.R.color.white))

                }
            }
            // textView.setText(employee.getName())
            //   binding.titleMenu.setText(dataModel.displayName)

        /*    itemView.setOnClickListener {
                //imageView.setVisibility(View.VISIBLE)
                binding.icon.imageTintList =
                    ColorStateList.valueOf(context.getColor(R.color.main_color))
                if (checkedPosition != adapterPosition) {
                    notifyItemChanged(checkedPosition)
                    checkedPosition = adapterPosition
                }
                Log.d("Clicking Event3", "binder: clicked"+dataList[position])

            }*/
            itemView.setOnClickListener {
                Log.d("Clicking Event4", "binder: clicked"+dataList[position])
                binding.icon.imageTintList =
                    ColorStateList.valueOf(context.getColor(R.color.main_color))
                if (checkedPosition != absoluteAdapterPosition) {
                    notifyItemChanged(checkedPosition)
                    checkedPosition = adapterPosition
                }
                Log.d("Clicking Event3", "binder: clicked"+dataList[position])
                mClick.onItemClick(dataList[position])

            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       //    holder.binder(dataList[position])

        holder.bind(dataList.get(position))

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface AdapterClick {
        fun onItemClick(data: CategoryType)
    }

}