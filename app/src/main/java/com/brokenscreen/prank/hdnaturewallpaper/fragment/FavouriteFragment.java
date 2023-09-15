package com.brokenscreen.prank.hdnaturewallpaper.fragment;

import static com.brokenscreen.prank.hdnaturewallpaper.SingletonClasses.AppOpenAds.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.brokenscreen.prank.hdnaturewallpaper.R;
import com.brokenscreen.prank.hdnaturewallpaper.adapter.WallpaperAdapter;
import com.brokenscreen.prank.hdnaturewallpaper.model.Wallpaper;
import com.brokenscreen.prank.hdnaturewallpaper.ui.CategoryShowActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FavouriteFragment extends Fragment {
    private static final String FAVORITES_PREF_NAME = "my_favorites_theme";
    private RecyclerView favouriterecycler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), view.findViewById(R.id.native_ad_0).findViewById(com.adsmodule.api.R.id.native_ad), 2, null);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), view.findViewById(R.id.native_ad_1).findViewById(com.adsmodule.api.R.id.native_ad1), 1, null);

        favouriterecycler = view.findViewById(R.id.favouriterecycler);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(FAVORITES_PREF_NAME, Context.MODE_PRIVATE);
        Set<String> favoriteUrls = sharedPreferences.getStringSet("favorite_urls", new HashSet<>());

        ArrayList<Wallpaper> favoriteDataList = new ArrayList<>();
        for (String imageUrl : favoriteUrls) {
            Wallpaper data = new Wallpaper();
            data.setUrl(imageUrl);
            favoriteDataList.add(data);
        }

        WallpaperAdapter favoriteAdapter = new WallpaperAdapter(requireActivity(), favoriteDataList);
        favouriterecycler.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
        favouriterecycler.setAdapter(favoriteAdapter);
        return view;
    }
}
