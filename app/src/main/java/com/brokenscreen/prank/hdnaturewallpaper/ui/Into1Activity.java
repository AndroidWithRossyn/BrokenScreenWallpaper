package com.brokenscreen.prank.hdnaturewallpaper.ui;




import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import static com.brokenscreen.prank.hdnaturewallpaper.SingletonClasses.AppOpenAds.activity;
import static com.brokenscreen.prank.hdnaturewallpaper.utils.Utility.setGradientShaderToTextView;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.brokenscreen.prank.hdnaturewallpaper.R;
import com.brokenscreen.prank.hdnaturewallpaper.databinding.ActivityInto1Binding;


public class Into1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInto1Binding binding = ActivityInto1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), binding.nativeAd0.findViewById(com.adsmodule.api.R.id.native_ad), 2, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setGradientShaderToTextView(binding.tvTitle, getColor(R.color.lg_blue), getColor(R.color.primary));
        }
        binding.txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    startActivity(new Intent(Into1Activity.this, TermsOfUseActivity.class));
                });

            }
        });
    }
}