package com.brokenscreen.prank.hdnaturewallpaper.ui;

import static com.brokenscreen.prank.hdnaturewallpaper.SingletonClasses.AppOpenAds.activity;
import static com.brokenscreen.prank.hdnaturewallpaper.utils.Utility.nextActivity;
import static com.brokenscreen.prank.hdnaturewallpaper.utils.Utility.setGradientShaderToTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.brokenscreen.prank.hdnaturewallpaper.R;
import com.brokenscreen.prank.hdnaturewallpaper.databinding.ActivityTermsOfUseBinding;

public class TermsOfUseActivity extends AppCompatActivity {
    ActivityTermsOfUseBinding binding;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_terms_of_use);
        type = getIntent().getIntExtra("type", 0);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), binding.nativeAd0.findViewById(com.adsmodule.api.R.id.native_ad), 2, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setGradientShaderToTextView(binding.title, getColor(R.color.primary), getColor(R.color.primary));
        }
        binding.privacyPolicyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TermsOfUseActivity.this, NewPrivacyPolicyActivity.class));
            }
        });

        binding.termsConditionsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TermsOfUseActivity.this, TermsConditionsActivity.class));
            }
        });

        binding.tvText.setOnClickListener(view -> mStartAct());

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void mStartAct() {
        AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
            startActivity(new Intent(this, DashBoardActivity.class));
            finish();
        });
    }
}