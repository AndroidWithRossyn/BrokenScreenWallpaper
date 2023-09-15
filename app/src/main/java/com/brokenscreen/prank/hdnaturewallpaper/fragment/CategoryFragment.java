package com.brokenscreen.prank.hdnaturewallpaper.fragment;

import static com.brokenscreen.prank.hdnaturewallpaper.SingletonClasses.AppOpenAds.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.brokenscreen.prank.hdnaturewallpaper.R;
import com.brokenscreen.prank.hdnaturewallpaper.adapter.CategoryWallpaperAdapter;
import com.brokenscreen.prank.hdnaturewallpaper.adapter.WallpaperAdapter;
import com.brokenscreen.prank.hdnaturewallpaper.model.CategoryWallpaper;
import com.brokenscreen.prank.hdnaturewallpaper.model.Wallpaper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment{
    private RecyclerView categoryrecycler;
    private CategoryWallpaperAdapter categoryWallpaperAdapter;
    private List<CategoryWallpaper> categoryWallpaperList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), view.findViewById(R.id.native_ad_small).findViewById(com.adsmodule.api.R.id.native_ad), 2, null);
        categoryrecycler = view.findViewById(R.id.categoryrecycler);
        categoryWallpaperList = new ArrayList<>();
        categoryWallpaperAdapter = new CategoryWallpaperAdapter(requireActivity(), (ArrayList<CategoryWallpaper>) categoryWallpaperList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        categoryrecycler.setLayoutManager(layoutManager);
        categoryrecycler.setHasFixedSize(true);
        categoryrecycler.setNestedScrollingEnabled(false);
        categoryrecycler.setAdapter(categoryWallpaperAdapter);

        fetchWallpapers();

        return view;
    }

    private void fetchWallpapers() {
        try {
            InputStream inputStream = requireActivity().getAssets().open("category.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonConfig = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(jsonConfig);
            JSONObject categoriesObject = jsonObject.getJSONObject("Categories");
            processCategory(categoriesObject, "NewCategory");

            categoryWallpaperAdapter.notifyDataSetChanged();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void processCategory(JSONObject categoriesObject, String categoryName) throws JSONException {
        if (categoriesObject.has(categoryName)) {
            JSONArray urlsArray = categoriesObject.getJSONObject(categoryName).getJSONArray("urls");
            JSONArray itemNameArray = categoriesObject.getJSONObject(categoryName).getJSONArray("item_name");


            for (int i = 0; i < urlsArray.length(); i++) {
                String url = urlsArray.getString(i);
                String itemName = itemNameArray.getString(i);


                CategoryWallpaper wallpaper = new CategoryWallpaper(url,itemName);
                wallpaper.setUrl(url);
                wallpaper.setItem_name(itemName);


                categoryWallpaperList.add(wallpaper);
            }
        }
    }



}