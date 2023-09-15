package com.brokenscreen.prank.hdnaturewallpaper.ui;

import static com.brokenscreen.prank.hdnaturewallpaper.SingletonClasses.AppOpenAds.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Interfaces.AppInterfaces;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.brokenscreen.prank.hdnaturewallpaper.R;
import com.brokenscreen.prank.hdnaturewallpaper.adapter.WallpaperAdapter;
import com.brokenscreen.prank.hdnaturewallpaper.model.Wallpaper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ItemsActivity extends AppCompatActivity {
    private RecyclerView itemrecycler;
    private WallpaperAdapter wallpaperAdapter;
    private List<Wallpaper> wallpaperList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), findViewById(R.id.native_ad_0).findViewById(com.adsmodule.api.R.id.native_ad), 2, null);
        itemrecycler = findViewById(R.id.itemrecycler);
        wallpaperList = new ArrayList<>();
        wallpaperAdapter = new WallpaperAdapter(ItemsActivity.this, (ArrayList<Wallpaper>) wallpaperList);
        GridLayoutManager layoutManager = new GridLayoutManager(ItemsActivity.this, 2);
        itemrecycler.setLayoutManager(layoutManager);
        itemrecycler.setHasFixedSize(true);
        itemrecycler.setNestedScrollingEnabled(false);
        itemrecycler.setAdapter(wallpaperAdapter);
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");
        fetchWallpapers(itemName);
    }

    private void fetchWallpapers(String itemName) {
        try {
            InputStream inputStream = this.getAssets().open("category.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonConfig = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(jsonConfig);
            JSONObject categoriesObject = jsonObject.getJSONObject("Categories");

            if (categoriesObject.has(itemName)) {
                JSONArray urlsArray = categoriesObject.getJSONObject(itemName).getJSONArray("urls");
                for (int i = 0; i < urlsArray.length(); i++) {
                    String url = urlsArray.getString(i);
                    Wallpaper wallpaper = new Wallpaper();
                    wallpaper.setUrl(url);
                    wallpaperList.add(wallpaper);
                }
            } else {
                Toast.makeText(this, "Error...", Toast.LENGTH_SHORT).show();

            }

            wallpaperAdapter.notifyDataSetChanged();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
            @Override
            public void appOpenAdState(boolean state_load) {
                ItemsActivity.super.onBackPressed();
            }
        });
    }

}