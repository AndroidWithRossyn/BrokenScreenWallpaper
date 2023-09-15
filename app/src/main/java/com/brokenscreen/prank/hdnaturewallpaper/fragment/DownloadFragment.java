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

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.brokenscreen.prank.hdnaturewallpaper.R;
import com.brokenscreen.prank.hdnaturewallpaper.adapter.WallpaperAdapter;
import com.brokenscreen.prank.hdnaturewallpaper.model.Wallpaper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class DownloadFragment extends Fragment {
    private static final String DOWNLOADS_PREF_NAME = "my_downloads_theme";
    private RecyclerView downloadrecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), view.findViewById(R.id.native_ad_0).findViewById(com.adsmodule.api.R.id.native_ad), 2, null);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), view.findViewById(R.id.native_ad_1).findViewById(com.adsmodule.api.R.id.native_ad1), 1, null);

        downloadrecycler = view.findViewById(R.id.downloadrecycler);

        SharedPreferences sharedPreferencesdownloads = requireActivity().getSharedPreferences(DOWNLOADS_PREF_NAME, Context.MODE_PRIVATE);
        Set<String> favoriteUrls = sharedPreferencesdownloads.getStringSet("download_urls", new HashSet<>());

        ArrayList<Wallpaper> downloadDataList = new ArrayList<>();
        for (String imageUrl : favoriteUrls) {
            Wallpaper data = new Wallpaper();
            data.setUrl(imageUrl);
            downloadDataList.add(data);
        }

        WallpaperAdapter downloadAdapter = new WallpaperAdapter(requireActivity(), downloadDataList);
        downloadrecycler.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
        downloadrecycler.setAdapter(downloadAdapter);
        return view;
    }
}