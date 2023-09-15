package com.brokenscreen.prank.hdnaturewallpaper.ui;

import static com.brokenscreen.prank.hdnaturewallpaper.SingletonClasses.AppOpenAds.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Interfaces.AppInterfaces;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.brokenscreen.prank.hdnaturewallpaper.R;
import com.brokenscreen.prank.hdnaturewallpaper.databinding.ActivityCategoryShowBinding;
import com.brokenscreen.prank.hdnaturewallpaper.databinding.SetWallpaperBinding;
import com.brokenscreen.prank.hdnaturewallpaper.model.Wallpaper;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class CategoryShowActivity extends AppCompatActivity {
    private Dialog dialog;
    Bitmap photoBitmap;
    public static final String FAVORITES_PREF_NAME = "my_favorites_theme";
    public static final String DOWNLOADS_PREF_NAME = "my_downloads_theme";
    private ActivityCategoryShowBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.R.color.transparent);

        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), findViewById(R.id.native_ad_0).findViewById(com.adsmodule.api.R.id.native_ad), 2, null);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
                    @Override
                    public void appOpenAdState(boolean state_load) {
                        CategoryShowActivity.super.onBackPressed();
                    }
                });
            }
        });
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Format the time
        String formattedTime = String.format("%02d:%02d", hour, minute);

        // Set the current time in the TextView
        binding.time.setText(formattedTime);

        // Set the day and date (replace with your desired logic)
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        String currentDay = dayFormat.format(calendar.getTime());

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        // Set the current day and date in the TextView
        String dayAndDate = currentDay + ", " + currentDate;
        binding.date.setText(dayAndDate);
        String imageUrl = getIntent().getStringExtra("imageUrl");
        Picasso.get()
                .load(imageUrl)
                .into(binding.imageView);

        //stores the item as favourites
        SharedPreferences sharedPreferences = getSharedPreferences(FAVORITES_PREF_NAME, Context.MODE_PRIVATE);
        Set<String> favoriteUrls = sharedPreferences.getStringSet("favorite_urls", new HashSet<>());
        if (imageUrl != null && favoriteUrls.contains(imageUrl)) {
            // Set the color of the favorite button to red
            binding.favourites.setColorFilter(ContextCompat.getColor(CategoryShowActivity.this, R.color.red));
        }
        SharedPreferences sharedPreferencesdownloads = getSharedPreferences(DOWNLOADS_PREF_NAME, Context.MODE_PRIVATE);
        Set<String> downloadUrls = sharedPreferencesdownloads.getStringSet("download_urls", new HashSet<>());
        if (imageUrl != null && downloadUrls.contains(imageUrl)) {
            // Set the color of the download button to red
            binding.downloads.setColorFilter(ContextCompat.getColor(CategoryShowActivity.this, R.color.primary));
        }

        binding.downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performImageDownload(imageUrl);
            }
        });
        binding.favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add the item URL to the favorites set
                addToFavoritesIfImageSet(imageUrl);
            }
        });
        binding.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage(imageUrl);
            }
        });


        dialog = new Dialog(this);
        // Inflate the dialog layout using data binding
        SetWallpaperBinding setWallpaperBinding = SetWallpaperBinding.inflate(LayoutInflater.from(this));
        View dialogView = setWallpaperBinding.getRoot();
        dialog.setContentView(dialogView);

        // Set up the "Apply" button click listener
        binding.apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoBitmap = ((BitmapDrawable) binding.imageView.getDrawable()).getBitmap();
                showSetWallpaperDialog();
            }
        });

    }

    private void addToFavoritesIfImageSet(String imageUrl) {
        SharedPreferences sharedPreferences = getSharedPreferences(FAVORITES_PREF_NAME, Context.MODE_PRIVATE);
        Set<String> favoriteUrls = sharedPreferences.getStringSet("favorite_urls", new HashSet<>());

        if (imageUrl != null) {
            if (favoriteUrls.contains(imageUrl)) {
                // Item is already marked as favorite, remove it from favorites
                favoriteUrls.remove(imageUrl);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("favorite_urls", favoriteUrls);
                editor.apply();
                binding.favourites.setColorFilter(null); // Reset color to default
                Toast.makeText(CategoryShowActivity.this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
            } else {
                // Item is not marked as favorite, add it to the favorites list
                favoriteUrls.add(imageUrl);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("favorite_urls", favoriteUrls);
                editor.apply();
                binding.favourites.setColorFilter(ContextCompat.getColor(CategoryShowActivity.this, R.color.red));
                Toast.makeText(CategoryShowActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showSetWallpaperDialog() {
        dialog = new Dialog(CategoryShowActivity.this);
        SetWallpaperBinding setWallpaperBinding = SetWallpaperBinding.inflate(LayoutInflater.from(CategoryShowActivity.this));
        dialog.setContentView(setWallpaperBinding.getRoot());

        setWallpaperBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        setWallpaperBinding.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
                    if (setWallpaperBinding.homeScreen.isChecked()) {
                        // Apply theme for Home Screen
                        try {
                            manager.setBitmap(photoBitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(CategoryShowActivity.this, "Theme applied for Home Screen", Toast.LENGTH_SHORT).show();
                    } else if (setWallpaperBinding.lockScreen.isChecked()) {
                        // Apply theme for Lock Screen
                        try {
                            manager.setBitmap(photoBitmap, null, true, WallpaperManager.FLAG_LOCK);//For Lock screen
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(CategoryShowActivity.this, "Theme applied for Lock Screen", Toast.LENGTH_SHORT).show();
                    } else if (setWallpaperBinding.bothScreen.isChecked()) {
                        // Apply theme for Both Home Screen and Lock Screen
                        try {
                            manager.setBitmap(photoBitmap, null, true, WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(CategoryShowActivity.this, "Theme applied for Both Home Screen and Lock Screen", Toast.LENGTH_SHORT).show();
                    } else {
                        // No radio button selected
                        Toast.makeText(CategoryShowActivity.this, "Please select a screen option", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });


                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void performImageDownload(String imageUrl) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(imageUrl));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download"); // Set a title for the download notification
        request.setDescription("Downloading image..."); // Set a description for the download notification
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "Broken Screen Wallpaper Prank/" + System.currentTimeMillis() + ".jpg");

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
//            Toast.makeText(this, "Image downloaded", Toast.LENGTH_SHORT).show();
        }
        SharedPreferences sharedPreferencesdownloads = getSharedPreferences(DOWNLOADS_PREF_NAME, Context.MODE_PRIVATE);
        Set<String> downloadUrls = sharedPreferencesdownloads.getStringSet("download_urls", new HashSet<>());

        if (imageUrl != null) {
            if (downloadUrls.contains(imageUrl)) {
                // Item is already marked as favorite, remove it from favorites
                downloadUrls.remove(imageUrl);
                SharedPreferences.Editor editor = sharedPreferencesdownloads.edit();
                editor.putStringSet("download_urls", downloadUrls);
                editor.apply();
                binding.downloads.setColorFilter(null); // Reset color to default
                Toast.makeText(CategoryShowActivity.this, "Removed from Downloads", Toast.LENGTH_SHORT).show();
            } else {
                // Item is not marked as favorite, add it to the favorites list
                downloadUrls.add(imageUrl);
                SharedPreferences.Editor editor = sharedPreferencesdownloads.edit();
                editor.putStringSet("download_urls", downloadUrls);
                editor.apply();
                binding.downloads.setColorFilter(ContextCompat.getColor(CategoryShowActivity.this, R.color.primary));
                Toast.makeText(CategoryShowActivity.this, "Added to Downloads", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void shareImage(String imageUrl) {
        if (imageUrl != null) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) binding.imageView.getDrawable();
            if (bitmapDrawable != null) {
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (bitmap != null) {
                    try {
                        // Save the image to a temporary file
                        String fileName = "temp_image.png";
                        File cachePath = new File(getApplicationContext().getCacheDir(), "images");
                        cachePath.mkdirs();
                        File file = new File(cachePath, fileName);
                        FileOutputStream fos = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.flush();
                        fos.close();

                        // Create the share intent
                        Uri imageUri = FileProvider.getUriForFile(CategoryShowActivity.this, getApplicationContext().getPackageName() + ".fileprovider", file);
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("image/png");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                        startActivity(Intent.createChooser(shareIntent, "Share Image"));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(CategoryShowActivity.this, "Failed to share image", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
            @Override
            public void appOpenAdState(boolean state_load) {
                CategoryShowActivity.super.onBackPressed();
            }
        });
    }
}