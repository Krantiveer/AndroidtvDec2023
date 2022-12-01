package com.ott.tv.ui.presenter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.leanback.app.HeadersSupportFragment;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowHeaderPresenter;

import com.ott.tv.R;
import com.ott.tv.ui.IconHeaderItem;
import com.ott.tv.ui.activity.LoginActivity;


/**
 * Customized HeaderItem Presenter to show {@link IconHeaderItem}
 */
public class IconHeaderItemPresenter extends RowHeaderPresenter {

    private static final String TAG = IconHeaderItemPresenter.class.getSimpleName();

    private float mUnselectedAlpha;

    private View selectedView = null;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        mUnselectedAlpha = viewGroup.getResources().getFraction(R.fraction.lb_browse_header_unselect_alpha, 1, 1);

        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.icon_header_item, null);
        view.setFocusable(true);
     //   view.setAlpha(mUnselectedAlpha); // Initialize icons to be at half-opacity.

        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object o) {
        IconHeaderItem iconHeaderItem = (IconHeaderItem) ((ListRow) o).getHeaderItem();
        View rootView = viewHolder.view;

        ImageView iconView = rootView.findViewById(R.id.header_icon);
        int iconResId = iconHeaderItem.getIconResId();
        if (iconResId != IconHeaderItem.ICON_NONE) { // Show icon only when it is set.
            Drawable icon = ContextCompat.getDrawable(rootView.getContext(), iconResId);
            iconView.setImageDrawable(icon);
        }

        TextView label = rootView.findViewById(R.id.header_label);
        label.setText(iconHeaderItem.getName());
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        // no op
    }

    @Override
    protected void onSelectLevelChanged(RowHeaderPresenter.ViewHolder holder) {
        View view = holder.view.findViewById(R.id.viewIndicator);
        if (holder.getSelectLevel() == 1){
            view.setVisibility(View.VISIBLE);
            holder.view.setBackgroundColor(ContextCompat.getColor(holder.view.getContext(), R.color.white_bg));
        }else{
            holder.view.setBackgroundColor(ContextCompat.getColor(holder.view.getContext(), android.R.color.transparent));
            view.setVisibility(View.GONE);
        }
        // holder.view.setAlpha(mUnselectedAlpha + holder.getSelectLevel() * (1.0f - mUnselectedAlpha));
    }
        /*
        TextView header_label = holder.view.findViewById(R.id.header_label);
        ImageView header_icon = holder.view.findViewById(R.id.header_icon);
        Log.i(TAG, "onSelectLevelChanged: "+header_label.getText());
        if (holder.getSelectLevel() == 1) {
            view.setVisibility(View.VISIBLE);
            */
/*header_label.setVisibility(View.VISIBLE);*//*

            holder.view.setBackgroundColor(ContextCompat.getColor(holder.view.getContext(), R.color.white_bg));
            header_icon.setColorFilter(header_icon.getContext().getResources().getColor(R.color.colorGold), PorterDuff.Mode.SRC_ATOP);

        } else {
            holder.view.setBackgroundColor(ContextCompat.getColor(holder.view.getContext(), android.R.color.transparent));
            header_icon.setColorFilter(header_icon.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            view.setVisibility(View.GONE);
        */
/*    header_label.setVisibility(View.GONE);*//*

        }

        // holder.view.setAlpha(mUnselectedAlpha + holder.getSelectLevel() * (1.0f - mUnselectedAlpha));
    }
*/


}
