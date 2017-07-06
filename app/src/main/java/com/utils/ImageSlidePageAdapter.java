package com.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.models.Album;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.test.samsung.R;

/**
 * Created by mauli on 7/5/2017.
 */

public class ImageSlidePageAdapter extends PagerAdapter{

    private Context mContext;
    private Album mAlbum;
    private LayoutInflater inflater;
    private ImageLoaderConfiguration config;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ProgressBar progress;

    public ImageSlidePageAdapter(Context context, Album album){
        mContext = context;
        mAlbum = album;
        inflater = ((Activity) mContext).getLayoutInflater();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        config = new ImageLoaderConfiguration.Builder(mContext)
                .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

    @Override
    public int getCount() {
        return mAlbum.getAlbumImages().size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.raw_preview, container, false);

        ImageView imgAlbum = (ImageView) view.findViewById(R.id.imgPreview);
        progress = (ProgressBar) view.findViewById(R.id.progImagePreview);
        view.setTag(position);

        ((ViewPager) container).addView(view);

        String path = "assets://" + Constants.DIR_SAMSUNG + "/" + mAlbum.getAlbumName()
                + "/" + mAlbum.getAlbumImages().get(position);

        imageLoader.displayImage(path, imgAlbum, options, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                progress.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }

   @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }
}
