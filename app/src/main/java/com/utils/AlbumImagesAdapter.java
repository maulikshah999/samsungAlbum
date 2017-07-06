package com.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.models.Album;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.test.samsung.R;

import java.util.ArrayList;

/**
 * Created by mauli on 7/4/2017.
 */

public class AlbumImagesAdapter extends BaseAdapter {

    private Context mContext;
    private Album album = new Album();
    /* Library: Nostral Universal External Library for the Image Loader*/
    private ImageLoaderConfiguration config;
    private ImageLoader imageLoader;
    private ArrayList<String> listImages;
    private DisplayImageOptions options;
    public AlbumImagesAdapter(Context context, Album album) {
        mContext = context;
        this.album = album;
        listImages = this.album.getAlbumImages();

        /* Initialization of Image Loader */
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
        /* Manage crash when there is no item in the list.*/
        return (listImages.size() > 0) ? listImages.size() : 1;
    }

    @Override
    public Object getItem(int position) {
        return listImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return (listImages.size() > 0) ? listImages.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolder {
        TextView tvAlbumTitle;
        ImageView imgAlbumFstPic;
        ProgressBar progAlbumImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.raw_album_images, null);


            holder = new ViewHolder();
            holder.tvAlbumTitle = (TextView) view.findViewById(R.id.tvAlbumTitle);
            holder.imgAlbumFstPic = (ImageView) view.findViewById(R.id.imgAlbumFirst);
            holder.progAlbumImage = (ProgressBar) view.findViewById(R.id.progAlbumImages);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvAlbumTitle.setText(listImages.get(position));

        /*PATH: Get image from assets Samsung folder */
        String pathAlbumFstImg = "assets://" + Constants.DIR_SAMSUNG + "/" + album.getAlbumName()
                + "/" + listImages.get(position);

        imageLoader.displayImage(pathAlbumFstImg, holder.imgAlbumFstPic, options, new ImageLoadingListener() {


            @Override
            public void onLoadingStarted(String imageUri, View view) {
                holder.progAlbumImage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.progAlbumImage.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.progAlbumImage.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                holder.progAlbumImage.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }
}
