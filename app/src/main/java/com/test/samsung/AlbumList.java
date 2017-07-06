package com.test.samsung;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.models.Album;
import com.utils.AlbumAdapter;
import com.utils.Constants;
import com.utils.ImageSlidePageAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class AlbumList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ListView.OnItemClickListener, ListView.OnItemLongClickListener {

    private ListView albumLV;
    private AlbumAdapter listAdapter;
    private ArrayList<Album> albumList = new ArrayList<>();
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        init();
    }

    /*METHOD: initialization*/
    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        albumLV = (ListView) findViewById(R.id.image_list);
        LayoutInflater inflater = this.getLayoutInflater();
        LinearLayout listHeader = (LinearLayout)inflater.inflate(R.layout.listview_header,null);
        albumLV.addHeaderView(listHeader);
        /*Set album adapter into list view*/
        listAdapter = new AlbumAdapter(this, albumList);

        setTitle(R.string.txt_samsung_albums);

        /*Setting up Album Data*/
        setAlbumData();

        albumLV.setAdapter(listAdapter);
        albumLV.setOnItemClickListener(this);
        albumLV.setOnItemLongClickListener(this);

    }

    /*METHOD: set Album with Images and titles from Assert folder
    *   Note: We can change external storage path after downloading the images from the internet instead of asset folder path.
    *   I put images in assets folder in project without configuring path.
    * */
    private void setAlbumData() {
        try {
            /* find list of albums inside assets folder and store it in Album'sArrayList */
            String[] arrayAlbums = getAssets().list("Samsung");
            if (arrayAlbums != null) {
                for (String albumTitle : arrayAlbums) {
                    Album album = new Album();
                    album.setAlbumName(albumTitle);
                    Log.d("Album Name: ", "" + albumTitle);
                    ArrayList<String> listImages = new ArrayList<>();
                    String[] images = getAssets().list("Samsung" + "/" + albumTitle);
                    if (images != null) {
                        for (String imgName : images) {
                            listImages.add(imgName);
                            Log.d("Image: ", "" + imgName);
                        }
                    }
                    album.setAlbumImages(listImages);
                    albumList.add(album);
                }
            }
            listAdapter.notifyDataSetChanged();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*METHOD: Display Custom Gallery*/
    private void displayGallery(Album selectedAlbum) {

        Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        dialog.getWindow().setBackgroundDrawableResource(R.color.blk_transparent);
        dialog.setContentView(getLayoutInflater().inflate(R.layout.image_preview
                , null));

        ImageSlidePageAdapter adapter = new ImageSlidePageAdapter(this, selectedAlbum);

        ViewPager viewPager = (ViewPager) dialog.findViewById(R.id.pager);

        viewPager.setAdapter(adapter);

        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(this, AlbumImages.class);
        intent.putExtra(Constants.PARSE_ALBUM_IMAGES, albumList.get(position - 1));
        startActivity(intent);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        Log.d("Album Selected: ", "" + albumList.get(position - 1).getAlbumName());
        displayGallery(albumList.get(position - 1));
        return false;
    }
}
