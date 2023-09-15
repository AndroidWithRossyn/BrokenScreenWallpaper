package com.brokenscreen.prank.hdnaturewallpaper.fragment.wallpaper;

import static com.brokenscreen.prank.hdnaturewallpaper.SingletonClasses.AppOpenAds.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adsmodule.api.AdsModule.AdUtils;
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

public class RandomFragment extends Fragment {

    private RecyclerView randomrecycler;
    private WallpaperAdapter wallpaperAdapter;
    private List<Wallpaper> wallpaperList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_random, container, false);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), view.findViewById(R.id.native_ad_0).findViewById(com.adsmodule.api.R.id.native_ad), 2, null);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), view.findViewById(R.id.native_ad_1).findViewById(com.adsmodule.api.R.id.native_ad1), 1, null);
        randomrecycler = view.findViewById(R.id.randomrecycler);
        wallpaperList = new ArrayList<>();
        wallpaperAdapter = new WallpaperAdapter(requireActivity(), (ArrayList<Wallpaper>) wallpaperList);

        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 2);
        randomrecycler.setLayoutManager(layoutManager);
        randomrecycler.setHasFixedSize(true);
        randomrecycler.setNestedScrollingEnabled(false);
        randomrecycler.setAdapter(wallpaperAdapter);

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
            processCategory(categoriesObject, "Random");

            wallpaperAdapter.notifyDataSetChanged();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void processCategory(JSONObject categoriesObject, String categoryName) throws JSONException {
        if (categoriesObject.has(categoryName)) {
            JSONArray urlsArray = categoriesObject.getJSONObject(categoryName).getJSONArray("urls");
            for (int i = 0; i < urlsArray.length(); i++) {
                String url = urlsArray.getString(i);
                Wallpaper wallpaper = new Wallpaper();
                wallpaper.setUrl(url);
                wallpaperList.add(wallpaper);
            }
        }
    }
}