package com.brokenscreen.prank.hdnaturewallpaper.ui;

import static com.brokenscreen.prank.hdnaturewallpaper.SingletonClasses.AppOpenAds.activity;
import static com.brokenscreen.prank.hdnaturewallpaper.utils.Utility.setGradientShaderToTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.brokenscreen.prank.hdnaturewallpaper.R;
import com.brokenscreen.prank.hdnaturewallpaper.databinding.ActivityDashBoardBinding;
import com.brokenscreen.prank.hdnaturewallpaper.databinding.DialogExitBinding;
import com.brokenscreen.prank.hdnaturewallpaper.fragment.CategoryFragment;
import com.brokenscreen.prank.hdnaturewallpaper.fragment.DownloadFragment;
import com.brokenscreen.prank.hdnaturewallpaper.fragment.FavouriteFragment;
import com.brokenscreen.prank.hdnaturewallpaper.fragment.WallpaperFragment;

public class DashBoardActivity extends AppCompatActivity {
    private ActivityDashBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), findViewById(R.id.native_ad_large).findViewById(com.adsmodule.api.R.id.native_ad1), 1, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setGradientShaderToTextView(binding.toolbarText, getColor(R.color.lg_blue), getColor(R.color.primary));
        }
        binding.menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawer.openDrawer(Gravity.LEFT);
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawer.closeDrawer(Gravity.LEFT);
            }
        });
        binding.wallpaperLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    binding.drawer.closeDrawer(Gravity.LEFT);
                    navigateToMainActivityWithFragment(WallpaperFragment.class, R.drawable.wallpaper_clear);
                });
            }

        });
        binding.categoryLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    binding.drawer.closeDrawer(Gravity.LEFT);
                    navigateToMainActivityWithFragment(CategoryFragment.class, R.drawable.category_clear);
                });

            }
        });
        binding.categoryScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    navigateToMainActivityWithFragment(CategoryFragment.class, R.drawable.category_clear);
                });

            }
        });
        binding.favouriteLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    binding.drawer.closeDrawer(Gravity.LEFT);
                    navigateToMainActivityWithFragment(FavouriteFragment.class, R.drawable.favourites_clear);
                });

            }
        });
        binding.favouritesScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    navigateToMainActivityWithFragment(FavouriteFragment.class, R.drawable.favourites_clear);
                });

            }
        });
        binding.downloadLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    binding.drawer.closeDrawer(Gravity.LEFT);
                    navigateToMainActivityWithFragment(DownloadFragment.class, R.drawable.download_clear);
                });

            }
        });
        binding.downloadScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    navigateToMainActivityWithFragment(DownloadFragment.class, R.drawable.download_clear);
                });

            }
        });
        binding.brokenScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    startActivity(new Intent(DashBoardActivity.this, MainActivity.class));
                });

            }
        });
        binding.privacyPolicyLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    startActivity(new Intent(DashBoardActivity.this, NewPrivacyPolicyActivity.class));
                });
            }
        });
        binding.termsAndConditionLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    startActivity(new Intent(DashBoardActivity.this, TermsConditionsActivity.class));
                });
            }
        });

    }

    private void navigateToMainActivityWithFragment(Class<? extends Fragment> fragmentClass, int iconResourceId) {
        Intent intent = new Intent(DashBoardActivity.this, MainActivity.class);
        intent.putExtra("fragmentClass", fragmentClass.getName());
        intent.putExtra("iconResourceId", iconResourceId);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (binding.drawer.isDrawerOpen(GravityCompat.START))
            binding.drawer.closeDrawer(GravityCompat.START);
        else {
            openCloseDialog();
        }
    }

    private void openCloseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DashBoardActivity.this);
        DialogExitBinding bind = DialogExitBinding.inflate(LayoutInflater.from(DashBoardActivity.this));
        builder.setView(bind.getRoot());
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), bind.nativeAd2.findViewById(com.adsmodule.api.R.id.native_ad1), 1, null);
        bind.btnExit.setOnClickListener(v -> {
            dialog.dismiss();
            finishAffinity();
            System.exit(0);
        });
        bind.btnBack.setOnClickListener(v -> dialog.dismiss());

    }


}