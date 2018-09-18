package com.hcpt.marketplace.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.hcpt.marketplace.R;
import com.hcpt.marketplace.util.ImageUltis;
import com.squareup.picasso.Picasso;

@SuppressLint("NewApi")
public final class BannerFragment extends Fragment implements OnClickListener {

    private View view;
    private ImageView imgBanner;
    private ProgressBar progress;
    public String urlImage;

    public static BannerFragment InStances(String urlImage) {
        BannerFragment fragment = new BannerFragment();
        fragment.urlImage = urlImage;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_layout_banner, null);
        AQuery aq = new AQuery(getActivity());
        imgBanner = (ImageView) view.findViewById(R.id.imgBanner);
        progress = (ProgressBar) view.findViewById(R.id.progess);
        int subtractions = convertDpToPixel(getActivity(), R.dimen.dimen_1x, R.dimen.margin_normal) * 2;
        ImageUltis.calViewRatio(getActivity(), imgBanner, 16, 9, subtractions);
        aq.id(imgBanner)
                .progress(progress)
                .image(urlImage, true, true, 0,
                        R.drawable.no_image_available_horizontal,
                        new BitmapAjaxCallback() {
                            @SuppressWarnings("deprecation")
                            @Override
                            public void callback(String url, ImageView iv,
                                                 Bitmap bm, AjaxStatus status) {

                                Drawable d = new BitmapDrawable(getResources(),
                                        bm);
                                imgBanner.setBackgroundDrawable(d);
                            }
                        });
        return view;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
    }

    public static int convertDpToPixel(Context context, int... dimensionId) {
        int result = 0;

//        for (int i = 0; i < dimensionId.length; i++) {
//            result = result + (int) (context.getResources().getDimension(dimensionId[i]) / context.getResources().getDisplayMetrics().density);
//        }

        for (int dimen : dimensionId) {
            result = result + (int) (context.getResources().getDimension(dimen) / context.getResources().getDisplayMetrics().density);
        }

        return result;
    }
}
