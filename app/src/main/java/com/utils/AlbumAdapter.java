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

public class AlbumAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Album> albumList = new ArrayList<>();
    /* Library: Nostral Universal External Library for the Image Loader*/
    private ImageLoaderConfiguration config;
    private ImageLoader imageLoader;
    DisplayImageOptions options;

    public AlbumAdapter(Context context, ArrayList<Album> album) {
        mContext = context;
        albumList = album;
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
        return (albumList.size() > 0) ? albumList.size() : 1;
    }

    @Override
    public Object getItem(int position) {
        return albumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return (albumList.size() > 0) ? albumList.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolder {
        TextView tvAlbumTitle;
        ImageView imgAlbumFstPic;
        ProgressBar progAlbum;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.raw_image, null);


            holder = new ViewHolder();
            holder.tvAlbumTitle = (TextView) view.findViewById(R.id.tvAlbumTitle);
            holder.imgAlbumFstPic = (ImageView) view.findViewById(R.id.imgAlbumFirst);
            holder.progAlbum = (ProgressBar) view.findViewById(R.id.progAlbumList);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvAlbumTitle.setText(albumList.get(position).getAlbumName());
        Album album = albumList.get(position);
        /*PATH: Get image from assets Samsung folder */
        String pathAlbumFstImg = "assets://" + Constants.DIR_SAMSUNG + "/" + album.getAlbumName()
                + "/" + album.getAlbumImages().get(0);


        imageLoader.displayImage(pathAlbumFstImg, holder.imgAlbumFstPic, options, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                holder.progAlbum.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.progAlbum.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.progAlbum.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                holder.progAlbum.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }
}
