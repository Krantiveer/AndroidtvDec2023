package com.ott.tv.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ott.tv.fragments.TvSplashFragment
import com.ott.tv.utils.PreferenceUtils
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.ott.tv.R
import com.ott.tv.ui.activity.NewMainActivity
import com.ott.tv.ui.activity.LoginChooserActivity

class TvSplashFragment : Fragment() {
    val AMAZON_FEATURE_FIRE_TV = "amazon.hardware.fire_tv"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //  checkStoragePermission();
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_splash_screen, container, false)
        val splashImgView = view.findViewById<ImageView>(R.id.splash_img_view)
        Glide.with(this).load(R.drawable.logo_splash).into(splashImgView)
       /* if (context != null && requireContext().packageManager.hasSystemFeature(AMAZON_FEATURE_FIRE_TV)) {
            Log.v(TAG, "Yes, this is a Fire TV device.")
        } else {
            Log.v(TAG, "No, this is not a Fire TV device.")
        }*/

/*
        val path = "android.resource://" + requireContext().packageName + "/" + R.raw.splashvideio
        view.findViewById<VideoView>(R.id.videoView).setVideoURI(Uri.parse(path))
        view.findViewById<VideoView>(R.id.videoView).start()
*/

        checkUserData()


        return view
    }

    private fun checkUserData() {
        Handler().postDelayed({
            if (context != null && activity != null) {
                if (PreferenceUtils.isLoggedIn(context)) {
                    val intent = Intent(context, NewMainActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(context, LoginChooserActivity::class.java)
                    startActivity(intent)
                    /*Intent intent = new Intent(getContext(), LeanbackActivity.class);
                    startActivity(intent);
                    */
                }
                activity!!.finishAffinity()
                activity!!.overridePendingTransition(R.anim.enter, R.anim.exit)
            }
        }, SPLASH_DURATION_MS)
    } // ------------------ checking storage permission ------------

    /*
 **** need no need to used because ***
    private void checkStoragePermission() {
        if (getActivity() != null) {
            String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            int grant = ActivityCompat.checkSelfPermission(getActivity(), permission);
            if (grant != PackageManager.PERMISSION_GRANTED) {
                String[] permission_list = new String[1];
                permission_list[0] = permission;
                requestPermissions(permission_list, 1);
            } else {
                checkUserData();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // perform your action here
                checkUserData();
            } else {
                Toast.makeText(getActivity(), "permission not granted", Toast.LENGTH_SHORT).show();
                System.exit(0);
            }
        }

    }*/
    companion object {
        private const val TAG = "TvSplashScreen"
        @JvmField
        var COMPLETED_SPLASH = false
        private const val SPLASH_DURATION_MS: Long = 1000
    }
}