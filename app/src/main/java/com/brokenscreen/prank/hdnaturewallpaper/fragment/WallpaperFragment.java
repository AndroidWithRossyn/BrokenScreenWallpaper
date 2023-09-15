package com.brokenscreen.prank.hdnaturewallpaper.fragment;

import static com.brokenscreen.prank.hdnaturewallpaper.SingletonClasses.AppOpenAds.activity;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.brokenscreen.prank.hdnaturewallpaper.R;
import com.brokenscreen.prank.hdnaturewallpaper.databinding.FragmentWallpaperBinding;
import com.brokenscreen.prank.hdnaturewallpaper.fragment.wallpaper.FeaturedFragment;
import com.brokenscreen.prank.hdnaturewallpaper.fragment.wallpaper.PopularFragment;
import com.brokenscreen.prank.hdnaturewallpaper.fragment.wallpaper.RandomFragment;
import com.brokenscreen.prank.hdnaturewallpaper.fragment.wallpaper.RecentFragment;
import com.brokenscreen.prank.hdnaturewallpaper.ui.MainActivity;
import com.brokenscreen.prank.hdnaturewallpaper.utils.Utility;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;


public class WallpaperFragment extends Fragment {
    private ArrayList<ThemeOptionsModel> tabListThemesType;
    private FragmentWallpaperBinding fragmentHomeBinding;
    private MainActivity myActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentWallpaperBinding.inflate(inflater, container, false);
        fragmentHomeBinding.viewPagerThemesOption.setUserInputEnabled(false);
        tabListThemesType = new ArrayList<>();
        tabListThemesType.add(new ThemeOptionsModel("Trending"));
        tabListThemesType.add(new ThemeOptionsModel("Featured"));
        tabListThemesType.add(new ThemeOptionsModel("Popular"));
        tabListThemesType.add(new ThemeOptionsModel("Random"));
        return fragmentHomeBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout tabLayoutThemesOption = fragmentHomeBinding.tabLayoutThemesOption;

        for (ThemeOptionsModel themeOptionsModel : tabListThemesType) {
            TabLayout.Tab tab = tabLayoutThemesOption.newTab();
            tab.setText(themeOptionsModel.getName());
            tabLayoutThemesOption.addTab(tab);
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(tabListThemesType.size());
        fragmentHomeBinding.viewPagerThemesOption.setAdapter(adapter);
        fragmentHomeBinding.viewPagerThemesOption.setOffscreenPageLimit(4);

        TabLayoutMediator.TabConfigurationStrategy tabConfigurationStrategy = (tab, position) -> {
            tab.setText(tabListThemesType.get(position).getName());
        };

        new TabLayoutMediator(tabLayoutThemesOption, fragmentHomeBinding.viewPagerThemesOption, tabConfigurationStrategy).attach();

        // Apply the gradient shader to the custom TextViews of tab items
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < tabLayoutThemesOption.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayoutThemesOption.getTabAt(i);
                if (tab != null) {
                    TextView tabTextView = (TextView) tab.getCustomView();
                    if (tabTextView != null) {
                        int lgBlueColor = ContextCompat.getColor(requireContext(), R.color.lg_blue);
                        int primaryColor = ContextCompat.getColor(requireContext(), R.color.primary);
                        tabTextView.getPaint().setShader(new LinearGradient(
                                0, 0, 0, tabTextView.getTextSize(),
                                new int[]{lgBlueColor, primaryColor},
                                null,
                                Shader.TileMode.CLAMP
                        ));
                    }
                }
            }
        }
    }




    private static class ThemeOptionsModel {
        private final String name;

        public ThemeOptionsModel(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {
        private final int totalTabs;

        public ViewPagerAdapter(int totalTabs) {
            super(WallpaperFragment.this);
            this.totalTabs = totalTabs;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new RecentFragment();
                case 1:
                    return new FeaturedFragment();
                case 2:
                    return new PopularFragment();
                default:
                    return new RandomFragment();
            }
        }

        @Override
        public int getItemCount() {
            return totalTabs;
        }
    }
}