package com.test.samsung;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.models.Album;
import com.utils.AlbumImagesAdapter;
import com.utils.Constants;

public class AlbumImages extends AppCompatActivity {

    private ListView lvAlbmImages;
    private AlbumImagesAdapter listAdapter;
    private Album album = new Album();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_images);

        init();
    }

    /*METHOD: Initialization*/
    private void init() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            album = bundle.getParcelable(Constants.PARSE_ALBUM_IMAGES);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(album.getAlbumName());
        lvAlbmImages = (ListView) findViewById(R.id.lv_album_images);
        LayoutInflater inflater = this.getLayoutInflater();
        LinearLayout listHeader = (LinearLayout)inflater.inflate(R.layout.listview_header,null);
        lvAlbmImages.addHeaderView(listHeader);
        /*Set album adapter into list view*/
        listAdapter = new AlbumImagesAdapter(this, album);
        lvAlbmImages.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
