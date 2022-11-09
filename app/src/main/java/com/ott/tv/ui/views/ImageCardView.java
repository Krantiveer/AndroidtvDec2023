/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.ott.tv.ui.views;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.leanback.widget.BaseCardView;


import com.ott.tv.Constants;
import com.ott.tv.R;

import org.w3c.dom.Text;
/*import com.myplex.api.APIConstants;
import com.myplex.model.CardData;
import com.myplex.util.SDKLogger;*/

public class ImageCardView extends BaseCardView {
    private static final int ITEM_TYPE_HORIZONTAL_LIST_BIG_ITEM = 1;
    private static final int ITEM_TYPE_BANNER = 2;
    private static final int ITEM_TYPE_SMALLVERTICAL = 3;
    private boolean showProgressBar;
    private String imageType = Constants.IMAGE_TYPE_COVERPOSTER;

    public ImageView getImageView() {
        return (ImageView) findViewById(R.id.main_image);
    }

    public ImageView getPrimeImageView() {
        return (ImageView) findViewById(R.id.premiumIconImage);
    }
    public TextView getTextPrimeView() {
        return (TextView) findViewById(R.id.premiumIcon);
    }


    //  private String imageType = APIConstants.IMAGE_TYPE_COVERPOSTER;


    public ImageCardView(Context context, int itemViewType) {
        super(context, null, R.style.TextCardStyle);
      //  LayoutInflater.from(getContext()).inflate(R.layout.card_view_image_layout, this);
        switch (itemViewType) {
            case ITEM_TYPE_HORIZONTAL_LIST_BIG_ITEM:
                showProgressBar = false;
                imageType = Constants.IMAGE_TYPE_COVERPOSTER;
                LayoutInflater.from(getContext()).inflate(R.layout.card_view_image_layout_banner, this);
                break;
            case ITEM_TYPE_BANNER:
                showProgressBar = false;
                imageType = Constants.IMAGE_TYPE_COVERPOSTER;
                LayoutInflater.from(getContext()).inflate(R.layout.card_view_image_layout, this);
                break;

                case ITEM_TYPE_SMALLVERTICAL:
                showProgressBar = false;
                imageType = Constants.IMAGE_TYPE_COVERPOSTER;
                LayoutInflater.from(getContext()).inflate(R.layout.card_view_image_genrelayout, this);
                break;
            default:
                showProgressBar = false;
                imageType = Constants.IMAGE_TYPE_COVERPOSTER;
                LayoutInflater.from(getContext()).inflate(R.layout.card_view_image_layout, this);
                break;

        }

 //todo: this code use in future used
        setFocusable(true);
    }







}
