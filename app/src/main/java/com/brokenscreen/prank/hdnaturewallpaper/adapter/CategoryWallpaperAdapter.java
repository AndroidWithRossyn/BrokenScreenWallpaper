package com.brokenscreen.prank.hdnaturewallpaper.adapter;

import static com.brokenscreen.prank.hdnaturewallpaper.SingletonClasses.AppOpenAds.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.brokenscreen.prank.hdnaturewallpaper.R;
import com.brokenscreen.prank.hdnaturewallpaper.model.CategoryWallpaper;
import com.brokenscreen.prank.hdnaturewallpaper.model.Wallpaper;
import com.brokenscreen.prank.hdnaturewallpaper.ui.CategoryShowActivity;
import com.brokenscreen.prank.hdnaturewallpaper.ui.ItemsActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryWallpaperAdapter extends RecyclerView.Adapter<CategoryWallpaperAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CategoryWallpaper> arrayList;



    public CategoryWallpaperAdapter(Context context, ArrayList<CategoryWallpaper> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }


    @NonNull
    @Override
    public CategoryWallpaperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_wallpaper_item, parent, false);
        return new CategoryWallpaperAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryWallpaperAdapter.ViewHolder holder, int position) {
        CategoryWallpaper wallpaper = arrayList.get(position);
        holder.imageView.setVisibility(View.VISIBLE);
        holder.progressBar.setVisibility(View.VISIBLE);

        Picasso.get()
                .load(wallpaper.getUrl())
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(context, "Failed to load image", Toast.LENGTH_SHORT).show();
                        holder.progressBar.setVisibility(View.GONE);
                    }
                });


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    CategoryWallpaper wallpaper = arrayList.get(position);
                    String itemName = wallpaper.getItem_name();
                    Intent intent = new Intent(context, ItemsActivity.class);
                    intent.putExtra("itemName", itemName);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                });


            }
        });
        if (position != 0){
            if (position%4 == 0){
                holder.native_ad_large.setVisibility(View.VISIBLE);
                AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), holder.native_ad_large.findViewById(com.adsmodule.api.R.id.native_ad1), 1, null);
                // Show ads
            } else {
                holder.native_ad_large.setVisibility(View.GONE);
                // hide ads
            }
        }
        holder.item_name.setText(wallpaper.getItem_name());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView item_name;
        ProgressBar progressBar;
        LinearLayout native_ad_large;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            progressBar = itemView.findViewById(R.id.progressBar);
            item_name = itemView.findViewById(R.id.item_name);
            native_ad_large = itemView.findViewById(R.id.native_ad_1);
        }
    }
}