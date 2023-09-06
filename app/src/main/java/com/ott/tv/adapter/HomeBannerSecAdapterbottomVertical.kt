package com.ott.tv.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ott.tv.Config
import com.ott.tv.R
import com.ott.tv.model.BrowseData
import com.ott.tv.model.phando.LatestMovieList
import com.ott.tv.ui.activity.DetailsActivityPhando
import com.ott.tv.ui.activity.ItemCountryActivity
import com.ott.tv.video_service.VideoPlaybackActivity

class HomeBannerSecAdapterbottomVertical(
    private val listdata: BrowseData,
    private val context: Context
) : RecyclerView.Adapter<HomeBannerSecAdapterbottomVertical.ViewHolder?>() {
    interface SendInterfaceClickSec {
        fun sendclickSec()
    }

    var sendInterfaceClickSec: SendInterfaceClickSec? = null
    fun setSendInterfaceClick(sendInterfaceClick: SendInterfaceClickSec?) {
        sendInterfaceClickSec = sendInterfaceClick
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem =
            layoutInflater.inflate(R.layout.banner_home_list_item_vertical, parent, false)
        return ViewHolder(listItem)
    }

    interface SendInterfaceDataBottom {
        fun sendDescriptionBottom(description: LatestMovieList?)
    }

    var SendInterfaceDataBottomVertical: SendInterfaceDataBottom? = null
    fun setSendInterfacedata(SendInterfaceDataBottom: SendInterfaceDataBottom?) {
        SendInterfaceDataBottomVertical = SendInterfaceDataBottom
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        /*  if (listdata.getTitle().equalsIgnoreCase("Continue watching")) {
            holder.progressBarMovie.setVisibility(View.VISIBLE);
            int total_runtime;
            int percentageProgressbar;
            int watch_runtime;
            if (listdata.getList().get(position) != null) {
                if (listdata.getList().get(position).getRuntime_in_minutes() != null) {
                    total_runtime = Integer.parseInt(listdata.getList().get(position).getRuntime_in_minutes());
                } else {
                    total_runtime = 0;
                }
                if(listdata.getList().get(position).continue_watch_minutes!=null){
                if (listdata.getList().get(position).continue_watch_minutes.getLast_watched_at() != null) {
                    watch_runtime = Integer.parseInt(listdata.getList().get(position).continue_watch_minutes.getLast_watched_at()) / 60;
                } else {
                    watch_runtime = 100;

                }
                }
                else{
                    watch_runtime = 100;

                }

                Log.i("continuewatching", watch_runtime + "--totaltime=" + total_runtime);

                if (total_runtime == 0) {
                    percentageProgressbar = 100;
                } else {
                    percentageProgressbar = (Integer) ((watch_runtime / total_runtime) * 100);
                }
                Log.i("continuewatching2", "" + percentageProgressbar);
                holder.progressBarMovie.setProgress(percentageProgressbar);
                holder.progressBarMovie.getProgressDrawable().setColorFilter(
                        Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);


            } else {
                holder.progressBarMovie.setVisibility(View.GONE);
            }
        } else {

        }*/
        if (true) {
            /*if (position == 5) {
                holder.primary_text.setText(listdata.getVideos().get(position).getTitle());
                if (!TextUtils.isEmpty(listdata.getVideos().get(position).getPosterUrl()))
                    Glide.with(context)
                            .load(R.drawable.movie_1).
                            placeholder(R.drawable.poster_placeholder_land)
                            .error(R.drawable.poster_placeholder_land)
                            .into(holder.main_image);
            } else*/
            if (listdata.list[position] != null) {
                holder.title_name.text = listdata.list[position].title
            }

            /*   if (listdata.getList().get(position).getIsPaid()!=null) {
            if (listdata.getList().get(position).getIsPaid().equalsIgnoreCase("0")) {
                holder.premiumIconImage.setVisibility(View.GONE);
            } else {
                holder.premiumIconImage.setVisibility(View.VISIBLE);
            }}*/if (listdata.list[position].is_free != null) {
                if (listdata.list[position].is_free.toString().equals("1", ignoreCase = true)) {
                    holder.premiumIconImage.visibility = View.GONE
                } else {
                    holder.premiumIconImage.visibility = View.VISIBLE
                }
            }
            holder.primary_text.text = listdata.list[position].title

      /*      if (!TextUtils.isEmpty(listdata.list[position].thumbnail)) {
                Glide.with(context)
                    .load(listdata.list[position].thumbnail)
                    .placeholder(R.drawable.poster_placeholder_land)
                    .error(R.drawable.poster_placeholder_land)
                    .into(holder.main_image)
            }*/

                Glide.with(context)
                    .load(listdata.list[position].poster)
                    .placeholder(R.drawable.poster_placeholder_land)
                    .error(R.drawable.poster_placeholder_land)
                    .into(holder.main_image)

            holder.relativeLayout_parent.setOnClickListener { view: View? ->
                //   Toast.makeText(view.getContext(), "click on item: " + position, Toast.LENGTH_LONG).show();
                listdata.list[position].viewallName = listdata.title
                detailActivity(listdata.list[position])
                if (sendInterfaceClickSec != null) {
                    sendInterfaceClickSec!!.sendclickSec()
                }
            }
            holder.relativeLayout_parent.onFocusChangeListener =
                OnFocusChangeListener { view: View?, b: Boolean ->
                    if (listdata.list[position].title != null) {
                        //setTextViewBanner(myListData.getDescription());
                        Log.i(
                            VideoPlaybackActivity.TAG,
                            "onFocusChange: " + listdata.list[position].title
                        )
                        if (SendInterfaceDataBottomVertical != null) {
                            SendInterfaceDataBottomVertical!!.sendDescriptionBottom(listdata.list[position])
                        }
                    }
                }
        }
    }

    override fun getItemCount(): Int {
        Log.d(VideoPlaybackActivity.TAG, "getItemCount2: $listdata")
        /* if (listdata.getList().size() > 6) {
            return 6;
        } else*/run { return listdata.list.size }
    }

    protected fun updateCardViewImage(url: String?) {}
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var main_image: ImageView
        var premiumIconImage: ImageView
        var primary_text: TextView
        var title_name: TextView
        var progressBarMovie: ProgressBar
        var relativeLayout_parent: RelativeLayout

        init {
            main_image = itemView.findViewById<View>(R.id.main_image) as ImageView
            premiumIconImage = itemView.findViewById<View>(R.id.premiumIconImage) as ImageView
            primary_text = itemView.findViewById<View>(R.id.primary_text) as TextView
            title_name = itemView.findViewById<View>(R.id.title_name) as TextView
            progressBarMovie = itemView.findViewById<View>(R.id.progress_bar_movie) as ProgressBar
            relativeLayout_parent =
                itemView.findViewById<View>(R.id.relativeLayout) as RelativeLayout
        }
    }

    private fun detailActivity(video: LatestMovieList) {
        if (video.type == null) {
            video.type = "M"
        }
        run {
            if (video.type.equals("VM", ignoreCase = true) || video.type.equals(
                    "GENRE",
                    ignoreCase = true
                )
            ) {
                val intent = Intent(context, ItemCountryActivity::class.java)
                intent.putExtra("id", video.id.toString())
                intent.putExtra("title", video.viewallName)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
            if (video.type.equals("OTT", ignoreCase = true)) {
                if (video.is_subscribed != null) {
                    if (video.is_subscribed.toString().equals("0", ignoreCase = true)) {
                        //  CMHelper.setSnackBar(context, "Enjoy Premium Content Watch anything without ads Watch  Please Subscribe Or Rent  from MOBILE APP | WEBSITE -" + Config.WebsiteURL, 1, 10000);
                        // CMHelper.setSnackBar(,  "Enjoy Premium Content Watch anything without ads Watch  Please Subscribe Or Rent  from MOBILE APP | WEBSITE -" + Config.WebsiteURL, 2);
                        Toast.makeText(
                            context,
                            "Unlock Exclusive Content By Subscribing Today from MOBILE APP | WEBSITE -" + Config.WebsiteURL,
                            Toast.LENGTH_LONG
                        ).show()
                        return
                    }
                }
                //   Intent intent = new Intent(context, ItemCountryActivity.class);
                if (video.android_tv_link != null) {
                    var intent = context.packageManager.getLaunchIntentForPackage(
                        video.android_tv_link.substring(video.android_tv_link.lastIndexOf("=") + 1)
                    )
                    if (intent == null) {
                        intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(video.android_tv_link)
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)

                } else {
                    var intent = context.packageManager.getLaunchIntentForPackage(
                        video.android_link.substring(video.android_link.lastIndexOf("=") + 1)
                    )
                    if (intent == null) {
                        intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(video.android_link)
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }
            if (video.type == "M" && video.is_live.toString().equals("0", ignoreCase = true)) {
                val intent = Intent(context, DetailsActivityPhando::class.java)
                if (video.type != null) intent.putExtra("type", video.type)
                if (video.thumbnail != null) {
                    intent.putExtra("thumbImage", video.thumbnail)
                } else {
                    if (video.thumbnailUrl != null) intent.putExtra(
                        "thumbImage",
                        video.thumbnailUrl
                    )
                }
                if (video.id != null) {
                    intent.putExtra("video_id", video.id.toString())
                } else {
                    if (video.id != null) intent.putExtra("video_id", video.id.toString())
                }
                if (video.title != null) intent.putExtra("title", video.title)
                if (video.detail != null) {
                    intent.putExtra("description", video.detail)
                } else {
                    if (video.description != null) intent.putExtra("description", video.description)
                }
                if (video.release_date != null) {
                    intent.putExtra("release", video.release_date)
                } else {
                    if (video.release != null) {
                        intent.putExtra("release", video.release)
                    }
                }
                /*   if (videoContent.getRuntime() != null)
                    intent.putExtra("duration", videoContent.getRuntime());*/if (video.duration_str != null) intent.putExtra(
                    "duration",
                    video.duration_str
                )
                if (video.maturity_rating != null) intent.putExtra(
                    "maturity_rating",
                    video.maturity_rating
                )
                if (video.is_free != null) intent.putExtra("ispaid", video.is_free.toString())
                if (video.language_str != null) intent.putExtra("language_str", video.language_str)
                if (video.is_live != null) intent.putExtra("is_live", video.is_live.toString())
                if (video.rating != null) intent.putExtra("rating", video.rating.toString())
                if (video.trailers != null && video.trailers.size > 0 && video.trailers[0] != null && video.trailers[0].media_url != null) {
                    intent.putExtra("trailer", video.thumbnailUrl)
                }
                if (video.trailer_aws_source != null) {
                    intent.putExtra("trailer", video.trailer_aws_source)
                }
                if (video.genres != null) {
                    if (video.genres.size != 0) {
                        if (video.genres.size > 0) {
                            var genres: String
                            genres = video.genres[0]
                            for (i in 1 until video.genres.size) {
                                genres = genres + "," + video.genres[i]
                            }
                            intent.putExtra("genres", genres)
                        }
                    }
                } else {
                    if (video.genre != null) {
                        intent.putExtra("genres", video.genre)
                    }
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
            if (video.type == "E" && video.is_live.toString().equals("0", ignoreCase = true)) {
                val intent = Intent(context, DetailsActivityPhando::class.java)
                if (video.type != null) intent.putExtra("type", video.type)
                if (video.thumbnail != null) {
                    intent.putExtra("thumbImage", video.thumbnail)
                } else {
                    if (video.thumbnailUrl != null) intent.putExtra(
                        "thumbImage",
                        video.thumbnailUrl
                    )
                }
                if (video.id != null) {
                    intent.putExtra("video_id", video.id.toString())
                } else {
                    if (video.id != null) intent.putExtra("video_id", video.id.toString())
                }
                if (video.title != null) intent.putExtra("title", video.title)
                if (video.detail != null) {
                    intent.putExtra("description", video.detail)
                } else {
                    if (video.description != null) intent.putExtra("description", video.description)
                }
                if (video.release_date != null) {
                    intent.putExtra("release", video.release_date)
                } else {
                    if (video.release != null) {
                        intent.putExtra("release", video.release)
                    }
                }
                /*   if (videoContent.getRuntime() != null)
                    intent.putExtra("duration", videoContent.getRuntime());*/if (video.duration_str != null) intent.putExtra(
                    "duration",
                    video.duration_str
                )
                if (video.maturity_rating != null) intent.putExtra(
                    "maturity_rating",
                    video.maturity_rating
                )
                if (video.is_free != null) intent.putExtra("ispaid", video.is_free.toString())
                if (video.language_str != null) intent.putExtra("language_str", video.language_str)
                if (video.is_live != null) intent.putExtra("is_live", video.is_live.toString())
                if (video.rating != null) intent.putExtra("rating", video.rating.toString())
                if (video.trailers != null && video.trailers.size > 0 && video.trailers[0] != null && video.trailers[0].media_url != null) {
                    intent.putExtra("trailer", video.thumbnailUrl)
                }
                if (video.trailer_aws_source != null) {
                    intent.putExtra("trailer", video.trailer_aws_source)
                }
                if (video.genres != null) {
                    if (video.genres.size != 0) {
                        if (video.genres.size > 0) {
                            var genres: String
                            genres = video.genres[0]
                            for (i in 1 until video.genres.size) {
                                genres = genres + "," + video.genres[i]
                            }
                            intent.putExtra("genres", genres)
                        }
                    }
                } else {
                    if (video.genre != null) {
                        intent.putExtra("genres", video.genre)
                    }
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
            if (video.type == "T") {
                val intent = Intent(context, DetailsActivityPhando::class.java)
                if (video.type != null) intent.putExtra("type", video.type)
                if (video.thumbnail != null) {
                    intent.putExtra("thumbImage", video.thumbnail)
                } else {
                    if (video.thumbnailUrl != null) {
                        intent.putExtra("thumbImage", video.thumbnailUrl)
                    }
                }
                if (video.id != null) {
                    intent.putExtra("video_id", video.id.toString())
                }
                if (video.title != null) intent.putExtra("title", video.title)
                if (video.detail != null) {
                    intent.putExtra("description", video.detail)
                } else {
                    if (video.description != null) intent.putExtra("description", video.description)
                }
                if (video.release_date != null) {
                    intent.putExtra("release", video.release_date)
                } else {
                    if (video.release != null) {
                        intent.putExtra("release", video.release)
                    }
                }
                if (video.duration_str != null) intent.putExtra("duration", video.duration_str)
                if (video.maturity_rating != null) intent.putExtra(
                    "maturity_rating",
                    video.maturity_rating
                )
                if (video.is_free != null) intent.putExtra("ispaid", video.is_free.toString())
                if (video.language_str != null) intent.putExtra("language_str", video.language_str)
                if (video.is_live != null) intent.putExtra("is_live", video.is_live.toString())
                if (video.rating != null) intent.putExtra("rating", video.rating.toString())
                if (video.trailers != null && video.trailers.size > 0 && video.trailers[0] != null && video.trailers[0].media_url != null) {
                    intent.putExtra("trailer", video.trailers[0].media_url)
                }
                if (video.trailer_aws_source != null) {
                    intent.putExtra("trailer", video.trailer_aws_source)
                }
                if (video.genres != null) {
                    if (video.genres.size > 0) {
                        var genres: String
                        genres = video.genres[0]
                        for (i in 1 until video.genres.size) {
                            genres = genres + "," + video.genres[i]
                        }
                        intent.putExtra("genres", genres)
                    }
                } else {
                    if (video.genre != null) {
                        intent.putExtra("genres", video.genre)
                    }
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
            if (video.type == "M" && video.is_live.toString().equals("1", ignoreCase = true)) {
                val intent = Intent(context, DetailsActivityPhando::class.java)
                if (video.type != null) intent.putExtra("type", video.type)
                if (video.thumbnail != null) {
                    intent.putExtra("thumbImage", video.thumbnail)
                } else {
                    if (video.thumbnailUrl != null) {
                        intent.putExtra("thumbImage", video.thumbnailUrl)
                    }
                }
                if (video.id != null) {
                    intent.putExtra("video_id", video.id.toString())
                }
                if (video.title != null) intent.putExtra("title", video.title)
                if (video.detail != null) {
                    intent.putExtra("description", video.detail)
                } else {
                    if (video.description != null) intent.putExtra("description", video.description)
                }
                if (video.release_date != null) {
                    intent.putExtra("release", video.release_date)
                } else {
                    if (video.release != null) {
                        intent.putExtra("release", video.release)
                    }
                }
                if (video.duration_str != null) intent.putExtra("duration", video.duration_str)
                if (video.maturity_rating != null) intent.putExtra(
                    "maturity_rating",
                    video.maturity_rating
                )
                if (video.is_free != null) intent.putExtra("ispaid", video.is_free.toString())
                if (video.language_str != null) intent.putExtra("language_str", video.language_str)
                if (video.is_live != null) intent.putExtra("is_live", video.is_live.toString())
                if (video.rating != null) intent.putExtra("rating", video.rating.toString())
                if (video.trailers != null && video.trailers.size > 0 && video.trailers[0] != null && video.trailers[0].media_url != null) {
                    intent.putExtra("trailer", video.trailers[0].media_url)
                }
                if (video.trailer_aws_source != null) {
                    intent.putExtra("trailer", video.trailer_aws_source)
                }
                if (video.genres != null) {
                    if (video.genres.size > 0) {
                        var genres: String
                        genres = video.genres[0]
                        for (i in 1 until video.genres.size) {
                            genres = genres + "," + video.genres[i]
                        }
                        intent.putExtra("genres", genres)
                    }
                } else {
                    if (video.genre != null) {
                        intent.putExtra("genres", video.genre)
                    }
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }
    }
}